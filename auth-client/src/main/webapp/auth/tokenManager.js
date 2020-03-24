/**
 * @author victor
 * @date 22.03.2020
 */

let token = '';

function checkAndPopulateToken() {

	if (window.sessionStorage) {
		const userToken = sessionStorage.getItem('token');
		if (userToken) {
			token = userToken;
		}
	}
}

function setToken(newToken) {

	if (window.sessionStorage && newToken) {
		token = newToken;
		sessionStorage.setItem('token', token);
	}
}

function getToken() {

	return token;
}

export default {
	checkAndPopulateToken,
	getToken,
	setToken
};
