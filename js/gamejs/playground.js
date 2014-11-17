define([], function() {
	function PlayGround(width, height) {
		this.CIRCLES = [];
		this.CROSSES = [];
		this.lastMove = null;

		this.width = width;
		this.height = height;
	}

	PlayGround.prototype.getCircles = function () {
		return this.CIRCLES;
	}

	PlayGround.prototype.getCrosses = function () {
		return this.CROSSES;
	}

	PlayGround.prototype.occupied = function (x, y) {
		var occupied = false;
		if(x >= this.width || y >= this.height) return true;
/*
		this.CIRCLES.forEach(function(circle) {
			if(circle.x == x && circle.y == y) {
				occupied = true;
			}
		});

		this.CROSSES.forEach(function(cross) {
			if(cross.x == x && cross.y == y) {
				occupied = true;
			}
		});*/
		return occupied;
	}

	PlayGround.prototype.clear = function (x, y) {
		this.CIRCLES = this.CIRCLES.filter(function(elm) {
			return ((elm.x != x) || (elm.y != y));
		})

		this.CROSSES = this.CROSSES.filter(function(elm) {
			return ((elm.x != x) || (elm.y != y));
		})
	}

	PlayGround.prototype.addCircle = function (circle) {
		if(circle.x >= this.width || circle.y >= this.height) return;
		if(this.occupied(circle.x, circle.y)) return;

		this.clear(circle.x, circle.y);
		this.CIRCLES.push(circle);
		this.registerLastMove(circle);
	}
	PlayGround.prototype.addCross = function (cross) {
		if(cross.x >= this.width || cross.y >= this.height) return;
		if(this.occupied(cross.x, cross.y)) return;

		this.clear(cross.x, cross.y);
		this.CROSSES.push(cross);
		this.registerLastMove(cross);
	}
	PlayGround.prototype.registerLastMove = function(obj) {
		this.lastMove = obj;
	}

	return PlayGround;
});
