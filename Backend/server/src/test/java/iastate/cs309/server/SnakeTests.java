package iastate.cs309.server;

import com.google.gson.Gson;
import iastate.cs309.server.Snake.Coordinate;
import iastate.cs309.server.Snake.Snake;
import org.junit.Test;

public class SnakeTests {

    @Test
    public void firstTest(){
        Snake snake = new Snake("jim", new Coordinate(0,0));
        Gson gson = new Gson();

        System.out.println(gson.toJson(snake));
    }
}
