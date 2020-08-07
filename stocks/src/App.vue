<template>
	<v-app>
		<v-main class="d-flex justify-center">
			<div class="search-box">
				<v-text-field
					clearable
					v-model="search"
					class="mx-4 d-flex justify-center"
					flat
					hide-details
					label="Search">
				</v-text-field>
			</div>

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

<style lang="scss">


.search-box {
	max-width: 500px;
	margin: 0 auto;
}

</style>
