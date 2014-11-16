define(['react', 'jsx!components/Game', 'jsx!components/Header', 'jsx!components/GamePlane'], function(React, Game, Header, GamePlane) {
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
						<Game />
						<GamePlane />
					</div>
				</div>
				);
		}
	});

});