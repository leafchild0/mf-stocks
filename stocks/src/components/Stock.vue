<template>
	<div class="stocks">
		<v-row align="center" justify="center">
			<v-card
				:key="s.id"
				v-for="s in stocks"
				class="stock d-block d-inline-block"
				max-width="500"
				width="300"
				outlined>
				<v-list-item>
					<v-list-item-avatar color="grey"></v-list-item-avatar>
					<v-list-item-content>
						<v-list-item-title class="headline">{{ s.name }}</v-list-item-title>
					</v-list-item-content>
				</v-list-item>

				<v-card-text>
					<div class="stock-price text-h6">Price: {{ s.price }}</div>
					<div class="stock-amount subtitle-1">Price: {{ s.amount }}</div>
				</v-card-text>

				<v-card-actions>
					<v-btn>Buy</v-btn>
					<v-spacer></v-spacer>
					<v-text-field label="Amount" dense hide-details></v-text-field>
				</v-card-actions>
			</v-card>
		</v-row>
	</div>

</template>

<script>
export default {
	name: 'stock',
	props: {
		stocks: Array
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
