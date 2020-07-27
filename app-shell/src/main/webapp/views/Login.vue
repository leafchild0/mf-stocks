<template>
	<div class='login'>
		<v-container class='fill-height' fluid>
			<v-row align='center' justify='center'>
				<v-col cols='12' sm='8' md='4'>
					<v-card class='elevation-12'>
						<v-toolbar color='primary' flat>
							<v-toolbar-title class='headline mb-1'>Login</v-toolbar-title>
						</v-toolbar>
						<v-card-text>
							<v-form v-model='valid' ref='form'>
								<v-text-field
										label='Login'
										name='login'
										prepend-icon='person'
										type='text'
										:rules='[v => !!v || "Username is required"]'
										v-model='username'/>

								<v-text-field
										label='Password'
										name='password'
										prepend-icon='lock'
										type='password'
										:rules='[v => !!v || "Password is required"]'
										v-model='password'/>
							</v-form>
						</v-card-text>
						<v-card-actions>
							<v-btn text color='green darken-2' @click='goToSignUp'>
								New? Sign up
							</v-btn>
							<v-spacer/>
							<v-btn :class='{ grey: !valid, blue: valid }' @click='login'>
								Login
							</v-btn>
						</v-card-actions>
					</v-card>
				</v-col>
			</v-row>
		</v-container>
	</div>
</template>

<script>
	import authApi from '../auth/authApi';
	import tokenManager from '../auth/tokenManager';

	export default {
		name: 'login',
		data: function() {
			return {
				username: '',
				password: '',
				valid: false
			};
		},
		methods: {
			login: function() {
				this.$refs.form.validate();
				const self = this;

				if (this.valid) {
					authApi.post('auth/login', {
							username: this.username,
							password: this.password
						})
						.then(response => {
								tokenManager.setToken(response.data.accessToken);
								self.$router.replace('home');
							},
							err => {
								if (err.response.status === 401) {
									self.$toastr.e('Username or Password is incorrect');
								}
								else {
									self.$toastr.e('Ups... Something went wrong');
								}
							}
						);
				}
			},
			goToSignUp() {
				this.$router.replace('signup');
			}
		}
	};
</script>

<style scoped lang="scss">

	.login {
		display: block;
		height: 100%;
		width: 100%;
	}
</style>
