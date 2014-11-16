define(['dispatcher/AppDispatcher', 'objectassign', 'eventEmitter'], function(Dispatcher, ObjectAssign, EventEmitter) {

	var _games = [];
  var CHANGE_EVENT = "change";

  var BrandValue = "nova hodnota dfgdfgdfgdfgdfg";

  var DummyStore = ObjectAssign({}, EventEmitter.prototype, {

	  getGames: function(){
		  return _games;
	  },

    getBrandText: function() {
      return BrandValue;
    },

    emitChange: function() {
      this.emit(CHANGE_EVENT);
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
//    BrandValue = payload.text;
//
//
//    //propagace zmen do vsech navazanych komponent
//    DummyStore.emitChange();
  });
  return DummyStore;
});
