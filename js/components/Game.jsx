define(['react', 'bootstrap'], function(React) {

	return React.createClass({
		render: function() {
			var Brand = this.props.Brand;

			return (
				<div className="panel panel-default panel-chat">
					<div className="panel-heading" >Chat</div>
					<div className="panel-body" >
						<div className="col-lg-6">
							<div className="input-group">
								<input type="text" value="Hello!" />
							</div>
						</div>
					</div>
				</div>
				);
		}
	});

});
