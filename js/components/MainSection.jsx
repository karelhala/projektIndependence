define(['react', 'jsx!components/Lobby', 'react-boostrap'], function(React, Lobby, Boostrap) {
	/**
	 *
	 */
	return React.createClass({
		render: function() {
			// JSX code
			return(
					<div>
							<Lobby />
					</div>
				);
		}
	});

});