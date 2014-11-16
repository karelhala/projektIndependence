define(['dispatcher/AppDispatcher'], function(Dispatcher) {

  Dispatcher.register(function(action, payload) {
    console.log(action);
    console.log(payload);
  });

});
