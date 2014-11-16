define(['react', 'jsx!components/Game', 'jsx!components/Header', 'jsx!components/GamePlane', 'jsx!components/Timer'], function(React, Game, Header, GamePlane, Timer) {
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
						<GamePlane />
					</div>
				</div>
				);
		}
	});

});