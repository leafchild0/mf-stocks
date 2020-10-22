<template>
	<v-app>
		<v-main class="d-flex justify-center">
			<v-row align="center" justify="center">
				<cart :carts="carts"></cart>
			</v-row>
		</v-main>
	</v-app>
</template>

<script>

import Cart from '@/components/Cart';
import auth from './auth/authApi';

export default {
	name: 'App',
	components: {
		Cart
	},
	data: () => ( {
		carts: [],
		search: '',
		notification: {
			color: 'success',
			message: '',
			show: false
		}
	} ),
	async created() {
		try {
			const cartsResponse = await auth.get('/carts');
			if (cartsResponse.status === 200) this.carts = cartsResponse.data;
		} catch (e) {
			// eslint-disable-next-line no-console
			console.error(e);
		}
	}
};
</script>

<style scoped lang='scss'>

.search-box {
	max-width: 500px;
	margin: 0 auto;
}

</style>
