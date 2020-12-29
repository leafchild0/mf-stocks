/**
 * @author victor
 * @date 07.08.2020
 */

const express = require('express');
const bodyParser = require('body-parser');
const app = express();
const db = require('./src/models');
const serviceName = 'stocks-service-ui';
app.use(bodyParser.json());
const consul = require('consul')(
	{
		host: 'consul',
		port: 8500
	}
);

// Routes
require('./src/stocks.routes')(app);
app.use(express.static('dist'));
app.use(bodyParser.urlencoded({
	extended: true
}));

app.get('*', (req, res) => {
	res.redirect('/');
});

app.get('/health', (req, res) => {
	res.end('OK!');
});

// Connect to DB
db.mongoose.connect(db.url, {
	useNewUrlParser: true,
	useUnifiedTopology: true
}).then(() => {
	console.log('Connected to the database!');
}).catch(err => {
	console.log('Cannot connect to the database!', err);
	process.exit();
});

const PORT = process.env.PORT || 3080;
app.listen(PORT, () => {
	console.log(`App listening on port ${PORT}`);
	consul.agent.join('127.0.0.1', function(err) {
		if (err) throw err;
		consul.agent.service.deregister(serviceName, function(err) {
			if (err) throw err;
		});

		consul.agent.service.register({
			name: serviceName,
			address: serviceName,
			port: 3080,
			check: {
				http: `http://${serviceName}:3080/health`,
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
