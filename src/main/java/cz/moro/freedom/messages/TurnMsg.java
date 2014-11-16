package cz.moro.freedom.messages;

import org.json.JSONObject;

import cz.moro.freedom.model.Game;
import cz.moro.freedom.model.Team;


public class TurnMsg extends Message {

    private int x;
    private int y;

    private Game game;

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

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    public JSONObject toJson() {
        
        JSONObject json = super.toJson();
        
        json.put("x", x);
        json.put("y", y);
        
        Team team = player.getTeam();
        
        if (team != null) {
            json.put("team", team.getId());
        }
        
        if(game != null) {
            json.put("game", game.getId());
        }

        return json;
    }
}
