define(['react', 'jsx!components/Header', 'jsx!components/MainSection'], function(React, Header, Game) {

	return React.createClass({
		render: function() {
			return (<div>
			<Header Brand="World Of Gomoku" />
					<Game />
			</div>);
		}
	});

});
