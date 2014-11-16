define(['react', 'jsx!components/Game', 'jsx!components/Header', 'jsx!components/GamePlane', 'jsx!components/Timer', 'jsx!components/Chat'], function(React, Game, Header, GamePlane, Timer, Chat) {
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
						<Timer />
						<Game />
						<Chat />
						<GamePlane />
					</div>
				</div>
				);
		}
	});

});
