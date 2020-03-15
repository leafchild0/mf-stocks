/**
 * Special axios instance with token passed
 * @author victor
 * @date 11.12.2019
 * @project: auth-buddy
 */

import axios from 'axios';

const authInstance = axios.create({});

authInstance.interceptors.request.use(config => {
	// TODO: Should simply poke session to get that token
	//config.headers.common['Authorization'] = 'Bearer ' + store.getters['getUserToken'];
	//store.commit('setIsLoading',true);
	return config;
});

export default authInstance;
