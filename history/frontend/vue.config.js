module.exports = {
	'transpileDependencies': [
		'vuetify'
	],
	outputDir: 'build/dist',
	assetsDir: 'static',
	// Proxy?
	devServer: {
		proxy: {
			'^/gateway-service': {
				target: 'http://localhost:9000',
				pathRewrite: {'^/gateway-service': ''},
				ws: true,
				changeOrigin: true,
			}
		}
	},
}
