define(['react', 'stores/DummyStore'], function(React, DummyStore) {

	/**
	 * <TimeMessage elapsed={100} />
	 */
	var TimeMessage = React.createClass({
		render: function() {
			var elapsed = Math.round(this.props.elapsed  / 100);
			var seconds = elapsed / 10 + (elapsed % 10 ? '' : '.0' );
			var message = 'React has been successfully running for ' + seconds + ' seconds.';
			// JSX code
			return;
		}
	});

	/**
	 * <Timer start={aDate} />
	 */
	var Timer = React.createClass({
		getInitialState: function() {
			return {elapsed: 0,
				visible: false};
		},

		componentDidMount: function(el, root) {
			var that = this;
			DummyStore.addResetTimeListener(this.resetTime);

			setInterval(function() {
				var newElapsed = that.state.elapsed - 1000
				that.setState({elapsed: newElapsed});
			}, 1000);
		},

		resetTime: function() {
			this.setState({elapsed: DummyStore.getTurnTime(),
				visible: true});

		},

		render: function() {
			var elapsed = Math.round(this.state.elapsed / 1000);
			if(!this.state.visible) {
				return (<div id="game-timer"><span>Wait for game start.</span></div>);
			} else if(elapsed >= 0) {
				return (<div id="game-timer"><span>End turn in: </span>{elapsed}s</div>);
			} else {
				elapsed += Math.round(DummyStore.getTurnTime() / 1000);
				return  (<div id="game-timer"><span>Enemy turn end in: </span>{elapsed}s</div>);
			}
		}
	});

	return Timer;
});
