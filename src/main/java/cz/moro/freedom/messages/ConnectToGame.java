package cz.moro.freedom.messages;

import org.json.JSONObject;

import cz.moro.freedom.model.Game;
import cz.moro.freedom.model.Player;


public class ConnectToGame extends Message {

    private Game game;
        
    public ConnectToGame(Player player, Game game) {
        super(Message.Type.CONNECT_TO_GAME);
        
        this.player = player;
        this.game = game;
    }
    
    @Override
    public JSONObject toJson() {
        JSONObject json = super.toJson();
        game.toJson(json);
        json.put("playerTeam", player.getTeam().getId());
        
        return json;
    }
}
