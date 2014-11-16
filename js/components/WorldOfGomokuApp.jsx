define(['react', 'jsx!components/Header', 'jsx!components/GamePlane', 'jsx!components/MainSection', 'stores/DummyStore', 'jsx!components/Timer'], function(React, Header, GamePlane, MainSection, DummyStore, Timer) {

	var PaneForGame = React.createClass({
		handleClick: function(){
			var serverMessage = {
				type: 'START_GAME',
				game: this.gameId
			};
			webSocket.send(JSON.stringify(serverMessage));
		},

		render: function(){
			this.gameId = this.props.text;
			console.log(this.props,"dfgsdfgsdfgsdfg");
			return(
				<div className="panel panel-default">
					<div className="panel-heading" >{this.props.text ? this.props.text : "New Game"}</div>
					<div className="panel-body" >
						<div>
							<div>
								<span>{this.props.text ? "Players: " : ""}{this.props.players}</span>
							</div>
							<button type="button" className="btn btn-default" aria-label="Left Align" onClick={this.handleClick}>
								Take me to game!
							</button>
						</div>
					</div>
					</div>);


//				<div>
//					<button type="button" className="btn btn-default" aria-label="Left Align" onClick={this.handleClick}>
//					Take me to game!
//					</button>
//				</div>
		}
	});

	var GamePanel = React.createClass({
		render: function(){
			var renderGamess = function(oneGame){
				if (oneGame){
					return <PaneForGame text={oneGame.game} players={oneGame.players} />
				}
				return null;
			};
			return(
				<div>
				{this.props.panelGames.map(renderGamess)}
				</div>);
		}
	});

	return React.createClass({

		getInitialState: function() {
			return {
				games: []
			};
		},
		componentDidMount: function() {
			console.log("Header.componentDidMount");
			DummyStore.addChangeListener(this._concatGames);
			DummyStore.addRedirectListener(this._onRedirectToGame);
		},

		_concatGames: function(){
			this.state.games = DummyStore.getGames();
			this.setState(
				{
					games: this.state.games
				}
			);
		},

		_onRedirectToGame: function(){
			var mainSection = React.createFactory(MainSection);
			React.unmountComponentAtNode(document.getElementById('js-app-container'));
			React.render(
				mainSection(),
				document.getElementById('js-app-container'));
		},

		render: function() {
			// JSX code
			return (
					<div>
						<Header Brand="World Of Gomoku"/>
						<PaneForGame />
						<GamePanel panelGames={this.state.games}/>
					</div>
			);
		}
	});

});
