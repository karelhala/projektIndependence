define(['dispatcher/AppDispatcher', 'objectassign', 'eventEmitter'], function(Dispatcher, ObjectAssign, EventEmitter) {

	var _games = [];
  var CHANGE_EVENT = "change";
	var CONNECT_TO_GAME = "CONNECT_TO_GAME";

  var DummyStore = ObjectAssign({}, EventEmitter.prototype, {

	  getGames: function(){
		  return _games;
	  },

    emitChange: function() {
      this.emit(CHANGE_EVENT);
    },

	  emitConnectGame: function() {
		  this.emit(CONNECT_TO_GAME);
	  },
	  addRedirectListener: function(callback){
		  this.on(CONNECT_TO_GAME, callback);
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
		  _games = payload.games;
		  DummyStore.emitChange();
	  }
	  else if (action == 'GAME_CREATED')
	  {
		  _games.push(payload.game);
		  DummyStore.emitChange();
	  }
	  else if (action == CONNECT_TO_GAME)
	  {
		  game = payload.game;
		  playerTeam = payload.playerTeam;
		  team0 = payload.playerTeam;
		  team1 = payload.playerTeam;
			  DummyStore.emitConnectGame();
	  }
  });
  return DummyStore;
});
