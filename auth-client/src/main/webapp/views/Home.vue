<template>
	<div class='home'>
		<div class="events" v-for="e in events" :key="e.name">
			<span class="event-name">{{e.name}}</span>
			<span class="event-date">{{e.date}}</span>
			<span class="event-time">{{e.time}}</span>
			<span class="event-message">{{e.message}}</span>
		</div>
	</div>
</template>

<script>
	import authApi from '../auth/authApi';

	export default {
		name: 'home',
		data() {
			return {
				events: []
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
