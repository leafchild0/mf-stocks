import Vue from 'vue';
import UserAccount from './UserAccount.vue';
import vuetify from './plugins/vuetify';
import Toastr from 'vue-toastr';
Vue.config.productionTip = false;
new Vue({
    vuetify,
    render: h => h(UserAccount),
}).$mount('#app');
Vue.use(Toastr, {
    defaultPosition: 'toast-bottom-center',
    defaultProgressBar: false,
});
//# sourceMappingURL=main.js.map