package cz.moro.freedom.messages;

import org.json.JSONObject;

import cz.moro.freedom.model.Player;



public class Message {
    
    protected Type type;
    protected Player player;

    public enum Type {
        START_GAME,
        START,
        END,
        TURN,
        CHAT;
        
        public static Type fromString(String name) {
            for(Type type : values()){
                if(type.toString().equals(name)){
                    return type;
                }
            }
            return null;
        }
    }
    

    protected Message(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }
    
    public Player getPlayer() {    
        return player;
    }
    
    public void setPlayer(Player player) {    
        this.player = player;
    }
    
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("type", type);
        if(player!= null) {
            json.put("player", player.getId());
        }
        
        return json;
    }
}
