import axios from 'axios';

const authInstance = axios.create({
	baseURL: '/'
});

authInstance.interceptors.request.use(config => {
	//config.headers.common['Authorization'] = 'Bearer ' + store.getters['getUserToken'];

	return config;
});

export default authInstance;
