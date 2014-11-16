define(['react', 'stores/ChatStore', 'jsx!components/Message'], function(React, ChatStore, Message) {


  var MessageList = React.createClass({

    getAllMessages: function() {
      return ChatStore.getMessages();
    },

    getInitialState: function() {
      return {
        Messages: this.getAllMessages()
      };
    },

    componentDidMount: function() {
      ChatStore.addNewMessageListener(this.onNewMessageRecieved);
    },

    componentWillUnmount: function() {
      ChatStore.removeNewMessageListener(this.onNewMessageRecieved);
    },

    render: function() {
      var allMessages = this.state.Messages;
      return (
        <div id="bla">
          {
            allMessages.map(function (msg) {
              return <Message userId={msg.to} text={msg.text} />;
            })
          }
        </div>
      );
    },

    onNewMessageRecieved: function() {
      this.setState(
        {
          Messages: this.getAllMessages()
        }
      );
    }
  });

  return MessageList;
});
