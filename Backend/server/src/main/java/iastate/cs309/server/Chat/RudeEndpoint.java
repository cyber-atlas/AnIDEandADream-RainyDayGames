package iastate.cs309.server.Chat;

import com.google.common.collect.HashBiMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@ServerEndpoint(value = "/rude/{username}")
public class RudeEndpoint {
    private Session session;
    private static Set<RudeEndpoint> chatEndpoints
            = new CopyOnWriteArraySet<>();
    private static HashBiMap<String, String> users = HashBiMap.create();
    private static Logger logger = LoggerFactory.getLogger(RudeEndpoint.class);

    private static void broadcast(Message message)
            throws IOException, EncodeException {

        chatEndpoints.forEach(endpoint -> {
            synchronized (endpoint) {
                try {
                    endpoint.session.getBasicRemote().
                            sendText(message.getFrom() + ":" + message.getContent());
                } catch (IOException e) {
                    e.printStackTrace();
                    logger.error(e.getMessage());
                }
            }
        });
    }

    private static void sendMessageToAnotherUser(Session session, Message msg) {
        try {
            String toId = users.inverse().get(msg.getTo());
            chatEndpoints.forEach(endpoint -> {
                synchronized (endpoint) {
                    try {
                        //Find the target and send the message to them.
                        if (endpoint.session.getId() == toId) {
                            endpoint.session.getBasicRemote().
                                    sendText(msg.getFrom() + ":" + msg.getContent());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        logger.error(e.getMessage());
                    }
                }
            });
            //Duplicate message for sender's chat log
            session.getBasicRemote().sendText(msg.getTo() + "~" + msg.getFrom() + ": " + msg.getContent());
        } catch (IOException e) {
            logger.info("Exception: " + e.getMessage().toString());
            e.printStackTrace();
        }
    }

    @OnOpen
    public void onOpen(
            Session session,
            @PathParam("username") String username) throws IOException, EncodeException {

        this.session = session;
        chatEndpoints.add(this);
        users.put(session.getId(), username);

        Message message = new Message();
        message.setFrom(username);
        message.setContent("Connected!");

        logger.info(message.getFrom() + ": " + message.getContent());
        broadcast(message);
    }

    @OnMessage
    public void onMessage(Session session, String message)
            throws IOException, EncodeException {
        Message msg = new Message();
        msg.setFrom(users.get(session.getId()));
        msg.setContent(message);

        if (message.startsWith("/whisper")) {
            String[] from = message.split(" ");
            msg.setTo(from[1]);
            //update message content to remove syntactic keywords
            msg.setContent(message.replaceFirst("\\(" + from[0] + ")","").replaceFirst("("+from[1]+")",""));
            sendMessageToAnotherUser(session, msg); //todo:implement private messaging
        }else {
            //msg.save(); //todo: determine if we want to preserve message contents
            broadcast(msg);
        }
    }

    @OnClose
    public void onClose(Session session) throws IOException, EncodeException {
        chatEndpoints.remove(this);
        Message message = new Message();
        message.setFrom(users.get(session.getId()));
        message.setContent("Disconnected!");
        broadcast(message);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        logger.error(session.toString());
        logger.error(throwable.getMessage());
    }

}
