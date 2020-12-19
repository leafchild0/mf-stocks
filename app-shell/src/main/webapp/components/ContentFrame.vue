<template>
	<div class='content'>
		<iframe height="100%" width="100%" v-if="dataUrl" :src={dataUrl}></iframe>
		<div v-else class="loading">Loading</div>
	</div>
</template>

<script>
	export default {
		name: 'content-frame',
		props: ['dataUrl'],
	};
</script>

<style lang="scss">

	.content {
		position: relative;
		width: 100%;
		height: 100%;

	}
</style>
