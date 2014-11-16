define(['react', 'jsx!components/Header', 'jsx!components/GamePlane', 'jsx!components/MainSection'], function(React, Header, GamePlane, MainSection) {

	return React.createClass({
		render: function() {
			// JSX code
			return (
					<div>
						<Header />
						<div className="container">
							<GamePlane />
							<MainSection />
						</div>
					</div>
			);
		}
	});

});
