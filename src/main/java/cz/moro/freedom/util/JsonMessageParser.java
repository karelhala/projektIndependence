package cz.moro.freedom.util;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.moro.freedom.core.MainServer;
import cz.moro.freedom.messages.ChatMsg;
import cz.moro.freedom.messages.ChatMsg.Group;
import cz.moro.freedom.messages.Message;
import cz.moro.freedom.messages.Message.Type;
import cz.moro.freedom.messages.StartGameMsg;
import cz.moro.freedom.messages.TurnMsg;
import cz.moro.freedom.model.Game;
import cz.moro.freedom.model.Team;


public class JsonMessageParser {

    private static final Logger logger = LoggerFactory.getLogger(JsonMessageParser.class);

    public static Message parse(String raw) {
        JSONObject json = null;
        String type = null;
        try {
            json = new JSONObject(raw);
            type = json.getString("type");
        } catch (JSONException e) {
            logger.warn("Cannot parse msg " + raw);
            return null;
        }

        switch (Type.fromString(type)) {
        case CHAT:
            return parseChatMsg(json);
        case TURN:
            return parseTurnMsg(json);
        case START_GAME:
            return parseStartGameMsg(json);
        default:
            return null;
        }
    }

    private static ChatMsg parseChatMsg(JSONObject json) {
        ChatMsg msg = new ChatMsg();
        Group group = Group.fromString(json.getString("to"));
        if (group == null) {
            return null;
        }

        msg.setMsg(json.getString("msg"));
        msg.setGroup(group);

        return msg;
    }

    private static TurnMsg parseTurnMsg(JSONObject json) {
        TurnMsg msg = new TurnMsg();
        msg.setX(json.getInt("x"));
        msg.setY(json.getInt("y"));

        return msg;
    }

    private static StartGameMsg parseStartGameMsg(JSONObject json) {
        StartGameMsg msg = new StartGameMsg();
        Game game = null;

        if (json.has("game")) {
            game = MainServer.getGameById(json.getLong("game"));
        }
        msg.setGame(game);

        if (game != null) {
            if (json.has("team")) {
                Long id = json.getLong("team");
                for (Team team : game.getTeams()) {
                    if (team.getId().equals(id)) {
                        msg.setTeam(team);
                    }
                }
            }
        }

        return msg;
    }
}
