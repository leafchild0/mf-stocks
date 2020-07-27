import Vue from 'vue';
import App from './App.vue';
import router from './router';
import tokenManager from './auth/tokenManager';
import vuetify from './VuetifyConf';
import Toastr from 'vue-toastr';

Vue.config.productionTip = false;

// Check token in session storage
tokenManager.checkAndPopulateToken();
initApp();

function initApp() {

	new Vue({
		router,
		vuetify,
		render: h => h(App)
	}).$mount('#app');

	Vue.use(Toastr, {
		defaultPosition: 'toast-bottom-center',
		defaultProgressBar: false
	});

}
