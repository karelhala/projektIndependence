define(['react', 'stores/DummyStore', 'jsx!components/Message'], function(React, DummyStore, Message) {


  var MessageList = React.createClass({

    getAllMessages: function() {
      return DummyStore.getMessages();
    },

    getInitialState: function() {
      return {
        Messages: this.getAllMessages()
      };
    },

    componentDidMount: function() {
      DummyStore.addNewMessageListener(this.onNewMessageRecieved);
    },

    componentWillUnmount: function() {
    //  DummyStore.removeNewMessageListener(this.onNewMessageRecieved);
    },

    render: function() {
      var allMessages = this.state.Messages;
      return (
        <div className="chat-window">
          {
            allMessages.map(function (msg) {
              return <Message userId={msg.player} text={msg.msg} type={msg.to}/>;
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
