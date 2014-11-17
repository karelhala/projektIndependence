define([
	'react',
	'easeljs',
	'gamejs/circle',
	'gamejs/cross',
	'gamejs/colors',
	'gamejs/playground',
	'gamejs/renderer',
	'stores/DummyStore'],
	function(
		React,
		Easel,
		Circle,
		Cross,
		Colors,
		PlayGround,
		Renderer,
		DummyStore
		)
	{

		var GamePlane = React.createClass({
			componentDidMount: function() {
				var canvas = document.getElementById("canvas");
				this.stage = new createjs.Stage(canvas);
				this.stage.update();
				this.playground = new PlayGround(10, 10);
				this.renderer = new Renderer();
				this.renderer.renderPlayGround(this.playground);
				_that = this;

		// webSocket.onmessage = function(event){
		// 	var data = JSON.parse(event.data);
		// 	var type = data.type;
		// 	if(type == "TURN") {
		// 		_that.makeTurn(data.x, data.y);
		// 	}
		// }
				DummyStore.addTurnHappendListener(this.makeTurn);
				this.renderer.stage.on("stagemousedown", function(event) {
					var square = _that.renderer.getSquareByCoords(event.stageX, event.stageY);
					if(!_that.playground.occupied(square.x, square.y)) {
						square.type = "TURN";
						webSocket.send(JSON.stringify(square));
					}
				});
			},


			makeTurn: function() {
				var x = DummyStore.getTurnX();
				var y = DummyStore.getTurnY();
console.log(x, y, DummyStore.getTurnTeamNumber());
				if (DummyStore.getTurnTeamNumber() != playerTeam) {
					this.playground.addCross(new Cross(x, y));
				} else if (DummyStore.getTurnTeamNumber() == playerTeam) {
					this.playground.addCircle(new Circle(x, y));
				}
				this.renderer.update();
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
