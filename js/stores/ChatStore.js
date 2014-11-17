define(['dispatcher/AppDispatcher', 'objectassign', 'eventEmitter', 'constants/ChatConstants'], function(Dispatcher, ObjectAssign, EventEmitter, ChatConstants) {

  var chatMessages = [];

  function appendNewChatMessage(newMessage) {
    chatMessages.push(newMessage);
  }

  var ChatStore = ObjectAssign({}, EventEmitter.prototype, {

    getMessages: function() {
      return chatMessages;
    },

    getInitialState: function() {
      return getAllMessages();
    },

    emitNewMessage: function() {
      this.emit(ChatConstants.RECIEVE_NEW_MESSAGE);
    },

    addNewMessageListener: function(callback) {
      this.on(ChatConstants.RECIEVE_NEW_MESSAGE, callback);
    },

    removeNewMessageListener: function(callback) {
      this.removeListener(ChatConstants.RECIEVE_NEW_MESSAGE, callback);
    }
  });

  // Dispatcher.register(function(action, payload){
  //   if (action == ChatConstants.RECIEVE_NEW_MESSAGE) {
  //     appendNewChatMessage(payload);
  //     ChatStore.emitNewMessage();
  //   }
  // });


  return ChatStore;
});
