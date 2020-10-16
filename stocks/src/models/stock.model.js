module.exports = mongoose => {
	const schema = mongoose.Schema(
		{
			name: String,
			price: Number, amount: Number
		},
		{timestamps: true}
	);

	schema.method('toJSON', function() {
		// eslint-disable-next-line no-unused-vars
		const {__v, _id, ...object} = this.toObject();
		object.id = _id;
		return object;
	});

	return mongoose.model('stock', schema);
};
