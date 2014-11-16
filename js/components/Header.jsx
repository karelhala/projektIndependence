define(['react', 'react-boostrap'], function(React, Bootstrap) {

  var Navbar = Bootstrap.Navbar;

  var Nav = Bootstrap.Nav;
  var NavItem = Bootstrap.NavItem;

  var DropdownButton = Bootstrap.DropdownButton;
  var MenuItem = Bootstrap.MenuItem;

	/**
	 *
	 */
	return React.createClass({
		render: function() {
			return (
        <Navbar fixedTop brand="Toto je brand">
        </Navbar>);
		}
	});

});
