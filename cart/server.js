/**
 * @author victor
 * @date 07.08.2020
 */

const express = require('express');
const app = express();
const consul = require('consul')();

app.use(express.static('dist'));

app.get('*', (req, res) => {
	res.redirect('/');
});

const PORT = process.env.PORT || 3003;
app.listen(PORT, () => {
	console.log(`App listening on port ${PORT}`);
	consul.agent.join('127.0.0.1', function(err) {
		if (err) throw err;

		consul.agent.service.register('mf-cart', function(err) {
			if (err) throw err;
		});
	});
});

process.on('exit', function() {
	consul.agent.service.deregister('mf-cart', function(err) {
		if (err) throw err;
	});
});
