package cz.moro.freedom.messages;



public class Message {
    
    protected Type type;

    public enum Type {
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
    
}
