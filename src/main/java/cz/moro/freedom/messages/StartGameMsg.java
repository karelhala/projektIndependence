package cz.moro.freedom.messages;

import org.json.JSONObject;


public class StartGameMsg extends Message {

    public StartGameMsg() {
        super(Message.Type.START_GAME);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = super.toJson();
        
        return json;
    }
}
