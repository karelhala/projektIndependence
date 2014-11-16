define(['dispatcher/AppDispatcher', 'constants/ChatConstants'], function(Dispatcher, ChatConstants) {

  return {
    createMessage: function(text) {
      Dispatcher.handleViewAction(
        {
          type: ChatConstants.CREATE_NEW_MESSAGE,
          data: {
                  text: text
                }
        }
      );
    },

    recieveNewMessage: function(to, from, text) {
      Dispatcher.handleServerAction(
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
