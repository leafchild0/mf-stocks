import Vue from 'vue';
import VueRouter from 'vue-router';
import Login from '../views/Login.vue';
import SignUp from '../views/SignUp.vue';
import Content from '@/views/Content';
import UserAccount from '@/views/UserAccount';
import Cart from '@/views/Cart';

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
		path: '/',
		name: 'content',
		component: Content,
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
	{
		path: '/',
		name: 'user',
		component: UserAccount,
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

// router.beforeEach((to, from, next) => {
//
// 	// We can just check if token is there
// 	const currentUser = store.state.isUserLogged;
// 	const requiresAuth = to.matched.some(record => record.meta.requiresAuth);
//
// 	if (requiresAuth && !currentUser) {
// 		next('login');
// 	} else if (!requiresAuth && currentUser) {
// 		next('content');
// 	} else {
// 		next();
// 	}
// });

export default router;
