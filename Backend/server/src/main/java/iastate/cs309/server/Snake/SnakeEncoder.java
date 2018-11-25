package iastate.cs309.server.Snake;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import java.io.IOException;
import java.io.Writer;

public class SnakeEncoder implements Encoder.TextStream<Snake> {

    @Override
    public void init(EndpointConfig config) {}

    @Override
    public void encode(Snake snake, Writer writer) throws EncodeException, IOException {
        Gson gson = new Gson();
        writer.write(gson.toJson(snake));
    }

    @Override
    public void destroy() {}
}