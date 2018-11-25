package iastate.cs309.server.Snake;

import com.google.common.collect.HashBiMap;
import com.google.gson.Gson;
import iastate.cs309.server.Chat.Message;
import iastate.cs309.server.Snake.SnakeEnums.Direction;
import iastate.cs309.server.Snake.SnakeEnums.TileType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@ServerEndpoint(value = "/snake/{username}")
public class SnakeEndpoint {
    private static Set<SnakeEndpoint> snakeEndpoints
            = new CopyOnWriteArraySet<>();
    private static Logger logger = LoggerFactory.getLogger(iastate.cs309.server.Chat.RudeEndpoint.class);
    private Session session;
    private HashMap<String, Snake> sessionSnakes = new HashMap<>();
    private Map map = new Map();
    private Gson gson = new Gson();
    private boolean isRunning;

    private void broadcast(Message message)
            throws IOException, EncodeException {

        snakeEndpoints.forEach(endpoint -> {
            synchronized (endpoint) {
                try {
                    endpoint.session.getBasicRemote().
                            sendText(gson.toJson(map));
                } catch (IOException e) {
                    e.printStackTrace();
                    logger.error(e.getMessage());
                }
            }
        });
    }

    public Map getMap() {
        return map;
    }

    private void run() {
        if (!isRunning) {
            Thread t = new Thread(map);
            t.start();
            isRunning = true;
        }
    }

    @OnOpen
    public void onOpen(
            Session session,
            @PathParam("username") String username) throws IOException, EncodeException {

        this.session = session;
        snakeEndpoints.add(this);
        Snake snake = new Snake(username, map.findSnakeSpawn());
        sessionSnakes.put(session.getId(), snake);
        map.addSnake(snake);

        //When the first snake connects run.
        run();


        logger.info(username + " connected");
    }

    @OnMessage
    public void onMessage(Session session, String message)
            throws IOException, EncodeException {

        //Todo: this but in json
        //format: username dir <N/S/E/W>
        if (message.contains("dir")) {
            String[] parts = message.split("\\s");
            switch (parts[2]) {
                case "N":
                    sessionSnakes.get(session.getId()).updateDirection(Direction.North);
                    break;
                case "S":
                    sessionSnakes.get(session.getId()).updateDirection(Direction.South);
                    break;
                case "E":
                    sessionSnakes.get(session.getId()).updateDirection(Direction.East);
                    break;
                case "W":
                    sessionSnakes.get(session.getId()).updateDirection(Direction.West);
                    break;
            }
        }
    }

    @OnClose
    public void onClose(Session session) throws IOException, EncodeException {
        sessionSnakes.get(session.getId()).endSnake();
        snakeEndpoints.remove(this);


        if (sessionSnakes.size()==0)
        {
            //cleanup the game
        };
        //broadcast(message);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        logger.error(session.toString());
        logger.error(throwable.getMessage());
    }

}


