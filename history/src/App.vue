<template>
	<v-app>
		<v-main class="d-flex justify-center">
			<v-row align="center" justify="center">
				<history :historyRecords="history"></history>
			</v-row>
		</v-main>
	</v-app>
</template>

<script>

import History from './components/History';
import auth from './auth/authApi';

export default {
	name: 'App',
	components: {
		History
	},
	data: () => ({
		history: [],
		search: '',
		notification: {
			color: 'success',
			message: '',
			show: false
		}
	}),
	methods: {},
	async created() {
		try {
			const stocksHistoryResponse = await auth.get('/history');
			this.history = stocksHistoryResponse.data;
			Array.from(this.history, record => record.date = new Date(record.date));
		} catch (e) {
			/* eslint no-console: ["error", { allow: ["warn", "error"] }] */
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
