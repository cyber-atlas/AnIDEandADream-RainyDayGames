package edu.iastate.IDE_AND_A_DREAM.Snake_Multiplayer.Snake_Object;

import com.google.gson.annotations.SerializedName;

import edu.iastate.IDE_AND_A_DREAM.Snake_Multiplayer.Direction;
import edu.iastate.IDE_AND_A_DREAM.Snake_Multiplayer.TileType;
import edu.iastate.IDE_AND_A_DREAM.Snake_Multiplayer.Coordinate;


import java.util.ArrayList;

/**
 * The type Snake.
 */
public class Snake {

    /**
     * The constant spawnHeight.
     */
    public static final int spawnHeight = 5;
    private ArrayList<Tile> snake = new ArrayList<>();
    private String name;
    private Direction dir;

    private Direction queuedDir;

    private Boolean justAte;

    private Boolean isAlive;

    private int score;

    /**
     * Instantiates a new Snake.
     *
     * @param name             the name
     * @param startingLocation the starting location
     */
    public Snake(String name, Coordinate startingLocation) {
        this.queuedDir = Direction.North;
        this.dir = this.queuedDir;
        this.justAte = false;
        this.name = name;
        this.isAlive = true;
        this.score = spawnHeight;
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

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets score.
     *
     * @return the score
     */
    public int getScore()
    {
        return score;
    }

    /**
     * Is alive boolean.
     *
     * @return the boolean
     */
    public boolean isAlive()
    {
        return isAlive;
    }

    /**
     * Is alive debug string.
     *
     * @return the string
     */
    public String isAliveDebug()
    {
        if(isAlive)
        {
            return "true";
        }
        return "false";
    }

    /**
     * Gets snake.
     *
     * @return the snake
     */
    public ArrayList<Tile> getSnake() {
        return snake;
    }

    /**
     * Feed.
     */
    public void feed() {
        justAte = true;
    }

    /**
     * Slither.
     */
//snake slithers in the direction set in the class
    public void slither() {
        if (isAlive) {
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
    }

    /**
     * End snake.
     */
    public void endSnake() {
        for (Tile t :
                snake) {
            t.setTileType(TileType.Apple);
        }
        isAlive = false;
        score = snake.size();
    }

    /**
     * Update direction.
     *
     * @param nextDir the next dir
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

        //used queue instead of boolean latch to allow most recent move before tick to be accepted
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

}
