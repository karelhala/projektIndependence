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

import cz.moro.freedom.messages.ChatMsg;
import cz.moro.freedom.messages.Message;
import cz.moro.freedom.messages.StartGameMsg;
import cz.moro.freedom.messages.TurnMsg;
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
     * @throws Exception 
     */
    @OnMessage
    public void onMessage(String message, Session session) throws Exception{
        logger.debug("Message from " + session.getId() + ": " + message);

        Player player = players.get(session.getId());
        if(player == null) {
            throw new Exception("Player with ID " + session.getId() + " not found");
        }
        
        Message msg = JsonMessageParser.parse(message);
        
        switch(msg.getType()) {
            case START_GAME:
                startGame((StartGameMsg) msg);
                break;
            case CHAT:
                chat((ChatMsg)msg);
                break;
            case TURN:
                turn((TurnMsg)msg);
                break;
            default:
                break;
            
        }
        /*
        try {
            for(Session s : sessions) {
                s.getBasicRemote().sendText(message);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }*/
    }
    
    private void startGame(StartGameMsg msg) {
        
    }
    
    private void chat(ChatMsg msg) {
        
    }
    
    private void turn(TurnMsg msg) {
        
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
