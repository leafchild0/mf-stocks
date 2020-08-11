/**
 * @author victor
 * @date 07.08.2020
 */

const express = require('express');
const data = require('./db.json');
const app = express();

app.use(express.static('dist'));

app.get('/stocks', (req, res) =>
{
	res.json(data.stocks);
});

app.get('*', (req, res) =>
{
	res.redirect('/');
});

const PORT = process.env.PORT || 3000;
app.listen(PORT, () =>
{
	console.log(`App listening on port ${PORT}`);
});
