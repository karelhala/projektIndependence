define(['react', 'bootstrap'], function(React) {

	return React.createClass({
		render: function() {
			var Brand = this.props.Brand;

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
              <a className="navbar-brand" href="#">{Brand}</a>
            </div>
          </div>
        </nav>
      );
		}
	});

});
