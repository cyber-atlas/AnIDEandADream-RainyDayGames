package iastate.cs309.server.Snake;

import iastate.cs309.server.Snake.SnakeEnums.TileType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Map {
    private static final int width = 42;
    private static final int height = 42;
    private TileType[][] map; //could convert to tile object
    private List<Snake> pileOfSnakes = new ArrayList<>();
    private Random psudo = new Random();

    public void Map() {
        map = new TileType[width][height];
        drawBorders();
    }

    public void addSnake()

    //finds a place with a contiguous snake sized spawn area
    private Coordinate findSnakeSpawn() {
        int dartX = psudo.nextInt() % width;
        // pad the top and bottom snake heights to prevent spawning into a wall 1 move after spawning
        int dartY = psudo.nextInt() % (height - (2 * Snake.spawnHeight)) + Snake.spawnHeight;

        if (isNTallNothing(dartX, dartY, Snake.spawnHeight))
            return new Coordinate(dartX, dartY);

        //dart didn't return anything, throw another;
        return findSnakeSpawn();
    }

    private boolean isNTallNothing(int x, int y, int n) {
        for (int i = 0; i < n; i++) {
            if (!map[x][y + i].equals(TileType.Nothing))
                return false;
        }
        return true;
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

    private void spawnFood() {
        //Throw a random dart at the gameboard; put food there if you didn't poke a thing
        int dartX = psudo.nextInt() % width;
        int dartY = psudo.nextInt() % height;
        if (map[dartX][dartY].equals(TileType.Nothing)) {
            map[dartX][dartY] = TileType.Apple;
        }
    }

    //Simple border maximizing playable area
    private void drawBorders() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (x == 0 || y == 0 || x == width - 1 || y == height - 1) {
                    updateTile(x, y, TileType.Wall);
                }
            }
        }
    }

    private void drawSnake() {
        for (Snake snake :
                pileOfSnakes) {
            List<Tile> p = snake.getSnek();
            for (int seg = 0; seg < p.size(); seg++) {
                Tile segSnek = p.get(seg);
                int segX = segSnek.getCoordinate().getX();
                int segY = segSnek.getCoordinate().getY();

                if (isOnMap(segX, segY)) {
                    switch (map[segX][segY]) {
                        case Nothing:
                            updateSnakeTile(segX, segY, seg);
                            break;
                        case Apple:
                            snake.feed();
                            updateSnakeTile(segX, segY, seg);
                            break;
                        case Wall:
                        case SnakeHead:
                        case SnakeTail:
                            //also tell the client he died
                            snake.endSnake();
                    }
                } else {
                    snake.endSnake();
                }
            }
        }
    }

    private boolean isOnMap(int x, int y) {
        if (x >= 0 && y >= 0 && x < width && y < height)
            return true;
        return false;
    }

    //wrapper for map
    private void updateTile(int x, int y, TileType tileType) {
        if (isOnMap(x, y)) {
            map[x][y] = tileType;
        }
    }

    //wrapper for map
    private void updateSnakeTile(int x, int y, int seg) {
        if (seg == 0)
            updateTile(x, y, TileType.SnakeHead);
        else
            updateTile(x, y, TileType.SnakeTail);
    }
}
