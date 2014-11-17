define(['react', 'stores/DummyStore'], function(React, DummyStore) {
	var Score = React.createClass({
		getInitialState: function() {
			return {};
		},

		componentDidMount: function(el, root) {
			DummyStore.addScoreListener(this.changeScore);
		},

		changeScore: function() {
			this.setState({
				score: DummyStore.getScore()
			});
		},

		render: function() {
			return (<div id="game-score"><span>{this.state.score}</span></div>);
		}
	});

	return Score;
});
