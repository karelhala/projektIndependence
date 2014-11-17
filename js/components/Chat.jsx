define(['react', 'jsx!components/MessageList', 'actions/ChatActionCreators'], function(React, MessageList, ChatActionCreators) {
  var Chat = React.createClass({

    handleChatMessageChange: function(e) {
      this.setState({chatMessage: e.target.value});
    },

    getInitialState: function() {
      return {
        chatMessage: ""
      };
    },

    render: function() {
      return (
        <div className="panel panel-default panel-chat">
          <div className="panel-heading" >Chat</div>
          <div className="panel-body" >
            <div>
              <div className="input-group">
                <div class='messages'>
                  <h2> Conversation: </h2>
                    <MessageList />
                </div>
              </div>
              <div className="input-group">
                <input
                  type="text"
                  className="form-control"
                  name="chatInput"
                  onChange={this.handleChatMessageChange}
                  placeholder="Write your message"
                  value={this.state.chatMessage} />

                <span className="input-group-btn">
                  <button className="btn btn-default"
                          type="button"
                          onClick={this.onSendNewMessageButtonClick}
                          role="form"
                          action="#">Go</button>
                </span>
              </div>
            </div>
          </div>
        </div>
      );
    },

    onSendNewMessageButtonClick: function(){
      ChatActionCreators.createMessage(this.state.chatMessage);
      this.setState({chatMessage: ""});
    }
  });

  return Chat;
});
