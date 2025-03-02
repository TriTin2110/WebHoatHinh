/**
 * 
 */
function checkPassword() {
                let password = document.getElementById('password').value;
                let confirmPassword = document.getElementById('confirm-password').value;

                if(password != '' && password != confirmPassword)
                {
                      password.value = '';
                        confirmPassword.value = '';
                        document.getElementById('password-error').style.display = 'block'
                        return false;
                }
                else
                {
                         document.getElementById('password-error').style.display = 'none'
                }
        }