define(['react', 'bootstrap'], function(React) {

	return React.createClass({
		createMessage: function(data){
			var newMessage = "[" + data.to + "]";
			newMessage+= " <" + data.player + ">: ";
			newMessage+= data.msg;
			return newMessage;
		},
		getInitialState: function() {
			return {
				textMessages: ''
			};
		},
		appendNewMessage: function(message){
			this.setState(
				{
					textMessages: this.state.textMessages+"\n" + message
				}
			);
		},
		handleChatMessageChange: function(e) {
			this.setState({chatMessage: e.target.value});
		},
		handleClick: function(){
			var that = this;
			webSocket.onmessage = function(event){
				var data = JSON.parse(event.data);
				var type = data.type;
				that.appendNewMessage(that.createMessage(data));
				console.log(data);
			}
			if (this.state.chatMessage){
				var chatMessage = {
					type: 'CHAT',
					msg: this.state.chatMessage,
					to: 'ALL'
				};
				webSocket.send(JSON.stringify(chatMessage));
			}
			this.setState({chatMessage: null});
		},
		render: function() {
			return (
				<div className="panel panel-default panel-chat">
					<div className="panel-heading" >Chat</div>
					<div className="panel-body" >
						<div>
							<div className="input-group">
								<textarea className="form-control" rows="30" disabled="disabled" value={this.state.textMessages}></textarea>
							</div>
							<div className="input-group">
								<input
									type="text"
									className="form-control"
									name="chatInput"
									onChange={this.handleChatMessageChange}
									placeholder="Write your message"
									value={this.state.chatMessage}
								/>

								<span className="input-group-btn">
									<button className="btn btn-default" type="button"  onClick={this.handleClick} role="form" action="#">Go!</button>
								</span>
							</div>
						</div>
					</div>
				</div>
				);
		}
	});

});