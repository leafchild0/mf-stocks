module.exports = {
	'transpileDependencies': [
		'vuetify'
	],
	publicPath: process.env.NODE_ENV === 'production'
		? '/stocks/'
		: '/',
	// Proxy?
	devServer: {
		proxy: {
			'^/gateway-service': {
				target: 'http://localhost:3080',
				pathRewrite: {'^/gateway-service': ''},
				ws: true,
				changeOrigin: true,
			}
		}
	},
};
