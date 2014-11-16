package cz.moro.freedom.util;

import org.json.JSONObject;

import cz.moro.freedom.messages.ChatMsg;
import cz.moro.freedom.messages.ChatMsg.Group;
import cz.moro.freedom.messages.Message;
import cz.moro.freedom.messages.Message.Type;
import cz.moro.freedom.messages.TurnMsg;


public class JsonMessageParser {
    
    public static Message parse(String raw) {
        JSONObject json = new JSONObject(raw);
        String type = json.getString("type");
        
        switch(Type.fromString(type)) {
            case CHAT:
                return parseChatMsg(json);
            case TURN:
                return parseTurnMsg(json);
            default:
                return null;
        }        
    }
    
    private static ChatMsg parseChatMsg(JSONObject json) {
        ChatMsg msg = new ChatMsg();
        msg.setMsg(json.getString("msg"));
        msg.setGroup(Group.fromString(json.getString("to")));
        
        return msg;
    }
    
    private static TurnMsg parseTurnMsg(JSONObject json) {
        TurnMsg msg = new TurnMsg();
        msg.setX(json.getInt("x"));
        msg.setY(json.getInt("y"));
        
        return msg;
    }
}
