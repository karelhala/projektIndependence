package cz.moro.freedom.messages;

import org.json.JSONObject;

import cz.moro.freedom.model.Team;


public class StartMsg extends Message {
    
    private Team team;

    public StartMsg() {
        super(Message.Type.START);
    }
    
    
    public Team getTeam() {    
        return team;
    }

    public void setTeam(Team team) {    
        this.team = team;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = super.toJson();
        json.put("team", team.getId());
        
        return json;
    }
}
