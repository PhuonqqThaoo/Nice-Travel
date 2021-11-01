var signUp = document.getElementById('button__sign-up');

document.getElementById('check-agree').onclick = function () {
    if (this.checked) {
        signUp.disabled = false;
        signUp.title = '';
    } else {
        signUp.disabled = true;
        signUp.title = 'Vui lòng đồng ý với các điều khoản và điều kiện.';
    }
}