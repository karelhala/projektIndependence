define(['react', 'jsx!components/Header', 'jsx!components/GamePlane', 'jsx!components/MainSection'], function(React, Header, GamePlane, MainSection) {

	var PaneForGame = React.createClass({
		handleClick: function(){
			var mainSection = React.createFactory(MainSection);
			React.unmountComponentAtNode(document.getElementById('js-app-container'));
			React.render(
				mainSection(),
				document.getElementById('js-app-container'));
		},
		render: function(){
			console.log(this.props.text);
			return(
				<div className="panel panel-default">
					<div className="panel-heading" >{this.props.text ? this.props.text : "New Game"}</div>
					<div className="panel-body" >
						<div>
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
					return <PaneForGame text={oneGame.game} />
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
		_concatGames: function(games){
			this.state.games = this.state.games.concat(games);
			this.setState(
				{
					games: this.state.games
				}
			);
		},

		render: function() {
			// JSX code
			return (
					<div>
						<Header Brand="World Of Gomoku"/>
						<PaneForGame />
					</div>
			);
		}
	});

});
