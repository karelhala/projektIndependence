define(['react', 'bootstrap'], function(React, Bootstrap) {

	return React.createClass({

		render: function() {
			return (
        <nav className="navbar navbar-default" role="navigation">
          <div className="container-fluid">
            <div className="navbar-header">
              <button type="button" className="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                <span className="sr-only">Toggle navigation</span>
                <span className="icon-bar"></span>
                <span className="icon-bar"></span>
                <span className="icon-bar"></span>
              </button>
              <a className="navbar-brand" href="#">{this.props.Brand}</a>
            </div>
          </div>
        </nav>
      );
		}
	});
});
