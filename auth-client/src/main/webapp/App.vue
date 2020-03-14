<template>
	<div id='app'>
		<v-app>
			<v-content>
				<router-view/>
			</v-content>
		</v-app>
	</div>
</template>

<script>
	import 'material-design-icons-iconfont/dist/material-design-icons.css';
	import '@mdi/font/css/materialdesignicons.css';

	export default {
		methods: {
			logout() {
				// TODO: Remove token
				//this.setUserLogged(false);
				this.$router.push('/');
			}
		}
	};
</script>

<style lang="scss">
	#app {
		font-family: "Avenir", Helvetica, Arial, sans-serif;
		-webkit-font-smoothing: antialiased;
		-moz-osx-font-smoothing: grayscale;
		text-align: center;
		color: #2c3e50;
	}
</style>
