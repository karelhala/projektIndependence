require.config({
	baseUrl: "js/",

	paths: {
		"react": "static/react-with-addons",
		"JSXTransformer": "static/JSXTransformer",
		"easeljs": "static/easeljs-0.7.1.min",
    "bootstrap": "static/bootstrap.min",
		'react-router-shim': 'static/react-router-shim',
		"objectassign": "static/object-assign",
		"estedispatcher": "static/este-dispatcher",
    "eventEmitter": "static/event-emitter"
	},

	jsx: {
		fileExtension: '.jsx'
	},
	shim: {
		"easeljs": {
			exports: "createjs"
		}
	}
});

require(['react', 'jsx!components/WorldOfGomokuApp'], function(React, WorldOfGomoku) {
	webSocket = new WebSocket("ws://192.168.2.67:8080/freedom/server");
	var worldOfGomoku = React.createFactory(WorldOfGomoku);
	// Mount the JSX component in the app container
	React.renderComponent(
		worldOfGomoku(),
		document.getElementById('js-app-container'));
});
