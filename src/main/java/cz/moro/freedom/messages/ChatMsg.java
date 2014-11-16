package cz.moro.freedom.messages;

import org.json.JSONObject;




public class ChatMsg extends Message {

    private Group group;
    private String msg;
    
    public enum Group {
        ALL,
        GAME,
        TEAM;
        
        public static Group fromString(String name) {
            for(Group group : values()){
                if(group.toString().equals(name)){
                    return group;
                }
            }
            return null;
        }
    }
    
    public ChatMsg() {
        super(Message.Type.CHAT);
    }
    
    public Group getGroup() {    
        return group;
    }
    
    public void setGroup(Group group) {    
        this.group = group;
    }
    
    public String getMsg() {    
        return msg;
    }
    
    public void setMsg(String msg) {    
        this.msg = msg;
    }
    
    @Override
    public JSONObject toJson() {
        JSONObject json = super.toJson();
        json.put("to", group);
        json.put("msg", msg);
        
        return json;
    }
    
    

}
