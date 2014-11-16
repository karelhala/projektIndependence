require.config({
	baseUrl: "js/",

	paths: {
		"react": "static/react-with-addons",
		"JSXTransformer": "static/JSXTransformer",
		"easeljs": "static/easeljs-0.7.1.min",
    	"bootstrap": "static/bootstrap.min",
		"react-router": "static/react-router",
		'react-router-shim': 'static/react-router-shim'
	},

	jsx: {
		fileExtension: '.jsx'
	},

	shim:    {
		'react-router-shim': {
			exports: 'React'
		},
		'react-router': {
			deps:    ['react-router-shim'],
			exports: 'ReactRouter'
		}
	}
});

require(['react', 'jsx!components/WorldOfGomokuApp', 'react-router'], function(React, WorldOfGomoku, Router) {
	webSocket = new WebSocket("ws://192.168.2.67:8080/freedom/server");
	var worldOfGomoku = React.createFactory(WorldOfGomoku);
	// Mount the JSX component in the app container
	React.renderComponent(
		worldOfGomoku(),
		document.getElementById('js-app-container'));
});
