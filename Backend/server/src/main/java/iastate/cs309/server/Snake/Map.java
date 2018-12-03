package iastate.cs309.server.Snake;

import iastate.cs309.server.Snake.SnakeEnums.TileType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * The map describes a view of a plane holding apples wall snakes and blank spaces
 */
public class Map implements Runnable {
    private static final int width = 32;
    private static final int height = 32;
    private static Logger logger = LoggerFactory.getLogger(Map.class);
    private TileType[][] map; //could convert to tile object
    private List<Snake> pileOfSnakes = new ArrayList<>();
    private transient Random psudo = new Random();
    private transient boolean everyOther = false;

    public Map() {
        map = new TileType[width][height];
        drawStarterMap();
        //spawnMob();
    }

    /**
     * The method run by the thread controlling the game state
     * This is a blocking call that will ensure reliable periodic updates to the map and snakes within.
     */
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

    /**
     * ticks an update to the map, draws all contents that need to be redrawn
     * Handles creation and removal of food contents
     */
    public void update() {
        if (everyOther) {
            int foodCount = countTiles(TileType.Apple);
            int slowDespawn = psudo.nextInt();
            if (readyToSpawnFood(foodCount)) spawnFood();
            if (readyToDespawnFood(foodCount) && slowDespawn % 3 == 0) {
                Optional<Tile> t = findTile(TileType.Apple);

                if (t.isPresent()) {
                    Coordinate c = t.get().getCoordinate();
                    updateTile(c.getX(), c.getY(), TileType.Nothing);
                }
            }
        }
        everyOther = !everyOther;

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

    /**
     * adds a snake to the map
     *
     * @param snake to be added to map
     */
    public void addSnake(Snake snake) {
        pileOfSnakes.add(snake);
    }

    /**
     * removes a snake from the map
     *
     * @param snake the snake to be removed
     */
    public void removeSnake(Snake snake) {
        pileOfSnakes.remove(snake);
    }

    /**
     * wrapper to make killing the snake easier
     *
     * @param snake the snake to be turned into apples
     */
    private void killSnake(Snake snake) {
        appleBomb(snake.getSnake());
        snake.endSnake();
    }

    /**
     * finds a place with a contiguous snake sized spawn area
     */
    public Coordinate findSnakeSpawn() {
        int dartX = Math.abs(psudo.nextInt()) % width;
        // pad the top and bottom snake heights to prevent spawning into a wall 1 move after spawning
        int dartY = Math.abs(psudo.nextInt()) % (height - (2 * Snake.spawnHeight)) + Snake.spawnHeight;

        if (isNTallNothing(dartX, dartY, Snake.spawnHeight))
            return new Coordinate(dartX, dartY);

        //dart didn't return anything, throw another;
        return findSnakeSpawn();
    }

    /**
     * helper to determine where a valid snake spawn is
     *
     * @param x x coord
     * @param y y coord
     * @param n how tall a space we need of nothing tiles
     * @return
     */
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

    /**
     * counts the tiles of a given type
     *
     * @param tileType the type to count
     * @return an integer count of the given type
     */
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

    /**
     * @param appleCount number of apples on map
     * @return true when ready to spawn food based on some math
     */
    private boolean readyToSpawnFood(int appleCount) {
        return appleCount < foodMath(1);
    }

    /**
     * @param appleCount number of apples on the map
     * @return true when ready to despawn food
     */
    private boolean readyToDespawnFood(int appleCount) {
        return appleCount > foodMath(5);
    }

    /**
     * get a ratio of apples to number of snakes
     *
     * @param mFactor
     * @return
     */
    private int foodMath(int mFactor) {
        final int baseFoodAmount = 3;
        return baseFoodAmount + ((pileOfSnakes.size() + 1) * mFactor);
    }

    /**
     * spawns the food on the map
     */
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

    /**
     * draws each snake on the map object
     */
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

    /**
     * determine if the coordinate is in the map
     *
     * @param x
     * @param y
     * @return true when the coord exists on the playing field
     */
    private boolean isOnMap(int x, int y) {
        if (x >= 0 && y >= 0 && x < width && y < height)
            return true;
        logger.info("~~off map coordinate was accessed~~");
        return false;
    }

    /**
     * determine if the coordinate is in the map
     *
     * @param coord the position to look
     * @return true when the coord exists on the playing field
     */
    private boolean isOnMap(Coordinate coord) {
        return isOnMap(coord.getX(), coord.getY());
    }

    /**
     * find if the given coord is a wall
     *
     * @param coord
     * @return true when the coord is a wall
     */
    private boolean isWall(Coordinate coord) {
        if (isOnMap(coord)) {
            return map[coord.getX()][coord.getY()].equals(TileType.Wall);
        }
        return true;
    }

    /**
     * updates the given tile on the map
     *
     * @param tile the tile to update on the map
     */
    private void updateTile(Tile tile) {
        if (isOnMap(tile.getCoordinate())) {
            map[tile.getCoordinate().getX()][tile.getCoordinate().getY()] = tile.getTileType();
        }
    }

    /**
     * updates the given tile on the map
     *
     * @param x
     * @param y
     * @param tileType the tile to update on the map
     */
    private void updateTile(int x, int y, TileType tileType) {
        if (isOnMap(x, y)) {
            map[x][y] = tileType;
        }
    }

    /**
     * drops apples on the given tiles
     *
     * @param area where to place the apples
     */
    public void appleBomb(List<Tile> area) {
        for (Tile t :
                area) {
            int x = t.getCoordinate().getX();
            int y = t.getCoordinate().getY();
            if (isOnMap(x, y) && !isWall(t.getCoordinate()))
                updateTile(x, y, TileType.Apple);
        }
    }

    /**
     * remove all the snakes from the map and redraw it
     */
    public void reset() {
        for (Snake snake :
                pileOfSnakes) {
            snake.endSnake();

        }
        pileOfSnakes.clear();
        drawStarterMap();
    }
}
