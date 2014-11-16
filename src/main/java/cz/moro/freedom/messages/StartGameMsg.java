package cz.moro.freedom.messages;


import org.json.JSONObject;

import cz.moro.freedom.model.Game;
import cz.moro.freedom.model.Team;



public class StartGameMsg extends Message {
    
    private Game game;
    
    private Team team;

    public StartGameMsg() {
        super(Message.Type.START_GAME);
    }


    @Override
    public JSONObject toJson() {
        JSONObject json = super.toJson();
        
        return json;
    }

    
    public Game getGame() {
        return game;
    }

    
    public void setGame(Game game) {
        this.game = game;
    }

    
    public Team getTeam() {
        return team;
    }

    
    public void setTeam(Team team) {
        this.team = team;
    }


}
