const usernameInvalidMessage = "Tài khoản không chứa ký tự đặc biệt";
const passwordInvalidMessage = "Mật khẩu không chứa ký tự đặc biệt";
const fullNameInvalidMessage = "Tên đầy đủ không chứa ký tự đặc biệt";

function checkPassword() {
	let password = document.getElementById('password').value;
	let confirmPassword = document.getElementById('confirm-password').value;

	if (password != '' && password != confirmPassword) {
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
	let username = document.getElementById('username').value;
	let password = document.getElementById('password').value;
	let fullName = document.getElementById('full-name').value;
	if (!fullName)
		fullName = '';
		
	return checkingData(username,usernameInvalidMessage) && checkingData(password, passwordInvalidMessage) && checkingData(fullName, fullNameInvalidMessage) && checkPassword() && checkingDate();
}

