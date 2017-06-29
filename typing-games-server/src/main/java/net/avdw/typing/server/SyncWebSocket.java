package net.avdw.typing.server;

import net.avdw.typing.server.message.AMessage;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import net.avdw.typing.server.message.SetupMessage;

@ServerEndpoint("/sync")
public class SyncWebSocket {
    private static final Queue<Session> SESSIONS = new ConcurrentLinkedQueue();
    private static final Gson SERIALISER = new Gson();

    @OnMessage
    public void onMessage(Session session, String msg) {
        try {
            System.out.println("Received msg " + msg + " from " + session.getId());
            System.out.println(SERIALISER.fromJson(msg, AMessage.class));
            //session.getBasicRemote().sendText(SERIALISER.toJson(new AMessage(new SetupMessage())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnOpen
    public void open(Session session) throws IOException {
        SESSIONS.add(session);
        session.getBasicRemote().sendText(SERIALISER.toJson(new AMessage(new SetupMessage())));
        System.out.println("New session opened: " + session.getId());
    }

    @OnError
    public void error(Session session, Throwable t) {
        SESSIONS.remove(session);
        System.err.println("Error on session " + session.getId());
    }

    @OnClose
    public void closedConnection(Session session) {
        SESSIONS.remove(session);
        System.out.println("session closed: " + session.getId());
    }
}
