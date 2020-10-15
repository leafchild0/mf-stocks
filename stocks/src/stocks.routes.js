/**
 * @author victor
 * @date 15.10.2020
 */

module.exports = app => {
	const stocks = require('./stock.controller');

	const router = require('express').Router();

	router.post('/', stocks.create);
	router.get('/', stocks.findAll);
	router.get('/:id', stocks.findOne);
	router.put('/:id', stocks.update);
	router.delete('/:id', stocks.delete);
	app.use('/stocks', router);
};
