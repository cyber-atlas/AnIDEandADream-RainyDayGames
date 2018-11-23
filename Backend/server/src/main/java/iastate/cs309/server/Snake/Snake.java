package iastate.cs309.server.Snake;

import iastate.cs309.server.Snake.SnakeEnums.Direction;
import iastate.cs309.server.Snake.SnakeEnums.TileType;

import java.lang.invoke.SwitchPoint;
import java.util.ArrayList;

public class Snake {

    public static final int spawnHeight = 5;
    private ArrayList<Coordinate> snake = new ArrayList<>();
    private ArrayList<Tile> snek = new ArrayList<>();
    private Direction dir;

    private Direction queuedDir;

    private Boolean justAte;

    public void Snake(Coordinate startingLocation) {
        snake.clear();

        for (int offset = 0; offset < spawnHeight; offset++) {
            snake.add(new Coordinate(startingLocation.getX() + offset, startingLocation.getY()));
        }
        dir = Direction.North;

        for (int offset = 0; offset < spawnHeight; offset++) {
            switch (offset) {
                case 0:
                    snek.add(new Tile(new Coordinate(startingLocation.getX() + offset, startingLocation.getY()), TileType.SnakeHead));
                    break;
                case spawnHeight - 1:
                    snek.add(new Tile(new Coordinate(startingLocation.getX() + offset, startingLocation.getY()), TileType.Nothing)); // paint a trail of nothing behind the snake
                    break;
                default:
                    snek.add(new Tile(new Coordinate(startingLocation.getX() + offset, startingLocation.getY()), TileType.SnakeTail));
            }
        }
    }

    public ArrayList<Coordinate> dumpLocation() {
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

        Coordinate head = snake.get(0);
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
            snake.add(new Coordinate(0, 0)); //Position doesn't matter; will get reset on first
            justAte = false;
            //No need to keep track of a score
        }
        for (int seg = snake.size() - 1; seg > 0; seg--) {
            Coordinate bodyPart = snake.get(seg);
            Coordinate nextBodyPart = snake.get(seg + 1);
            bodyPart.setX(nextBodyPart.getX());
            bodyPart.setY(nextBodyPart.getY());
        }
    }
}
