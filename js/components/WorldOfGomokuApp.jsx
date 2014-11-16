define(['react', 'jsx!components/Header', 'jsx!components/GamePlane'], function(React, Header, GamePlane) {

	return React.createClass({
		render: function() {
			// JSX code
			return (
					<div>
						<Header Brand="World Of Gomoku"/>
						<div className="container">
							<GamePlane />
						</div>
					</div>
			);
		}
	});

});
