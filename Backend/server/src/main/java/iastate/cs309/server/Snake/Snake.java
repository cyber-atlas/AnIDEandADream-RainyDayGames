package iastate.cs309.server.Snake;

import iastate.cs309.server.Snake.SnakeEnums.Direction;
import iastate.cs309.server.Snake.SnakeEnums.TileType;

import java.util.ArrayList;

public class Snake {
    public static final int spawnHeight = 5;
    public transient Boolean desireRespawn;
    private ArrayList<Tile> snake = new ArrayList<>();
    private String name;
    private Direction dir;
    private Direction queuedDir;
    private Boolean justAte;
    private Boolean isAlive;
    private int score;

    public Snake(String name, Coordinate startingLocation) {
        this.queuedDir = Direction.North;
        this.dir = this.queuedDir;
        this.justAte = false;
        this.name = name;
        this.isAlive = true;
        this.score = spawnHeight;
        this.desireRespawn = false;
        for (int offset = 0; offset <= spawnHeight; offset++) {
            switch (offset) {
                case 0:
                    snake.add(new Tile(new Coordinate(startingLocation.getX(), startingLocation.getY() + offset), TileType.SnakeHead));
                    break;
                case spawnHeight:
                    snake.add(new Tile(new Coordinate(startingLocation.getX(), startingLocation.getY() + offset), TileType.Nothing)); // paint a trail of nothing behind the snake
                    break;
                default:
                    snake.add(new Tile(new Coordinate(startingLocation.getX(), startingLocation.getY() + offset), TileType.SnakeTail));
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
        if (isAlive) {
            //pop directional queue
            dir = queuedDir;
            shuffleTowardHead();

            //Need a second check to isalive because shuffle could kill the snake
            if (isAlive) {
                Coordinate head = snake.get(0).getCoordinate();
                switch (dir) {
                    case North:
                        head.setY(head.getY() - 1);
                        break;
                    case South:
                        head.setY(head.getY() + 1);
                        break;
                    case East:
                        head.setX(head.getX() + 1);
                        break;
                    case West:
                        head.setX(head.getX() - 1);
                        break;
                }
            }
        }
    }

    public void endSnake() {
        isAlive = false;
        for (Tile t :
                snake) {
            t.setTileType(TileType.Apple);
        }
        score = snake.size();
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
    }

    private void shuffleTowardHead() {
        //Move each body part from tail to head
        if (justAte) {
            snake.add(new Tile(new Coordinate(0, 0), TileType.SnakeTail)); //Position doesn't matter; will get reset on first
            score = snake.size();
            justAte = false;
        }
        for (int seg = snake.size() - 1; seg > 0; seg--) {
            Coordinate bodyPart = snake.get(seg).getCoordinate();
            Coordinate nextBodyPart = snake.get(seg - 1).getCoordinate();
            bodyPart.setX(nextBodyPart.getX());
            bodyPart.setY(nextBodyPart.getY());
        }
    }

    public void respawn(Coordinate spawn) {
        Snake s = new Snake(name, spawn);
        this.snake = s.snake;
        this.dir = s.dir;
        this.queuedDir = s.queuedDir;
        this.isAlive = s.isAlive;
        this.justAte = s.justAte;
        this.name = s.name;
        this.score = s.score;
        desireRespawn = false;
    }
}
