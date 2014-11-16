define([
		'react',
		'easeljs', 
		'gamejs/circle',
		'gamejs/cross',
		'gamejs/colors',
		'gamejs/playground',
		'gamejs/renderer'], 
function(
	React, 
	Easel, 
	Circle,
	Cross,
	Colors,
	PlayGround,
	Renderer
){
	var GamePlane = React.createClass({
		componentDidMount: function() {
			var canvas = document.getElementById("canvas");
			this.stage = new createjs.Stage(canvas);
			this.stage.update();
			this.playground = new PlayGround(10, 10);
			this.renderer = new Renderer();
			this.renderer.renderPlayGround(this.playground);
		},

		render: function() {
			return (
					<canvas widht="500" height="300" id="canvas"></canvas>
				   );
		} 

	});

	return GamePlane;
})
