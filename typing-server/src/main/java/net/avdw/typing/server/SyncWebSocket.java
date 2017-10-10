package net.avdw.typing.server;

import net.avdw.typing.message.AMessage;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import net.avdw.typing.message.OpenGamesMessage;
import net.avdw.typing.message.SetupMessage;

@ServerEndpoint("/sync")
public class SyncWebSocket {

    private static final Queue<Session> SESSIONS = new ConcurrentLinkedQueue();
    private static final Gson SERIALISER = new Gson();
    private static List<String> openGames = new ArrayList();

    @OnMessage
    public void onMessage(Session session, String msg) {
        try {
            System.out.println("<-" + session.getId() + " " + msg);
            AMessage aMessage = SERIALISER.fromJson(msg, AMessage.class);
            switch (aMessage.type) {
                case "OpenGamesMessage": {
                    OpenGamesMessage message = SERIALISER.fromJson(aMessage.message.toString(), OpenGamesMessage.class);
                    message.openGames = openGames;
                    Comm.send(session, message);
                    break;
                }
                case "SetupMessage": {
                    SetupMessage message = SERIALISER.fromJson(aMessage.message.toString(), SetupMessage.class);
                    message.excerpt = "A depressed man (Edward Norton) suffering from insomnia meets a strange soap salesman named Tyler Durden (Brad Pitt) and soon finds himself living in his squalid house after his perfect apartment is destroyed. The two bored men form an underground club with strict rules and fight other men who are fed up with their mundane lives. Their perfect partnership frays when Marla (Helena Bonham Carter), a fellow support group crasher, attracts Tyler's attention.";

                    Comm.send(session, message);
                    break;
                }
            }
        }
        catch (JsonSyntaxException | IOException e) {
            e.printStackTrace();
        }

    }

    @OnOpen
    public void open(Session session) throws IOException {
        SESSIONS.add(session);
        System.out.println("o-" + session.getId());
    }

    @OnError
    public void error(Session session, Throwable t) {
        SESSIONS.remove(session);
        System.err.println("e-" + session.getId());
    }

    @OnClose
    public void closedConnection(Session session) {
        SESSIONS.remove(session);
        System.out.println("x-" + session.getId());
    }
}
