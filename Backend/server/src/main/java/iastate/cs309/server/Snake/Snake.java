package iastate.cs309.server.Snake;

import iastate.cs309.server.Snake.SnakeEnums.Direction;
import iastate.cs309.server.Snake.SnakeEnums.TileType;

import java.util.ArrayList;

/**
 * Snake contains location details on where to draw a snake
 * Handles moving, feeding and dying
 */
public class Snake {
    public static final int spawnHeight = 5;
    public transient Boolean desireRespawn;
    protected ArrayList<Tile> snake = new ArrayList<>();
    protected String name;
    protected Direction dir;
    protected Direction queuedDir;
    protected Boolean justAte;
    protected Boolean isAlive;
    protected int score;

    public Snake(String name, Coordinate startingLocation) {
        this.queuedDir = Direction.North;
        this.dir = this.queuedDir;
        this.justAte = false;
        this.name = name;
        this.isAlive = true;
        this.score = spawnHeight;
        this.desireRespawn = false;

        //spawn the snake parts
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


    /**
     * Corrects placement of snake parts
     */
    protected void beautifySnake() {
        for (int i = 0; i < snake.size(); i++) {
            Tile curTile = snake.get(i);
            int endTail = snake.size() - 1;
            if (i == 0)
                curTile.setTileType(TileType.SnakeHead);
            else if (i == endTail)
                curTile.setTileType( TileType.Nothing); // paint a trail of nothing behind the snake
            else
                curTile.setTileType( TileType.SnakeTail);
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

    /**
     * Ticks the snake one spot forward in the direction set by its member variable.
     * Brings the tail bits along with it
     */
    public void slither() {
        if (isAlive) {
            //pop directional queue
            dir = queuedDir;
            shuffleTowardHead();

            //Need a second check to isalive because shuffle could kill the snake
            if (isAlive) {
                moveHead(dir);
            }
        }
    }

    /**
     * Moves the head of the snake in the direction given
     * @param direction North, South, East, or West
     */
    protected void moveHead(Direction direction) {
        Coordinate head = snake.get(0).getCoordinate();
        switch (direction) {
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

    /**
     * prepares the snake object for death. it shrivels on the sidewalk
     */
    public void endSnake() {
        isAlive = false;
        coldWater();
    }

    /**
     * Validates directional movement to prevent suicide
     * @param nextDir North, south, east or west
     */
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

    /**
     * Move each body part from tail to head
     */
    protected void shuffleTowardHead() {
        if (!isAlive)
            return;
        if (justAte) {
            snake.add(new Tile(new Coordinate(0, 0), TileType.SnakeTail)); //Position doesn't matter; will get reset in the following loop
            score = snake.size() - 1;
            justAte = false;
        }
        for (int seg = snake.size() - 1; seg > 0; seg--) {
            Coordinate bodyPart = snake.get(seg).getCoordinate();
            Coordinate nextBodyPart = snake.get(seg - 1).getCoordinate();
            bodyPart.setX(nextBodyPart.getX());
            bodyPart.setY(nextBodyPart.getY());
        }
        beautifySnake();
    }

    /**
     * Recreates the snake object from nearly scratch
     * @param spawn The location the snake should respawn at
     */
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

    /**
     * *the snake shrivels from the harsh conditions*
     */
    public void coldWater() {
        snake.clear();
    }
}
