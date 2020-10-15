/**
 * @author victor
 * @date 07.08.2020
 */

const express = require('express');
const bodyParser = require('body-parser');
const app = express();
const db = require('./src/models');
app.use(bodyParser.json());

// Routes
require('./src/stocks.routes')(app);
app.use(express.static('dist'));
app.use(bodyParser.urlencoded({
	extended: true
}));

app.get('*', (req, res) => {
	res.redirect('/');
});

// Connect to DB
db.mongoose.connect(db.url, {
		useNewUrlParser: true,
		useUnifiedTopology: true
	})
	.then(() => {
		console.log('Connected to the database!');
	})
	.catch(err => {
		console.log('Cannot connect to the database!', err);
		process.exit();
	});

const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
	console.log(`App listening on port ${PORT}`);
});
