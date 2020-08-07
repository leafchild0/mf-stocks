<template>
	<v-app>
		<v-main>
			<v-text-field
				v-model="search"
				class="mx-4"
				flat
				hide-details
				label="Search">
			</v-text-field>

			<stock :stocks="filtered"></stock>
		</v-main>
	</v-app>
</template>

<script>

import Stock from './components/Stock';
import auth from './auth/authApi';

export default {
	name: 'App',

	components: {
		Stock
	},
	computed: {
		filtered() {
			if (this.search.length < 3) return this.stocks;
			else return this.stocks.filter(s => s.name.toLowerCase().includes(this.search.toLowerCase()));
		}
	},
	data: () => ({
		stocks: [],
		search: ''
	}),
	async created() {
		try {
			const stocksResponse = await auth.get('/stocks');
			this.stocks = stocksResponse.data;
		} catch (e) {
			console.error(e);
		}
	}
};
</script>
