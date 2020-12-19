<template>
	<div class='account'>
		<content-frame :data-url="contentUrl"></content-frame>
	</div>
</template>

<script>

	import ContentFrame from '@/components/ContentFrame';
	export default {
		name: 'account',
		components: {ContentFrame},
		data() {
			return {
				contentUrl: 'http://localhost:3006'
			};
		},

	};
</script>

<style lang="scss">

	.account {
		position: relative;
	}
</style>
