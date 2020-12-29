/**
 * @author victor
 * @date 07.08.2020
 */

const express = require('express');
const app = express();
const consul = require('consul')(
	{
		host: 'consul',
		port: 8500,
	}
);
const serviceName = 'user-account-service-ui';

app.use(express.static('dist'));

app.get('*', (req, res) => {
	res.redirect('/');
});

app.get('/health', (req, res) => {
	res.end('OK!');
});

const PORT = process.env.PORT || 3006;
app.listen(PORT, () => {
	console.log(`App listening on port ${PORT}`);
	consul.agent.join('127.0.0.1', function(err) {
		if (err) throw err;

		consul.agent.service.register({
			name: serviceName,
			address: serviceName,
			port: 3006,
			check: {
				http: `http://${serviceName}:3006/health`,
				interval: '10s',
				timeout: '5s',
			}
		}, function(err) {
			if (err) throw err;
		});
	});
});

process.on('exit', function() {
	consul.agent.service.deregister(serviceName, function(err) {
		if (err) throw err;
	});
});
