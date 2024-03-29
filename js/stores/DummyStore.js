define(['dispatcher/AppDispatcher', 'objectassign', 'eventEmitter', 'actions/ChatActionCreators'], function(Dispatcher, ObjectAssign, EventEmitter, ChatActionCreators) {

	var _games = [];
  var CHANGE_EVENT = "change";
	var CONNECT_TO_GAME = "CONNECT_TO_GAME";
	var START = "START";
	var END = "END";
	var TURN = "TURN";
	var SCORE = "SCORE";
	var turnTeamNumber;
	var turnTime;
	var turnX;
	var turnY;
	var score;
  var chatMessages = [];

  function appendNewChatMessage(newMessage) {
    chatMessages.push(newMessage);
  }


  var DummyStore = ObjectAssign({}, EventEmitter.prototype, {

	  getGames: function(){
		  return _games;
	  },
	  getTurnY: function(){
		  return turnY;
	  },
	  getTurnX: function(){
		  return turnX;
	  },
	  getTurnTeamNumber: function(){
		return turnTeamNumber;
	},
      getTurnTime: function() {
      	return turnTime;
      },

      getScore: function() {
      	return score;
      },

    emitChange: function() {
      this.emit(CHANGE_EVENT);
    },

	  emitConnectGame: function() {
		  this.emit(CONNECT_TO_GAME);
	  },
	  emitTurnHappend: function() {
		  this.emit(TURN);
	  },

	  emitResetTimer: function() {
	  	this.emit(START);
	  },

	  addRedirectListener: function(callback){
		  this.on(CONNECT_TO_GAME, callback);
	  },

	  addTurnHappendListener: function(callback){
		  this.on(TURN, callback);
	  },

	  addResetTimeListener: function(callback) {
	  	this.on(START, callback);
	  },

	  addScoreListener: function(callback) {
	  	this.on(SCORE, callback);
	  },

	  emitScoreChange: function() {
	  	this.emit(SCORE);
	  },

    /**
     * @param {function} callback
     */
    addChangeListener: function(callback) {
      this.on(CHANGE_EVENT, callback);
    },

    /**
     * @param {function} callback
     */
    removeChangeListener: function(callback) {
      this.removeListener(CHANGE_EVENT, callback);
    },

    getMessages: function() {
      return chatMessages;
    },

    emitNewMessage: function() {
      this.emit('CHAT');
    },

    addNewMessageListener: function(callback) {
      this.on('CHAT', callback);
    },

  });

  Dispatcher.register(function(action, payload) {
  	console.log("rec", payload);
	  if (action == 'CONNECT') {
		  userId = payload.player;
		  _games = payload.games;
		  DummyStore.emitChange();
	  } else if (action == 'GAME_CREATED') {
		  _games.push(payload);
		  DummyStore.emitChange();
	  } else if (action == CONNECT_TO_GAME) {
		  if (userId ==  payload.player){
			  game = payload.game;
			  playerTeam = payload.playerTeam;
			  team0 = payload.playerTeam;
			  team1 = payload.playerTeam;
			  DummyStore.emitConnectGame();
	  		}

		  var msg = {
		  	to : 'NOTIFICATION',
		  	player: payload.player,
		  	msg: "joined the game."
		  }
	      appendNewChatMessage(msg);
	      DummyStore.emitNewMessage();

	  } else if (action == TURN) {
		turnTeamNumber = payload.team;
		  turnX = payload.x;
		  turnY = payload.y;
		  DummyStore.emitTurnHappend();
	  } else if (action == START) {
	  	turnTime = payload.time;
	  	DummyStore.emitResetTimer();
	  } else if (action == 'CHAT') {
	      appendNewChatMessage(payload);
	      DummyStore.emitNewMessage();
	    } else if(action == SCORE) {
	    	var scores = payload.scores;
	    	score = "Score: " + scores[0].teamScore + " : " + scores[1].teamScore;

	    	DummyStore.emitScoreChange();
	    }
  });
  return DummyStore;
});
