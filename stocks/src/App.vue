<template>
	<v-app>
		<v-main class="d-flex justify-center">
			<div class="search-box">
				<v-text-field
					clearable
					v-model="search"
					flat
					prepend-inner-icon="mdi-search-web"
					hide-details
					label="Search">
				</v-text-field>
			</div>
			<v-row align="center" justify="center">
				<div class="stocks mx-4 d-flex justify-center" :key="s.id" v-for="s in filtered">
					<stock :stock="s"
					@add="addToCart"></stock>
				</div>
			</v-row>
			<v-snackbar
				v-model="notification.show"
				absolute
				bottom
				right
				:color="notification.color">
				<span>{{ notification.message }}</span>
				<v-icon dark>mdi-checkbox-marked-circle</v-icon>
			</v-snackbar>
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
		search: '',
		notification: {
			color: 'success',
			message: '',
			show: false
		}
	}),
	methods: {
		addToCart(toAdd) {
			// Should be added somewhere
			// Show notification
			// eslint-disable-next-line no-console
			console.log(toAdd);
			this.notification.message = 'Stock has been added to your cart';
			this.notification.show = true;
		}
	},
	async created() {
		try {
			const stocksResponse = await auth.get('/stocks');
			this.stocks = stocksResponse.data;
		} catch (e) {
			// eslint-disable-next-line no-console
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
