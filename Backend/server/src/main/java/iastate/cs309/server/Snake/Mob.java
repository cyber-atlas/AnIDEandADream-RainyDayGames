package iastate.cs309.server.Snake;

import iastate.cs309.server.Snake.SnakeEnums.Direction;

import java.util.Random;

public class Mob extends Snake {

    protected transient Random psudo = new Random();

    public Mob(String name, Coordinate coord) {
        super(name, coord);
    }

    //snake slithers in the direction set in the class
    @Override
    public void slither() {
        if (isAlive) {
            //pop directional queue
            Integer iDir = Math.abs(psudo.nextInt()) % 5;
            shuffleTowardHead();

            //Need a second check to isalive because shuffle could kill the snake
            if (isAlive) {
                Coordinate head = snake.get(0).getCoordinate();
                switch (iDir) {
                    case 0:
                        updateDirection(Direction.North);
                        break;
                    case 1:
                        updateDirection(Direction.South);
                        break;
                    case 2:
                        updateDirection(Direction.East);
                        break;
                    case 3:
                        updateDirection(Direction.West);
                        break;
                }
                moveHead(dir);
            }
        } else {
            desireRespawn = true;
        }
    }


}
