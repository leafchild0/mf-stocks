<template>
	<div class="cart">
		<v-data-table
			:headers="headers"
			:items="carts"
			sort-by="amount"
			class="elevation-1">
			<template v-slot:top>
				<v-toolbar flat color="white">
					<v-toolbar-title>Your Cart</v-toolbar-title>
					<v-divider
						class="mx-4"
						inset
						vertical></v-divider>
					<v-spacer></v-spacer>
					<v-dialog v-model="dialog" max-width="500px">
						<template v-slot:activator="{ on, attrs }">
							<v-btn
								color="primary"
								dark
								class="mb-2"
								v-bind="attrs"
								v-on="on">New Item
							</v-btn>
						</template>
						<v-card>
							<v-card-title>
								<span class="headline">{{ formTitle }}</span>
							</v-card-title>

							<v-card-text>
								<v-container>
									<v-row>
										<v-col cols="12" sm="6" md="4">
											<v-text-field v-model="editedItem.stockName" label="Stock name"></v-text-field>
										</v-col>
										<v-col cols="12" sm="6" md="4">
											<v-text-field v-model="editedItem.amount" label="Amount"></v-text-field>
										</v-col>
										<v-col cols="12" sm="6" md="4">
											<v-text-field v-model="editedItem.type" label="Type"></v-text-field>
										</v-col>
									</v-row>
								</v-container>
							</v-card-text>

							<v-card-actions>
								<v-spacer></v-spacer>
								<v-btn color="blue darken-1" text @click="close">Cancel</v-btn>
								<v-btn color="blue darken-1" text @click="save">Save</v-btn>
							</v-card-actions>
						</v-card>
					</v-dialog>
				</v-toolbar>
			</template>
			<template v-slot:item.actions="{ item }">
				<v-icon
					small
					class="mr-2"
					@click="editItem(item)">
					mdi-pencil
				</v-icon>
				<v-icon
					small
					@click="deleteItem(item)">
					mdi-delete
				</v-icon>
			</template>
		</v-data-table>
	</div>

</template>

<script>

export default {
	name: 'cart',
	props: {
		carts: Array
	},
	data: () => ({
		dialog: false,
		headers: [
			{
				text: 'Stock',
				align: 'start',
				value: 'stockName',
			},
			{text: 'Amount', value: 'amount'},
			{text: 'Type', value: 'type'}
		],
		editedIndex: -1,
		editedItem: {
			stockName: '',
			amount: 0,
			type: ''
		},
		defaultItem: {
			stockName: '',
			amount: 0,
			type: ''
		},
	}),
	computed: {
		formTitle()
		{
			return this.editedIndex === -1 ? 'New Item' : 'Edit Item';
		},
	},
	watch: {
		dialog(val)
		{
			val || this.close();
		},
	},
	methods: {
		editItem(item)
		{
			this.editedIndex = this.carts.indexOf(item);
			this.editedItem = Object.assign({}, item);
			this.dialog = true;
		},

		deleteItem(item)
		{
			const index = this.carts.indexOf(item);
			this.carts.splice(index, 1);
		},

		close()
		{
			this.dialog = false;
			this.$nextTick(() =>
			{
				this.editedItem = Object.assign({}, this.defaultItem);
				this.editedIndex = -1;
			});
		},

		save()
		{
			if (this.editedIndex > -1)
			{
				Object.assign(this.carts[this.editedIndex], this.editedItem);
			}
			else
			{
				this.carts.push(this.editedItem);
			}
			this.close();
		},
	},
};
</script>

<style scoped lang='scss'>

	.cart {
		width: 100%;
		height: 100%;
	}

</style>
