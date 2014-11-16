define(['easeljs', 'gamejs/colors'], function(Easel, Colors) {
	function Renderer() {
		var dimension = [document.documentElement.clientWidth, document.documentElement.clientHeight];
		var canvas = document.getElementById("canvas");
		canvas.width = dimension[0];
		canvas.height = dimension[1];
		var stage = new createjs.Stage("canvas");
		stage.enableMouseOver();
		this.stage = stage;

		this.backgrounds = [];

		this.SQUARE_SIZE = 30;
	}

	Renderer.prototype.renderPlayGround = function(pg) {
		this.createPlayGround(pg.width, pg.height)
			var _this = this;
		if(pg.lastMove != null) {
			this.fillBackground(pg.lastMove.x, pg.lastMove.y, Colors.light_green);
		}
		pg.getCircles().forEach(function(circle){
			_this.createCircle(circle.x, circle.y);
		});
		pg.getCrosses().forEach(function(cross){
			_this.createCross(cross.x, cross.y);
		});
		this.stage.update();
	}

	Renderer.prototype.createSquare = function(x, y) {
		var square = new createjs.Shape();
		square.graphics.beginStroke("black").drawRect(0,0,this.SQUARE_SIZE,this.SQUARE_SIZE);
		square.x = x;
		square.y = y;
		this.stage.addChild(square);
	}

	Renderer.prototype.createLine = function(x, y, tox, toy) {
		var line = new createjs.Shape();
		line.graphics.beginStroke("black").setStrokeStyle(2)
			.moveTo(x, y).lineTo(tox, toy);
		this.stage.addChild(line);
	}

	Renderer.prototype.createCircle = function(x, y) {
		var circle = new createjs.Shape();
		circle.graphics.beginStroke("red").setStrokeStyle(2).drawCircle(0, 0, this.SQUARE_SIZE/2 - 3);
		circle.x = x * this.SQUARE_SIZE + this.SQUARE_SIZE/2;
		circle.y = y * this.SQUARE_SIZE + this.SQUARE_SIZE/2;
		this.stage.addChild(circle);
	}

	Renderer.prototype.createCross = function(x, y) {
		var cross = new createjs.Shape();
		cross.graphics.beginStroke("blue").setStrokeStyle(2)
			.moveTo(x * this.SQUARE_SIZE + 3, y * this.SQUARE_SIZE + 3)
			.lineTo(x * this.SQUARE_SIZE + this.SQUARE_SIZE - 3, y * this.SQUARE_SIZE + this.SQUARE_SIZE - 3)
			.moveTo(x * this.SQUARE_SIZE + this.SQUARE_SIZE - 3, y * this.SQUARE_SIZE + 3)
			.lineTo(x * this.SQUARE_SIZE + 3, y * this.SQUARE_SIZE + this.SQUARE_SIZE - 3);
		this.stage.addChild(cross);
	}

	Renderer.prototype.createPlayGround = function(width, height) {

		for(i = 0; i <= width; i++) {
			this.createLine(i * this.SQUARE_SIZE, 0, i * this.SQUARE_SIZE, height * this.SQUARE_SIZE);
		}

		for(i = 0; i <= height; i++) {
			this.createLine(0, i * this.SQUARE_SIZE, width * this.SQUARE_SIZE, i * this.SQUARE_SIZE);
		}
	}

	Renderer.prototype.getSquareByCoords = function(x, y) {
		var square = {
			x: Math.floor(x / this.SQUARE_SIZE),
			y: Math.floor(y / this.SQUARE_SIZE),
		}
		return square;
	}

	Renderer.prototype.update = function() {
		this.stage.removeAllChildren();
		this.stage.update();
	}

	Renderer.prototype.fillBackground = function(x, y, color) {
		var bg = new createjs.Shape();
		bg.graphics.beginFill(color).drawRect(1, 1, this.SQUARE_SIZE - 2, this.SQUARE_SIZE - 2);
		bg.x = x * this.SQUARE_SIZE;
		bg.y = y * this.SQUARE_SIZE;
		this.stage.addChild(bg);
	}

	return Renderer;
});

