import Vue from 'vue';
import VueRouter from 'vue-router';
import Home from '../views/Home.vue';
import Login from '../views/Login.vue';
import SignUp from '../views/SignUp.vue';
import tokenManager from '../auth/tokenManager';

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
		path: '/home',
		name: 'home',
		component: Home,
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
	const currentUser = tokenManager.getToken();
	const requiresAuth = to.matched.some(record => record.meta.requiresAuth);

	if (requiresAuth && !currentUser) {
		const loginPath = window.location.pathname;
		next({name:'login', query:{from:loginPath}});
	} else if (!requiresAuth && currentUser) {
		next('home');
	} else {
		next();
	}
});

export default router;
