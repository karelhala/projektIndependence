define(['dispatcher/AppDispatcher', 'constants/ChatConstants'], function(Dispatcher, ChatConstants) {

  return {
    createMessage: function(text) {
      webSocket.send("{type:'CHAT',player:" + userId + ", msg: '" + text + "',to:'GAME'}");
    },

    recieveNewMessage: function(to, from, text) {
      Dispatcher.handleViewAction(
        {
          type: ChatConstants.RECIEVE_NEW_MESSAGE,
          data: {
                  to: to,
                  from: from,
                  text: text
                }
        }
      );
    }
  };

});
