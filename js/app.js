require.config({
	baseUrl: "js/",

	paths: {
		"react": "static/react-with-addons",
		"JSXTransformer": "static/JSXTransformer",
		"easeljs": "static/easeljs-0.7.1.min",
    "bootstrap": "static/bootstrap.min"
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
	var worldOfGomoku = React.createFactory(WorldOfGomoku);

	// Mount the JSX component in the app container
	React.render(
		worldOfGomoku(),
		document.getElementById('js-app-container'));
});
