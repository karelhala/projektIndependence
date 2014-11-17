define(['react', 'jsx!components/Game', 'jsx!components/Header', 'jsx!components/GamePlane', 'jsx!components/Timer', 'jsx!components/Chat', 'jsx!components/Score'], function(React, Game, Header, GamePlane, Timer, Chat, Score) {
	/**
	 *
	 */
	return React.createClass({
		render: function() {
			// JSX code
			return (
				<div>
					<Header Brand="World Of Gomoku"/>
					<div className="container">
						<div>
							<Score />
							<Timer />
						</div>
						<Chat />
						<GamePlane />
					</div>
				</div>
				);
		}
	});

});
