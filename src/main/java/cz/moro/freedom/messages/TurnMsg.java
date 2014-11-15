package cz.moro.freedom.messages;



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
            
    
}
