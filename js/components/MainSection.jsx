define(['react', 'jsx!components/Game'], function(React, Game) {

	return React.createClass({
		render: function() {
			return(
					<div>
						<Game />
					</div>
				);
		}
	});

});
