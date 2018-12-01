package iastate.cs309.server.Snake;

import iastate.cs309.server.Snake.SnakeEnums.TileType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class Map implements Runnable {
    private static final int width = 42;
    private static final int height = 42;
    private static Logger logger = LoggerFactory.getLogger(Map.class);
    private TileType[][] map; //could convert to tile object
    private List<Snake> pileOfSnakes = new ArrayList<>();
    private transient Random psudo = new Random();

    public Map() {
        map = new TileType[width][height];
        drawStarterMap();
        //spawnMob();
    }

    public void run() {
        long lastLoopTime = System.nanoTime();
        final int TARGET_FPS = 3;
        final long OPTIMAL_TIME = 1000000000 / TARGET_FPS; //nanoseconds per second / target fps
        long lastFpsTime = 0;
        while (true) {
            long now = System.nanoTime();
            long updateLength = now - lastLoopTime;
            lastLoopTime = now;
            double delta = updateLength / ((double) OPTIMAL_TIME);

            lastFpsTime += updateLength;
            if (lastFpsTime >= 1000000000) {
                lastFpsTime = 0;
            }

            this.update();

            try {
                SnakeEndpoint.broadcastMap();
            } catch (Exception e) {
                logger.error(e.getMessage());
            }

            try {
                Thread.sleep((lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / 1000000);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
    }

    public void update() {
        if (readyToSpawnFood()) spawnFood();
        if (readyToDespawnFood()) {
            Optional<Tile> t = findTile(TileType.Apple);

            if (t.isPresent()) {
                Coordinate c = t.get().getCoordinate();
                updateTile(c.getX(), c.getY(), TileType.Nothing);
            }
        }
        for (Snake snake :
                pileOfSnakes) {
            if (snake.desireRespawn) {
                appleBomb(snake.getSnake());
                snake.respawn(findSnakeSpawn());
            } else {
                snake.slither();
            }
        }
        drawSnakes();
    }

    //i was watching the office and wanted to implement something mindless while doing it
    private void spawnMob() {
        pileOfSnakes.add(new Mob("mob0", findSnakeSpawn()));
    }

    public void addSnake(Snake snake) {
        pileOfSnakes.add(snake);
    }

    public void removeSnake (Snake snake){
        pileOfSnakes.remove(snake);
    }
    private void killSnake(Snake snake) {
        appleBomb(snake.getSnake());
        snake.endSnake();
    }

    //finds a place with a contiguous snake sized spawn area
    public Coordinate findSnakeSpawn() {
        int dartX = Math.abs(psudo.nextInt()) % width;
        // pad the top and bottom snake heights to prevent spawning into a wall 1 move after spawning
        int dartY = Math.abs(psudo.nextInt()) % (height - (2 * Snake.spawnHeight)) + Snake.spawnHeight;

        if (isNTallNothing(dartX, dartY, Snake.spawnHeight))
            return new Coordinate(dartX, dartY);

        //dart didn't return anything, throw another;
        return findSnakeSpawn();
    }

    private boolean isNTallNothing(int x, int y, int n) {
        for (int i = 0; i < n; i++) {
            if (isOnMap(x, y + i) && map[x][y + i] != TileType.Nothing)
                return false;
        }
        return true;
    }

    /**
     * Optionals are pretty neat. Hibernate uses them a lot but I hadn't used them prior.
     *
     * @param tileType desired tile to find
     * @return Tile, maybe. return.isPresent()
     */
    private Optional<Tile> findTile(TileType tileType) {
        //dont want procedural looking cleaning.
        int dartX = Math.abs(psudo.nextInt()) % width;
        int dartY = Math.abs(psudo.nextInt()) % height;
        Coordinate c = new Coordinate(dartX, dartY);
        if (countTiles(TileType.Apple) < 5 * pileOfSnakes.size())
            return Optional.empty();
        if (map[dartX][dartY].equals(tileType))
            return Optional.of(new Tile(new Coordinate(dartX, dartY), tileType));
        //implicit else
        return findTile(tileType);
    }

    private int countTiles(TileType tileType) {
        int count = 0;
        //not really sure if this does by rows or columns, doesn't really matter tho
        for (TileType[] seg :
                map) {
            for (TileType pt :
                    seg) {
                if (pt.equals(tileType)) {
                    count++;
                }
            }
        }
        return count;
    }

    private boolean readyToSpawnFood() {
        return countTiles(TileType.Apple) < (countTiles(TileType.SnakeTail)+ 3 )/ (pileOfSnakes.size() +1 );
    }

    private boolean readyToDespawnFood() {
        return countTiles(TileType.Apple) > (pileOfSnakes.size() + 1) * 6 ;
    }

    private void spawnFood() {
        //Throw a random dart at the gameboard; put food there if you didn't poke a thing
        int dartX = Math.abs(psudo.nextInt()) % width;
        int dartY = Math.abs(psudo.nextInt()) % height;
        if (map[dartX][dartY].equals(TileType.Nothing)) {
            map[dartX][dartY] = TileType.Apple;
        }
    }

    /**
     * Creates the base map by filling edges with walls, and replacing the rest with a Nothing tile.
     */
    private void drawStarterMap() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (x == 0 || y == 0 || x == width - 1 || y == height - 1) {
                    updateTile(x, y, TileType.Wall);
                } else {
                    updateTile(x, y, TileType.Nothing);
                }
            }
        }
    }

    public void drawSnakes() {
        for (Snake snake :
                pileOfSnakes) {
            if (snake.isAlive) {
                List<Tile> p = snake.getSnake();
                for (int seg = 0; seg < p.size(); seg++) {
                    Tile snakeTile = p.get(seg);
                    int segX = snakeTile.getCoordinate().getX();
                    int segY = snakeTile.getCoordinate().getY();

                    if (isOnMap(segX, segY)) {
                        //only consider interactions with the head of the snake
                        if (seg == 0) {
                            switch (map[segX][segY]) {
                                case Nothing:
                                    updateTile(snakeTile);
                                    break;
                                case Apple:
                                    snake.feed();
                                    updateTile(new Tile(snakeTile.getCoordinate(), TileType.Nothing));
                                    updateTile(snakeTile);
                                    break;
                                case Wall:
                                case SnakeHead:
                                case SnakeTail:
                                    //kill the snake if its head is in another block
                                    killSnake(snake);
                                    //if (!isWall(snakeTile.getCoordinate()))
                                    //    updateTile(snakeTile);
                            }
                        } else {
                            if (!isWall(snakeTile.getCoordinate()))
                                updateTile(snakeTile);
                        }
                    } else {
                        //cheaters get the boot
                        killSnake(snake);
                    }
                }
            }
        }
    }

    private boolean isOnMap(int x, int y) {
        if (x >= 0 && y >= 0 && x < width && y < height)
            return true;
        logger.info("~~off map coordinate was accessed~~");
        return false;
    }

    private boolean isOnMap(Coordinate coord) {
        return isOnMap(coord.getX(), coord.getY());
    }

    private boolean isWall(Coordinate coord) {
        if (isOnMap(coord)) {
            return map[coord.getX()][coord.getY()].equals(TileType.Wall);
        }
        return true;
    }

    private void updateTile(Tile tile) {
        if (isOnMap(tile.getCoordinate())) {
            map[tile.getCoordinate().getX()][tile.getCoordinate().getY()] = tile.getTileType();
        }
    }

    //wrapper for map
    private void updateTile(int x, int y, TileType tileType) {
        if (isOnMap(x, y)) {
            map[x][y] = tileType;
        }
    }

    public void appleBomb(List<Tile> area) {
        for (Tile t :
                area) {
            int x = t.getCoordinate().getX();
            int y = t.getCoordinate().getY();
            if (isOnMap(x, y) && !isWall(t.getCoordinate()))
                updateTile(x, y, TileType.Apple);
        }
    }

    public void reset() {
        for (Snake snake :
                pileOfSnakes) {
            snake.endSnake();

        }
        pileOfSnakes.clear();
        drawStarterMap();
    }
}
