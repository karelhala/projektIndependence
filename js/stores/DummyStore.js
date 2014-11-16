define(['dispatcher/AppDispatcher', 'objectassign', 'eventEmitter'], function(Dispatcher, ObjectAssign, EventEmitter) {

  var CHANGE_EVENT = "change";

  var BrandValue = "nova hodnota dfgdfgdfgdfgdfg";

  var DummyStore = ObjectAssign({}, EventEmitter.prototype, {

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

    BrandValue = payload.text;


    //propagace zmen do vsech navazanych komponent
    DummyStore.emitChange();
  });


  return DummyStore;
});
