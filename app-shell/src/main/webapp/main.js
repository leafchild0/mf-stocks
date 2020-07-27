import Vue from 'vue';
import App from './App.vue';
import router from './router';
import vuetify from './VuetifyConf';
import store from './store/index';
import {SET_TOKEN_ACTION, USER_TOKEN_KEY} from './constants';
import Toastr from 'vue-toastr';

Vue.config.productionTip = false;

// Check token in session storage
// Check token in session storage
if (window.sessionStorage) {
	const userToken = sessionStorage.getItem(USER_TOKEN_KEY);
	// Use it if exists
	if (userToken) {
		store.dispatch(SET_TOKEN_ACTION, userToken);
	}
}
initApp();

function initApp() {

	new Vue({
		router,
		vuetify,
		store,
		render: h => h(App)
	}).$mount('#app');

	Vue.use(Toastr, {
		defaultPosition: 'toast-bottom-center',
		defaultProgressBar: false
	});

}
