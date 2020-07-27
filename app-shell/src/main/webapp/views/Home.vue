<template>
	<div class='home'>
		<v-data-table
				:headers="headers"
				:items="events"
				:items-per-page="10"
				class="elevation-1"
		></v-data-table>
	</div>
</template>

<script>
	import authApi from '../auth/authApi';

	export default {
		name: 'home',
		data() {
			return {
				events: [],
				headers: [
					{text: 'Name', value: 'name'},
					{text: 'Date', value: 'date'},
					{text: 'Time', value: 'time'},
					{text: 'Message', value: 'message'}
				]
			};
		},
		created() {
			authApi.get('/data/events').then(resp => {
				this.events = resp.data;
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
