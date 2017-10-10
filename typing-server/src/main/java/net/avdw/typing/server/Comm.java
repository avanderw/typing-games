package net.avdw.typing.server;

import com.google.gson.Gson;
import java.io.IOException;
import javax.websocket.Session;
import net.avdw.typing.message.AMessage;

public class Comm {
    private static final Gson SERIALISER = new Gson();
    
    public static void send(Session session, Object message) throws IOException {
        String msg = SERIALISER.toJson(new AMessage(message));
        System.out.println("->"+session.getId() + " " + msg);
        session.getBasicRemote().sendText(msg);
    }
}
