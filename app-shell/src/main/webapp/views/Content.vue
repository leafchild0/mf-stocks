<template>
	<div class='content'>
		<div class='tabs'>
			<v-tabs centered>
				<v-tab @click='tabSelected = 0'>Stocks</v-tab>
				<v-tab @click='tabSelected = 1'>History</v-tab>
			</v-tabs>
		</div>
		<content-frame :data-url="contentUrl"></content-frame>
	</div>
</template>

<script>

	import ContentFrame from '@/components/ContentFrame';
	export default {
		name: 'content',
		components: {ContentFrame},
		data() {
			return {
				tabSelected: 0
			};
		},
		computed: {
			contentUrl() {
				let url;
				switch (this.tabSelected) {
					case 0:
					default:
						url = 'http://localhost:3000';
						break;
					case 1:
						url = 'http://localhost:9000';
						break;
				}
				return url;
			}
		}
	};
</script>

<style lang="scss">

	.content {
		position: relative;

		.tabs {
			display: flex;
			align-content: center;
			justify-content: center;
			margin-top: 50px;
			padding-bottom: 20px;
			flex-direction: column;
		}

	}
</style>
