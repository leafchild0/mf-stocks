<template>
	<v-file-input
			ref='uploader'
			:rules='rules'
			accept='image/*'
			placeholder='Pick an avatar'
			prepend-icon=''
			label='Avatar' :readonly='readonly' v-on:change='onUpload'
			hint='Pick new avatar'/>
</template>

<script>
	export default {
		name: 'image-uploader',
		props: ['readonly'],
		data() {
			return {
				image: null,
				valid: false,
				rules: [
					value => (!value || value.length === 0 || value.size < 5000000) ||
						'Avatar size should be less than 0.5 MB',
				]
			};
		},
		methods: {
			onUpload() {
				const reader = new FileReader();
				reader.onload = (e) => {
					this.image = e.target.result;
					this.$emit('onImageUpload', this.image, this.valid);
				};
				if (this.$refs.uploader.internalValue) {
					reader.readAsDataURL(this.$refs.uploader.internalValue, this.valid);
				} else {
					this.$emit('onImageUpload', null, this.valid);
				}
			}
		}
	};

</script>

<style scoped lang='scss'>

</style>
