/**
 * Special axios instance with token passed
 *
 * @author victor
 * @date 11.12.2019
 */

import axios from 'axios';

const authInstance = axios.create({
	baseURL: '/api'
});

authInstance.interceptors.request.use(config => {
	//config.headers.common['Authorization'] = 'Bearer ' + store.getters['getUserToken'];

	return config;
});

export default authInstance;
