package net.avdw.typing.client;

import com.google.gson.Gson;
import java.io.IOException;
import java.net.URI;
import javax.websocket.ClientEndpoint;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import net.avdw.typing.server.message.AMessage;
import net.avdw.typing.server.message.SetupMessage;

@ClientEndpoint
public class GameSyncTestClient {
    private static final Object WAIT_LOCK = new Object();
    private static final Gson SERIALISER = new Gson();
    private static Thread thread;

    @OnOpen
    public void onOpen(Session session) throws IOException {
        session.getBasicRemote().sendText(SERIALISER.toJson(new AMessage(new SetupMessage())));
    }
    
    @OnMessage
    public void onMessage(Session session, String message) throws IOException {
        System.out.println("Received msg: " + message);
    }

    private static void wait4TerminateSignal() {
        GameSyncTestClient.thread = Thread.currentThread();
        synchronized (WAIT_LOCK) {
            try {
                WAIT_LOCK.wait();
            } catch (InterruptedException e) {
            }
        }
    }

    public static void main(String[] args) {
        WebSocketContainer container;
        Session session = null;
        try {
            container = ContainerProvider.getWebSocketContainer();
            session = container.connectToServer(GameSyncTestClient.class, URI.create("ws://localhost:8080/typing-games-server/sync"));
            wait4TerminateSignal();
        } catch (IOException | DeploymentException e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
