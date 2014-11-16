require.config({
	baseUrl: "js/",

	paths: {
		"react": "static/react-with-addons",
		"JSXTransformer": "static/JSXTransformer",
		"easeljs": "static/easeljs-0.7.1.min",
    	"bootstrap": "static/bootstrap.min",
		'react-router-shim': 'static/react-router-shim',
		"objectassign": "static/object-assign",
    	"eventEmitter": "static/event-emitter",
		"Draggable": "static/draggable",
		"estedispatcher": "static/este-dispatcher",
		"event-emitter": "static/event-emitter"
	},

	jsx: {
		fileExtension: '.jsx'
	},
	shim: {
		"easeljs": {
			exports: "createjs"
		},
		'react-router-shim': {
			exports: 'React'
		},
		'Draggable': {
			deps: ['react-router-shim'],
			exports: 'Draggable'
		}
	}
});

require(['react', 'jsx!components/WorldOfGomokuApp', 'webapi/WorldOfGomokuApi'], function(React, WorldOfGomoku, WorldOfGomokuApi) {
	"use strict";
	// webSocket = new WebSocket("ws://192.168.2.67:8080/freedom/server");
	var worldOfGomokuApi = WorldOfGomokuApi.initialize("ws://192.168.2.67:8080/freedom/server");
	var worldOfGomoku = React.createFactory(WorldOfGomoku);
	AppDispatcher.handleServerAction({type: "serverLoad"});
	// Mount the JSX component in the app container
	React.render(
		worldOfGomoku(),
		document.getElementById('js-app-container'));
});
