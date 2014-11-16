package cz.moro.freedom.messages;

import org.json.JSONObject;



public class TurnMsg extends Message {

    private int x;
    private int y;
   // private final Player player;
    
    public TurnMsg() {
        super(Message.Type.TURN);
    }

    
    public int getX() {    
        return x;
    }
    
    public void setX(int x) {    
        this.x = x;
    }
    
    public int getY() {    
        return y;
    }
    
    public void setY(int y) {    
        this.y = y;
    }
            
    @Override
    public JSONObject toJson() {
        JSONObject json = super.toJson();
        json.put("x", x);
        json.put("y", y);
        
        return json;
    }
}
