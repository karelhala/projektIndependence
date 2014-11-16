define(['react', 'bootstrap', 'actions/DummyAction', 'stores/DummyStore'], function(React, Bootstrap, DummyAction, DummyStore) {

	return React.createClass({

    getBrandText: function() {
      return {
        Brand: DummyStore.getBrandText()
      };
    },

    getInitialState: function() {
      return this.getBrandText();
    },

    componentDidMount: function() {
      console.log("Header.componentDidMount");
      DummyStore.addChangeListener(this._onChange);
    },

    componentWillUnmount: function() {
      console.log("Header.componentWillUnmount");
      DummyStore.removeChangeListener(this._onChange);
    },

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
              <a className="navbar-brand" href="#" onDoubleClick={this._onBrandDoubleClick}>{this.state.Brand}</a>
            </div>
          </div>
        </nav>
      );
		},

    _onChange: function() {
      console.log("Header._onChange");
      this.setState(this.getBrandText());
    },

    _onBrandDoubleClick: function() {
      var Brand = this.state.Brand + " changed";
      DummyAction.testAction(Brand);
    },
	});

});
