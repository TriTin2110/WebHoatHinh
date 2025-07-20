const usernameInvalidMessage = "Tài khoản không được để trống và không chứa ký tự đặc biệt";
const passwordInvalidMessage = "Mật khẩu không được để trống!";
const fullNameInvalidMessage = "Tên đầy đủ không được để trống và không chứa ký tự đặc biệt";

function checkPassword() {
	let password = document.getElementById('password').value;
	let confirmPassword = document.getElementById('confirm-password').value;

	if (!password || password != confirmPassword) {
		password.value = '';
		confirmPassword.value = '';
		document.getElementById('password-error').style.display = 'block'
		return false;
	}
	else {
		document.getElementById('password-error').style.display = 'none'
		return true;
	}
}

function checkingDate()
{
	let birthDate = document.getElementById("dob").value
	let yearInput = new Date(Date.parse(birthDate)).getFullYear();
	let yearNow = new Date(Date.now()).getFullYear();
	if(yearInput > yearNow)
	{
		alert("Vui lòng nhập năm sinh hợp lệ!");
		return false;
	}
	else if((yearNow - yearInput) < 15)
	{
		alert("Chức năng đăng ký chỉ dành cho người trên 15 tuổi!");
		return false;
	}
	else{
		return true;
	}
}

function checkingFormData() {
	let btn = document.getElementById('btn-sign-up')
	let username = document.getElementById('username').value;
	let fullName = document.getElementById('full-name').value;
	let validInput = checkingData(username,usernameInvalidMessage) && checkingData(fullName, fullNameInvalidMessage) && checkPassword() && checkingDate();
	if(validInput)
	{
		btn.disabled = true;
	}
	return validInput;
}

