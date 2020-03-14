const CopyPlugin = require('copy-webpack-plugin');
const path = require('path');

module.exports = {
	lintOnSave: true,
	outputDir: __dirname + '/src/main/webapp/dist',
	// Proxy?
	devServer: {
		proxy: {
			'^/api': {
				target: 'http://localhost:80',
				ws: true,
				changeOrigin: true
			}
		}
	},
	chainWebpack: config => {
		config.plugin('html').tap(args => {
			args[0].template = path.resolve(__dirname, 'src/main/webapp/public/index.html');
			return args;
		});
	},
	configureWebpack: {
		resolve: {
			alias: {
				'@': __dirname + '/src/main/webapp/'
			}
		},
		entry: {
			app: './src/main/webapp/main.js'
		},
		plugins: [
			new CopyPlugin([
				{
					from: 'src/main/webapp/public',
					to: path.resolve(__dirname, 'src/main/webapp/dist'),
					flatten: true,
					ignore: ['index.html', '*.png', '*.jpg']
				},
			]),
		]
	}
};
