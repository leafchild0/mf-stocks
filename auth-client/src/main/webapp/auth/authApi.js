/**
 * Special axios instance with token passed
 * @author victor
 * @date 11.12.2019
 * @project: auth-buddy
 */

import axios from 'axios';
import tokenManager from './tokenManager';

const authInstance = axios.create({
	baseURL: '/gateway-service/'
});

authInstance.interceptors.request.use(config => {

	config.headers.common['Authorization'] = 'Bearer ' + tokenManager.getToken();
	return config;
});

export default authInstance;
