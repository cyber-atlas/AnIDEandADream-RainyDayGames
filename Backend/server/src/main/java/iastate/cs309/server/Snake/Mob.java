package iastate.cs309.server.Snake;

import iastate.cs309.server.Snake.SnakeEnums.Direction;

import javax.print.DocFlavor;
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
            Integer iDir = Math.abs(psudo.nextInt()) % 6;
            shuffleTowardHead();

            //Need a second check to isalive because shuffle could kill the snake
            if (isAlive) {
                Coordinate head = snake.get(0).getCoordinate();
                switch (iDir) {
                    case 0:
                        head.setY(head.getY() - 1);
                        break;
                    case 1:
                        head.setY(head.getY() + 1);
                        break;
                    case 2:
                        head.setX(head.getX() + 1);
                        break;
                    case 3:
                        head.setX(head.getX() - 1);
                        break;
                    default:
                            switch (dir)
                            {
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
        } else {
            desireRespawn = true;
        }
    }


}
