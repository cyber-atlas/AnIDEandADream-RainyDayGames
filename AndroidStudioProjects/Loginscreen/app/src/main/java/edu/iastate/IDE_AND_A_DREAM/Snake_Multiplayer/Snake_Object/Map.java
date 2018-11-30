package edu.iastate.IDE_AND_A_DREAM.Snake_Multiplayer.Snake_Object;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.iastate.IDE_AND_A_DREAM.Snake_Multiplayer.TileType;
import edu.iastate.IDE_AND_A_DREAM.Snake_Multiplayer.Coordinate;
import edu.iastate.IDE_AND_A_DREAM.Snake_Multiplayer.TileType;


public class Map {

    private static final int width = 42;
    private static final int height = 42;
    private TileType[][] map; //could convert to tile object
    private List<Snake> pileOfSnakes = new ArrayList<>();
    private transient Random psudo = new Random();

    public Map() {
        map = new TileType[width][height];
        //drawStarterMap();
    }


    public void addSnake(Snake snake) {
        pileOfSnakes.add(snake);
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
        return countTiles(TileType.Apple) < pileOfSnakes.size() * 3;
    }

    private void spawnFood() {
        //Throw a random dart at the gameboard; put food there if you didn't poke a thing
        int dartX = Math.abs(psudo.nextInt()) % width;
        int dartY = Math.abs(psudo.nextInt()) % height;
        if (map[dartX][dartY].equals(TileType.Nothing)) {
            map[dartX][dartY] = TileType.Apple;
        }
    }

    //Simple border maximizing playable area
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

    private void drawSnakes() {
        for (Snake snake :
                pileOfSnakes) {
            List<Tile> p = snake.getSnake();
            for (int seg = 0; seg < p.size(); seg++) {
                Tile snakeTile = p.get(seg);
                int segX = snakeTile.getCoordinate().getX();
                int segY = snakeTile.getCoordinate().getY();

                if (isOnMap(segX, segY)) {
                    switch (map[segX][segY]) {
                        case Nothing:
                            updateTile(snakeTile);
                            break;
                        case Apple:
                            snake.feed();
                            updateTile(snakeTile);
                            break;
                        case Wall:
                        case SnakeHead:
                        case SnakeTail:
                            //kill the snake if its head is in another block
                            if (seg == 0)
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

    private boolean isOnMap(Coordinate coord) {
        if (coord.getX() >= 0 && coord.getY() >= 0 && coord.getX() < width && coord.getY() < height)
            return true;
        return false;
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



}
