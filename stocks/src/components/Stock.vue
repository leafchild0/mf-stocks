<template>
	<div class="stocks">
			<v-card
				class="stock d-block d-inline-block"
				max-width="500"
				width="300"
				outlined>
				<v-list-item>
					<v-list-item-icon>
						<v-icon color="primary">{{ getStockIcon(stock.name) }}</v-icon>
					</v-list-item-icon>
					<v-list-item-content>
						<v-list-item-title class="headline blue-grey--text">{{ stock.name }}</v-list-item-title>
					</v-list-item-content>
				</v-list-item>
				
				<v-card-text>
					<div class="stock-price text-h6 d-flex justify-space-between">
						<span>Price: </span>
						<span>{{ stock.price }}</span>
					</div>
					<div class="stock-amount subtitle-1 d-flex justify-space-between">
						<span>Amount: </span>
						<span>{{ stock.amount }}</span>
					</div>
				</v-card-text>
				
				<v-card-actions>
					<v-text-field
						label="Amount"
						v-model="amount"
						dense
						type="number"
						color="success"
						prepend-icon="mdi-checkbox-marked-circle-outline"
						:rules="[
							(v) => v < stock.amount || 'Not enough stocks to buy'
						]"
					></v-text-field>
					<v-spacer></v-spacer>
					<v-btn color="success" @click="add(stock.name)">Buy</v-btn>
				</v-card-actions>
			</v-card>
	</div>

</template>

<script>
export default {
	name: 'stock',
	props: {
		stock: Object
	},
	data: () => {
		return {
			amount: 1
		};
	},
	methods: {
		add(name) {
			// Send an event
			this.$emit('add', {
				name: name,
				amount: this.amount
			});
			// Back to basics
			this.amount = 1;
		},
		getStockIcon(name) {
			let icon = 'mdi-checkbox-blank-circle';
			
			switch (name) {
				case 'Google':
					icon = 'mdi-google';
					break;
				case 'Apple':
					icon = 'mdi-apple';
					break;
				case 'Amazon':
					icon = 'mdi-amazon';
					break;
				case 'Netflix':
					icon = 'mdi-netflix';
					break;
				case 'Facebook':
					icon = 'mdi-facebook';
					break;
			}
			
			return icon;
		}
	}
};
</script>

<style scoped lang='scss'>

	.stocks {
		width: 100%;
		height: 100%;
	}

	.stock {
		margin: 20px;
	}

</style>
