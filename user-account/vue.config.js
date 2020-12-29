module.exports = {
  'transpileDependencies': [
    'vuetify'
  ],
  publicPath: process.env.NODE_ENV === 'production'
      ? '/user-account/'
      : '/',
  // Proxy?
  devServer: {
    proxy: {
      '^/gateway-service': {
        target: 'http://localhost:3006',
        pathRewrite: {'^/gateway-service': ''},
        ws: true,
        changeOrigin: true,
      }
    }
  },
};
