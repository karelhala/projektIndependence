<<<<<<< HEAD
define(['react', 'stores/DummyStore', 'jsx!components/Message'], function(React, DummyStore, Message) {
=======
define(['react', 'stores/ChatStore', 'jsx!components/Message'], function(React, ChatStore, Message) {
>>>>>>> refactoring chatu, vytvoreni a implementace abstraktniho api


  var MessageList = React.createClass({

    getAllMessages: function() {
<<<<<<< HEAD
      return DummyStore.getMessages();
=======
      return ChatStore.getMessages();
>>>>>>> refactoring chatu, vytvoreni a implementace abstraktniho api
    },

    getInitialState: function() {
      return {
        Messages: this.getAllMessages()
      };
    },

    componentDidMount: function() {
<<<<<<< HEAD
      DummyStore.addNewMessageListener(this.onNewMessageRecieved);
    },

    componentWillUnmount: function() {
    //  DummyStore.removeNewMessageListener(this.onNewMessageRecieved);
=======
      ChatStore.addNewMessageListener(this.onNewMessageRecieved);
    },

    componentWillUnmount: function() {
      ChatStore.removeNewMessageListener(this.onNewMessageRecieved);
>>>>>>> refactoring chatu, vytvoreni a implementace abstraktniho api
    },

    render: function() {
      var allMessages = this.state.Messages;
      return (
<<<<<<< HEAD
        <div>
          {
            allMessages.map(function (msg) {
              return <Message userId={msg.to} text={msg.msg} />;
=======
        <div id="bla">
          {
            allMessages.map(function (msg) {
              return <Message userId={msg.to} text={msg.text} />;
>>>>>>> refactoring chatu, vytvoreni a implementace abstraktniho api
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
