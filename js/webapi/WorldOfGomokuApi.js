define(['dispatcher/AppDispatcher', 'actions/ChatActionCreators', 'constants/ChatConstants', 'constants/WebApiConstants'], function(Dispatcher, ChatActionCreators, ChatConstants, WebApiConstants) {
  var webSocket = null;

  var WorldOfGomokuApi = {

    initialize: function (server) {
      webSocket = new WebSocket(server);

      var that = this;
      webSocket.onmessage = function(event){
        var data = JSON.parse(event.data);
        var type = data.type;

        if (type == WebApiConstants.NEW_MESSAGE) {
          that.onNewChatMessageRecieved(data);
        }
      };
    },

    onNewChatMessageRecieved: function (message) {
      ChatActionCreators.recieveNewMessage(message.to, message.player, message.msg);
    },

    createNewMessage: function (message) {
      var chatMessage = {
          type: 'CHAT',
          msg: message.text,
          to: 'ALL'
      };
      webSocket.send(JSON.stringify(chatMessage));
    }

  };

  Dispatcher.register(function(action, payload){
    if (action == ChatConstants.CREATE_NEW_MESSAGE) {
      WorldOfGomokuApi.createNewMessage(payload);
    }
  });

  return WorldOfGomokuApi;
});
