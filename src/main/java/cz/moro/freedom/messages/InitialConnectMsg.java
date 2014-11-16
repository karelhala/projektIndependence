package cz.moro.freedom.messages;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.moro.freedom.core.MainServer;
import cz.moro.freedom.core.handlers.GameHandler;
import cz.moro.freedom.model.Game;


public class InitialConnectMsg extends Message {

    public InitialConnectMsg() {
        super(Message.Type.CONNECT);
    }
    
    @Override
    public JSONObject toJson() {
        JSONObject json = super.toJson();
        
        JSONArray games = createGameListMsg();
        json.put("games", games);
        
        return json;
    }
    
    private JSONArray createGameListMsg() {
        JSONArray json = new JSONArray();
        
        for(GameHandler handler : MainServer.getGameHandlers()) {
            Game game = handler.getGame();            
            JSONObject jsonGame = new JSONObject();
            jsonGame.put("game", game.getId());
            game.toJson(jsonGame);
            json.put(jsonGame);
        }
        
        return json;
    }
}
