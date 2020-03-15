<template>
	<div class='home'>
	</div>
</template>

<script>
	import authApi from '../auth/authApi';

	export default {
		name: 'home',
		data() {
			return {
				tabSelected: 0,
				places: []
			};
		},
		created() {
			authApi.get('/api/data').then(resp => {
				this.places = resp.data;
			}).catch(() => {
				this.$toastr.e('Ups... Something went wrong');
			});
		}
	};
</script>

<style lang="scss">

	.home {
		position: relative;

	}
</style>
