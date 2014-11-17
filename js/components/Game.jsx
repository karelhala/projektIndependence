define(['react'], function(React) {

	var Message = React.createClass({
		render: function(){
			if (this.props.userId == userId)
			{
				return(
					<div className="message my-message">
              			{this.props.text}
					</div>
					)
			}
			return(
				<div className="message">
              		{this.props.text}
				</div>
				)
		}
	});

	var MessageList = React.createClass({
		render: function(){
			var renderMessage = function(dataMessage){
				if (dataMessage){
					var message = "[" + dataMessage.to + "]";
					message+= " <" + dataMessage.player + ">: ";
					message+= dataMessage.msg;
					return <Message text={message} userId={dataMessage.player}/>
				}
				return null;
			}
			return (
				<div className='messages'>
					<h2> Conversation: </h2>
              { this.props.textMessages.map(renderMessage)}
				</div>
				);
		}
	});

	return React.createClass({
		getInitialState: function() {
			return {
				textMessages: []
			};
		},
		appendNewMessage: function(message){
			if (this.state.textMessages.length == 10){
				this.state.textMessages.shift();
			}
			this.state.textMessages.push(message);
			this.setState(
				{
					textMessages: this.state.textMessages
				}
			);
		},
		handleChatMessageChange: function(e) {
			this.setState({chatMessage: e.target.value});
		},
		handleClick: function(){
			var that = this;
			// webSocket.onmessage = function(event){
			// 	var data = JSON.parse(event.data);
			// 	var type = data.type;
			// 	if (type == "CHAT")
			// 	{
			// 		that.appendNewMessage(data);
			// 	}
			// };
			if (this.state.chatMessage){
				var chatMessage = {
					type: 'CHAT',
					msg: this.state.chatMessage,
					to: 'GAME'
				};
				webSocket.send(JSON.stringify(chatMessage));
			}
			this.setState({chatMessage: null});
		},
		pressedKey: function(e, asd, ww){
			if (e.keyCode == 13){
				this.handleClick();
			}
		},
		render: function() {
			return (

				<div className="panel panel-default panel-chat">
					<div className="panel-heading" >Chat</div>
					<div className="panel-body" >
						<div>
							<div className="input-group">
								<MessageList textMessages={this.state.textMessages} />
							</div>
							<div className="input-group at-bottom" >
								<input
									type="text"
									onKeyDown={this.pressedKey}
									className="form-control"
									name="chatInput"
									onChange={this.handleChatMessageChange}
									placeholder="Write your message"
									value={this.state.chatMessage}
								/>

								<span className="input-group-btn">
									<button className="btn btn-default" type="button"  onClick={this.handleClick} role="form" action="#">Send</button>
								</span>
							</div>
						</div>
					</div>
				</div>
				);
		}
	});

});
