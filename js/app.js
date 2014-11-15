require.config({
	baseUrl: "js/",

	paths: {
		"react": "static/react-with-addons",
		"JSXTransformer": "static/JSXTransformer",
		"easeljs": "static/easeljs-0.7.1.min"
	},

	jsx: {
		fileExtension: '.jsx'
	}
});

require(['react', 'jsx!components/Timer'], function(React, Timer) {
	var start = new Date();
	Timer = React.createFactory(Timer);

	// Mount the JSX component in the app container
	React.render(
		Timer({start: start}),
		document.getElementById('js-app-container'));
});