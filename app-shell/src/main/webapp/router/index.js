import Vue from 'vue';
import VueRouter from 'vue-router';
import Stocks from '../views/Stocks.vue';
import PurchaseHistory from '../views/History.vue';
import Login from '../views/Login.vue';
import SignUp from '../views/SignUp.vue';
import Cart from "../views/Cart.vue";
import store from '../store/index';

Vue.use(VueRouter);

const routes = [
	{
		path: '/signup',
		name: 'signup',
		component: SignUp
	},
	{
		path: '/login',
		name: 'login',
		component: Login
	},
	{
		path: '/stocks',
		name: 'stocks',
		component: Stocks,
		meta: {
			requiresAuth: true
		}
	},
	{
		path: '/history',
		name: 'history',
		component: PurchaseHistory,
		meta: {
			requiresAuth: true
		}
	},
	{
		path: '/cart',
		name: 'cart',
		component: Cart,
		meta: {
			requiresAuth: true
		}
	},
	// All unknown routes should go to Login
	{
		path: '*',
		redirect: '/login'
	}
];

const router = new VueRouter({
	mode: 'history',
	base: process.env.BASE_URL,
	routes
});

router.beforeEach((to, from, next) => {

	// We can just check if token is there
	const currentUser = store.state.isUserLogged;
	const requiresAuth = to.matched.some(record => record.meta.requiresAuth);

	if (requiresAuth && !currentUser) {
		next('login');
	} else if (!requiresAuth && currentUser) {
		next('stocks');
	} else {
		next();
	}
});

export default router;
