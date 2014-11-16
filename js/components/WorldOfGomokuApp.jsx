define(['react', 'jsx!components/Header', 'jsx!components/GamePlane'], function(React, Header, GamePlane) {

	/**
	 *
	 */
	return React.createClass({

		render: function() {
			// JSX code
			return (<div className="container">
						<Header />
						<GamePlane />
					</div>
					);
		}
	});

});
