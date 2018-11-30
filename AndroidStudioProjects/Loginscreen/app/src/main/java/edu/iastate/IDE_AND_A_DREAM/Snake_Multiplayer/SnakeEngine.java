package edu.iastate.IDE_AND_A_DREAM.Snake_Multiplayer;

import android.widget.TextView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class SnakeEngine  {

    public static final int GameWidth = 28;
    public static final int GameHeight = 42;


    //JSONArray Js =

    private List<Coordinate> walls = new ArrayList<>();
    private List<Coordinate> snake = new ArrayList<>();
    private List<Coordinate> monster = new ArrayList<>();

    private List<Coordinate> apples = new ArrayList<>();

    private Random random = new Random();
    private Random monNum = new Random();

    private boolean increaseTail = false;

    private Direction currentDirection = Direction.East;

    private GameState currentGameState = GameState.Running;

    int score = 0;

    private Coordinate getSnakeHead(){
        return snake.get(0);
    }

    public SnakeEngine(){}

    public void initGame(int monst){
        Addsnake();
        AddWalls();
        AddApples();
        AddMonster( monst);
    }

    public void updateDirection(Direction newDirection){

        if(Math.abs(newDirection.ordinal() - currentDirection.ordinal()) % 2 == 1){
            currentDirection = newDirection;
        }
    }

    public void Update(){

        //UMA ABu update the snale
        switch (currentDirection) {
            case North:
                updateSnake(0,-1);
                break;
            case East:
                updateSnake(1,0);
                break;
            case South:
                updateSnake(0, 1);
                break;
            case West:
                updateSnake(-1, 0);
                break;
        }

        //Check collision

        for(Coordinate w : walls){
            if(snake.get(0).equals(w)){
                currentGameState = GameState.Lost;
            }
        }

        for(Coordinate x : monster){
            if(snake.get(0).equals(x)){
                currentGameState = GameState.Lost;
            }
        }


        //SefColusuon
        for(int i = 1; i < snake.size(); i ++){

            if(getSnakeHead().equals(snake.get(i))){
                currentGameState = GameState.Lost;
                return;
            }
        }


        Coordinate appleToRemove = null;
        for(Coordinate apple: apples){
            if(getSnakeHead().equals(apple)){

                appleToRemove = apple;
                increaseTail = true;
            }
        }
        if (appleToRemove != null){
            apples.remove(appleToRemove);
            AddApples();
        }

    }

    public TileType[][] getmap(){
        TileType[][] map =  new TileType[GameWidth][GameHeight];

        for(int x = 0; x < GameWidth;x++){
            for(int y = 0; y < GameHeight; y++){
                map[x][y] = TileType.Nothing;
            }
        }

        for (Coordinate s : snake) {
            map[s.getX()][s.getY()] = TileType.SnakeTail;
        }

        for(Coordinate a : apples){
            map[a.getX()][a.getY()] = TileType.Apple;
        }

        map[snake.get(0).getX()][snake.get(0).getY()] = TileType.SnakeHead;

        for (Coordinate wall: walls){
            map[wall.getX()][wall.getY()] = TileType.Wall;
        }

        for ( Coordinate mon : monster){
            map[mon.getX()][mon.getY()] = TileType.Monster;
        }
        return map;
    }


    private void AddWalls() {
        //Adding walls to top and buttom walls
        for (int i = 0; i < GameWidth; i++) {
            walls.add(new Coordinate(i,0));
            walls.add(new Coordinate(i,GameHeight-1 ));
        }

        //Adding Left and Right walls
        for (int y = 0; y < GameHeight; y++) {
            walls.add(new Coordinate(0,y));
            walls.add(new Coordinate(GameWidth-1,y));
        }
    }

    private void updateSnake(int x, int y){

        int newX = snake.get(snake.size()-1).getX();
        int newY = snake.get(snake.size()-1).getY();


        for (int i = snake.size()-1; i > 0; i--) {
            snake.get(i).setX(snake.get(i-1).getX());
            snake.get(i).setY(snake.get(i-1).getY());
        }

        if (increaseTail){
            snake.add(new Coordinate(newX, newY));
            increaseTail = false;
            //SnakeMainActivity.upd
            score ++;
        }

        snake.get(0).setX(snake.get(0).getX() +x);
        snake.get(0).setY(snake.get(0).getY() +y);

    }

    private void Addsnake(){

        snake.clear();

        snake.add(new Coordinate(7,7));
        snake.add(new Coordinate(6,7));
        snake.add(new Coordinate(5,7));
        snake.add(new Coordinate(4,7));
        snake.add(new Coordinate(3,7));
        snake.add(new Coordinate(2,7));
    }

    public void AddMonster(int num){
        for (int i = 0; i < num; i++)
        {
            monster.add(new Coordinate(1 + monNum.nextInt(GameWidth - 2), 1 + monNum.nextInt(GameHeight - 2)));
        }
    }

    private void AddApples(){
        Coordinate coordinate = null;

        boolean added = false;

        while(!added){
            int x = 1 + random.nextInt(GameWidth - 2);
            int y = 1 + random.nextInt(GameHeight - 2);

            coordinate = new Coordinate(x, y);
            boolean collision = false;
            for(Coordinate s : snake){
                if(s.equals(coordinate)){
                    collision = true;
//                    break;
                }
            }

            for (Coordinate a : apples){
                if(a.equals((coordinate))){
                    collision = true;
                    break;
                }
            }
            added = !collision;
        }
        apples.add(coordinate);
    }


    public GameState getCurrentGameState(){
        return currentGameState;
    }

}
