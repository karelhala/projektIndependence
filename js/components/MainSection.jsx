define(['react', 'jsx!components/Game'], function(React, Game, Boostrap) {
	/**
	 *
	 */
	return React.createClass({
		render: function() {
			// JSX code
			return <Game />;
		}
	});

});


<div className="panel panel-default panel-chat">
	<div className="panel-heading" >Chat</div>
	<div className="panel-body" >
		<div className="col-lg-6">
			<div className="input-group">
				<input type="text" className="form-control">
					<span className="input-group-btn">
						<button className="btn btn-default" type="button">Go!</button>
					</span>
				</div>
			</div>
		</div>
	</div>