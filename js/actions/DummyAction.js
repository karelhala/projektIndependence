define(['dispatcher/AppDispatcher'], function(Dispatcher) {

  return {
    testAction: function (text) {
      Dispatcher.handleViewAction({
        type: "testAction",
        data: {text: text}
      });
    }
  };

});
