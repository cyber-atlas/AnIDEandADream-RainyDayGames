package iastate.cs309.server.Snake;

import iastate.cs309.server.Snake.SnakeEnums.Direction;
import iastate.cs309.server.Snake.SnakeEnums.TileType;

import java.util.ArrayList;

public class Snake {
    public static final int spawnHeight = 5;
    private ArrayList<Tile> snake = new ArrayList<>();
    private String name;
    private Direction dir;

    private Direction queuedDir;

    private Boolean justAte;

    public Snake(String name, Coordinate startingLocation) {
        dir = Direction.North;
        this.name = name;
        for (int offset = 0; offset <= spawnHeight; offset++) {
            switch (offset) {
                case 0:
                    snake.add(new Tile(new Coordinate(startingLocation.getX() + offset, startingLocation.getY()), TileType.SnakeHead));
                    break;
                case spawnHeight:
                    snake.add(new Tile(new Coordinate(startingLocation.getX() + offset, startingLocation.getY()), TileType.Nothing)); // paint a trail of nothing behind the snake
                    break;
                default:
                    snake.add(new Tile(new Coordinate(startingLocation.getX() + offset, startingLocation.getY()), TileType.SnakeTail));
            }
        }
    }

    public String getName() {
        return name;
    }

    public ArrayList<Tile> getSnake() {
        return snake;
    }

    public void feed() {
        justAte = true;
    }

    //snake slithers in the direction set in the class
    public void slither() {
        //pop directional queue
        dir = queuedDir;
        shuffleTowardHead();

        Coordinate head = snake.get(0).getCoordinate();
        switch (dir) {
            case North:
                head.setY(head.getY() + 1);
                break;
            case South:
                head.setY(head.getY() - 1);
                break;
            case East:
                head.setX(head.getX() + 1);
                break;
            case West:
                head.setX(head.getX() - 1);
                break;
        }
    }

    public int endSnake() {
        for (Tile t :
                snake) {
            t.setTileType(TileType.Apple);
        }
        return snake.size();
    }

    public void updateDirection(Direction nextDir) {
        //Make sure the move is legal
        if ((dir == Direction.North && nextDir == Direction.South)
                || (dir == Direction.South && nextDir == Direction.North)
                || (dir == Direction.West && nextDir == Direction.East)
                || (dir == Direction.East && nextDir == Direction.West))
            return; //Illegal moves should be ignored, snake will continue in original direction

        //ELSE

        //Don't immediately set dir, otherwise people could spam up/down and kill themselves
        queuedDir = nextDir;

        //used queue instead of boolean latch to allow most recent move before tick to be accepted
    }


    private void shuffleTowardHead() {
        //Move each body part from tail to head
        if (justAte) {
            snake.add(new Tile(new Coordinate(0, 0), TileType.SnakeTail)); //Position doesn't matter; will get reset on first
            justAte = false;
        }
        for (int seg = snake.size() - 1; seg > 0; seg--) {
            Coordinate bodyPart = snake.get(seg).getCoordinate();
            Coordinate nextBodyPart = snake.get(seg + 1).getCoordinate();
            bodyPart.setX(nextBodyPart.getX());
            bodyPart.setY(nextBodyPart.getY());
        }
    }
}
