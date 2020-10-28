<template>
	<v-app>
		<v-main>
			<v-row align='center' justify='center'>
				<v-col cols='12' sm='8' md='4'>
					<v-card class='elevation-12'>
						<v-toolbar color='primary' flat>
							<v-toolbar-title class='headline mb-1 white--text'>User Account</v-toolbar-title>
						</v-toolbar>
						<v-card-text>
							<v-form v-model='valid' ref='form'>
								<v-row justify='space-around' class='upload-row'>
									<v-avatar size='64' class='ma-2'>
										<span v-show='!image' class='white--text headline'></span>
										<img
												v-show='image'
												:src='image'
												alt='User avatar'>
									</v-avatar>
									<image-uploader @onImageUpload='onImageUpload'/>
								</v-row>

								<v-text-field
										label='Username'
										name='username'
										type='text'
										:rules='usernameRules'
										counter
										hint='More than 6 characters, letters and numbers'
										required
										v-model='username'/>

								<v-text-field
										label='Email name'
										name='email'
										type='text'
										required
										:rules='[v => !!v || "Email is required"]'
										v-model='email'/>

								<v-text-field
										label='Password'
										name='password'
										type='password'
										:rules='passwordRules'
										counter
										hint='Provide password'
										required
										v-model='password'/>

								<v-text-field
										id='password'
										label='Confirm password'
										name='confirm_password'
										type='password'
										:rules='[v => v === this.password || "Passwords are not equal"]'
										hint='Confirm password'
										required/>
							</v-form>
						</v-card-text>
						<v-card-actions>
							<v-btn @click='save' :class='{ grey: !valid, green: valid }'>Save</v-btn>
							<v-spacer/>
							<v-btn color='grey' @click='cancel'>Cancel</v-btn>
						</v-card-actions>
					</v-card>
				</v-col>
			</v-row>
		</v-main>
	</v-app>
</template>

<script>
	import ImageUploader from '@/components/ImageUploader';
	import authApi from '@/auth/authApi';

	export default {
		name: 'user-account',
		components: {
			ImageUploader
		},
		data() {
			return {
				image: null,
				password: '',
				username: '',
				email: '',
				valid: false,
				passwordRules: [
					v => !!v || 'Password is required',
					v => (v && v.length >= 6) || 'Name must be more than 6 characters',
					v =>
						/(?=.*\d)(?=.*[a-z])(?=.*[A-Z])/.test(v) ||
						'At least one uppercase, one lowercase, one digit required',
					v => (v && v.length < 14) || 'Username should be less than 14 characters',
					v => v === this.password || 'Password are not equal'
				],
				usernameRules: [
					v => !!v || 'Username is required',
					v => (v && v.length >= 6) || 'Username must be more than 6 characters',
					v => /^[a-z0-9]+$/.test(v) || 'Only lowercase letters and numbers allowed',
					v => (v && v.length < 14) || 'Username should be less than 14 characters'
				],
				imageRules: [
					value => !value || value.size < 2000000 || 'Avatar size should be less than 2 MB!',
				],
			};
		},
		methods: {
			async save() {
				this.validateForm();
				// Should save and close, i.e. emit an event
				try {

					if (this.valid) {
						await authApi.put('/', {
							username: this.username,
							password: this.password,
							email: this.email,
						});

						this.$toastr.i('Your settings has been updated');
						
					}
				} catch(e) {
					// eslint-disable-next-line no-console
					console.log(e);
					this.$toastr.e('Ups... Something went wrong');
				}
			},
			cancel() {
				// eslint-disable-next-line no-console
				console.log('Cancel');
				this.clearForm();
				// Should emit an event somewhere
				this.$emit('close');
			},
			onImageUpload(dataUrl) {
				this.image = dataUrl;
			},
			clearForm() {
				this.$refs.form.reset();
			},
			validateForm() {
				this.$refs.form.validate();
			},
		},
		async created() {
			try {
				// Load user info
				const response = await authApi.get('/');
				[this.username, this.email, this.image] = response.data;
			} catch(e) {
				this.$toastr.e('Ups... Something went wrong');
			}
		}
	};
</script>
<style scoped lang="scss">
	.user-image {
		max-height: 100%;
		max-width: 100%;
	}

	.upload-row {
		margin: 0;
	}
</style>
