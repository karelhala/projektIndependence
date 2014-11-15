package cz.moro.freedom.core;

import java.io.IOException;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.moro.freedom.messages.Message;
import cz.moro.freedom.model.Player;
import cz.moro.freedom.util.JsonMessageParser;

@ServerEndpoint("/server")
public class MainServer {
    
    private final Logger logger = LoggerFactory.getLogger(MainServer.class);
    
    private static Queue<Session> sessions = new ConcurrentLinkedQueue<>();
    private static Map<String, Player> players = new ConcurrentHashMap<>();
    
    /**
     * @OnOpen allows us to intercept the creation of a new session.
     * The session class allows us to send data to the user.
     * In the method onOpen, we'll let the user know that the handshake was 
     * successful.
     */
    @OnOpen
    public void onOpen(Session session){
        logger.debug(session.getId() + " has opened a connection");
        
        initPlayer(session);
        
        try {
            session.getBasicRemote().sendText("Connection Established");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
 
    /**
     * When a user sends a message to the server, this method will intercept the message
     * and allow us to react to it. For now the message is read as a String.
     */
    @OnMessage
    public void onMessage(String message, Session session){
        logger.debug("Message from " + session.getId() + ": " + message);
        
        Message msg = JsonMessageParser.parse(message);
        
        switch(msg.getType()) {
            case CHAT:
                break;
            case TURN:
                break;
            default:
                break;
            
        }
        
        try {
            for(Session s : sessions) {
                s.getBasicRemote().sendText(message);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
 
    
    /**
     * The user closes the connection.
     * 
     * Note: you can't send messages to the client from this method
     */
    @OnClose
    public void onClose(Session session){
        sessions.remove(session);
        logger.debug("Session " +session.getId()+" has ended");
    }
    
    private void initPlayer(Session session) {
        sessions.add(session);
        Player player = new Player(session.getId());
        players.put(player.getId(), player);
    }
}
