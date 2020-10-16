/**
 * @author victor
 * @date 15.10.2020
 */

const db = require('./models');
const Stock = db.stocks;

exports.create = (req, res) => {
	// Validate request
	if (!req.body.name && !req.body.price && !req.body.amount) {
		res.status(400).send({message: 'Content can not be empty!'});
		return;
	}

	// Create a Tutorial
	const stock = new Stock({
		name: req.body.name,
		price: req.body.price,
		amount: req.body.amount
	});

	// Save Tutorial in the database
	stock
		.save(stock)
		.then(data => {
			res.send(data);
		})
		.catch(err => {
			res.status(500).send({
				message:
					err.message || 'Some error occurred while adding a stock.'
			});
		});
};

exports.findAll = (req, res) => {
	const name = req.query.name;
	// Added a search as well
	const condition = name ? {name: {$regex: new RegExp(name), $options: 'i'}} : {};

	Stock.find(condition)
		.then(data => {
			res.send(data);
		})
		.catch(err => {
			res.status(500).send({
				message:
					err.message || 'Some error occurred while retrieving stocks.'
			});
		});
};

exports.findOne = (req, res) => {
	const id = req.params.id;

	Stock.findById(id)
		.then(data => {
			if (!data) {
				res.status(404).send({message: 'Not found Stocks with id ' + id});
			} else {
				res.send(data);
			}
		})
		.catch(err => {
			res
				.status(500)
				.send({message: err.message || 'Error retrieving Stocks with id ' + id});
		});
};

exports.update = (req, res) => {
	// Validate request
	if (!req.body.name && !req.body.price && !req.body.amount) {
		res.status(400).send({message: 'Content can not be empty!'});
		return;
	}
	
	const id = req.params.id;

	Stock.findByIdAndUpdate(id, req.body, { useFindAndModify: false })
		.then(data => {
			if (!data) {
				res.status(404).send({
					message: `Cannot update Stock with id=${id}.`
				});
			} else {res.send({ message: 'Stock was updated successfully.' });}
		})
		.catch(err => {
			res.status(500).send({
				message: err.message || 'Error updating Stock with id=' + id
			});
		});
};

exports.delete = (req, res) => {
	const id = req.params.id;

	Stock.findByIdAndRemove(id)
		.then(data => {
			if (!data) {
				res.status(404).send({
					message: `Cannot delete Stock with id=${id}.`
				});
			} else {
				res.send({
					message: 'Stock was deleted successfully!'
				});
			}
		})
		.catch(err => {
			res.status(500).send({
				message: err.message || 'Could not delete Stock with id=' + id
			});
		});
};

