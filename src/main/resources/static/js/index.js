/**
 * 
 */
var username = /*[[${account.userName}]]*/
function checkingLogin() {
	if (username === undefined) {
		window.location.href = "/sign-in"
	}
}
function showUsername() {
	console.log(username)
}
window.addEventListener("beforeunload", () => {
	navigator.sendBeacon("localhost:8080/account/logout", JSON.stringify(username))
})
