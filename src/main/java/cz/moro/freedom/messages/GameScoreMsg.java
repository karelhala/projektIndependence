package cz.moro.freedom.messages;

import java.util.List;

import org.json.JSONObject;

import cz.moro.freedom.model.Team;
import cz.moro.freedom.service.ScoreCounter.Score;


public class GameScoreMsg extends Message {

    private List<Team> teams;

    private List<Score> scores;

    public GameScoreMsg() {
        super(Type.SCORE);
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public List<Score> getScores() {
        return scores;
    }

    public void setScores(List<Score> scores) {
        this.scores = scores;
    }

    @Override
    public JSONObject toJson() {
       JSONObject jsonObject = super.toJson();
       
       jsonObject.put("teams", teams);
       jsonObject.put("scores", scores);
       
       return jsonObject;
    }

}
