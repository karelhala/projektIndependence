define(['dispatcher/AppDispatcher', 'objectassign', 'eventEmitter'], function(Dispatcher, ObjectAssign, EventEmitter) {

	var _games = [];
  var CHANGE_EVENT = "change";
	var CONNECT_TO_GAME = "CONNECT_TO_GAME";
	var TURN = "TURN";
	var turnTeamNumber;
	var turnX;
	var turnY;
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
    emitChange: function() {
      this.emit(CHANGE_EVENT);
    },

	  emitConnectGame: function() {
		  this.emit(CONNECT_TO_GAME);
	  },
	  emitTurnHappend: function() {
		  this.emit(TURN);
	  },

	  addRedirectListener: function(callback){
		  this.on(CONNECT_TO_GAME, callback);
	  },

	  addTurnHappendListener: function(callback){
		  this.on(TURN, callback);
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
    }
  });

  Dispatcher.register(function(action, payload) {
    console.log(action);
    console.log(payload);
	  if (action == 'CONNECT')
	  {
		  userId = payload.player;
		  _games = payload.games;
		  DummyStore.emitChange();
	  }
	  else if (action == 'GAME_CREATED')
	  {
		  _games.push(payload);
		  DummyStore.emitChange();
	  }
	  else if (action == CONNECT_TO_GAME)
	  {
		  if (userId ==  payload.player){
			  game = payload.game;
			  playerTeam = payload.playerTeam;
			  team0 = payload.playerTeam;
			  team1 = payload.playerTeam;
			  DummyStore.emitConnectGame();
	  		}
	  }
	  else if (action == TURN)
	  {
		turnTeamNumber = payload.team;
		  turnX = payload.x;
		  turnY = payload.y;
		  DummyStore.emitTurnHappend();
	  }
  });
  return DummyStore;
});
