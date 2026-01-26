// ================= 회원가입 =================
document.addEventListener('DOMContentLoaded', () => {
  const editForm = document.getElementById('mypage-container');

  const pwInput = document.getElementById('password');
  const rePwInput = document.getElementById('confirmPassword');
  const togglePassword = document.getElementById('togglePassword');
  const toggleRePassword = document.getElementById('toggleRePassword');
  const nameInput = document.getElementById('nickname');

  // 프로필 이미지 미리보기
  const input = document.getElementById('profileImage');
  const img = document.querySelector('.profile-img-edit img');

  input.addEventListener('change', e => {
    const file = e.target.files[0];
    if (!file) return;
    img.src = URL.createObjectURL(file);
  });

  togglePassword.addEventListener('click', () => {
      const isHidden = pwInput.type === 'password';
      pwInput.type = isHidden ? 'text' : 'password';

      if (isHidden) {
        togglePassword.classList.remove('isHidden');
        togglePassword.classList.add('visible');
      } else {
        togglePassword.classList.remove('visible');
        togglePassword.classList.add('hidden');
      }
    });

    toggleRePassword.addEventListener('click', () => {
      const isHidden = rePwInput.type === 'password';
      rePwInput.type = isHidden ? 'text' : 'password';
      if (isHidden) {
        toggleRePassword.classList.remove('Hidden');
        toggleRePassword.classList.add('visible');
      } else {
        toggleRePassword.classList.remove('visible');
        toggleRePassword.classList.add('hidden');
      }
    });

  editForm.addEventListener('submit', (e) => {
    const password = pwInput.value.trim();
    const rePassword = rePwInput.value.trim();
    const nickname = nameInput.value.trim();

    function isStrongPassword(password) {
      const lengthCheck = password.length >= 8;
      const hasLatter = /[a-zA-Z]/.test(password);
      const hasNumber = /[0-9]/.test(password);
      const hasSpecial = /[!@#$%^&*(),.?":{}|<>]/.test(password);
      return lengthCheck && hasLatter && hasNumber && hasSpecial;
    }

    // 둘 다 비어 있으면 검증 스킵
    if (password === '' && rePassword === '') {
      return; // 그대로 submit
    }

    // 하나만 입력된 경우
    if (password === '' || rePassword === '') {
      e.preventDefault();
      alert('비밀번호를 모두 입력해주세요.');
      return;
    }

    // 불일치
    if (password !== rePassword) {
      e.preventDefault();
      alert('비밀번호가 일치하지 않습니다.');
      return;
    }

    // 보안 규칙 미달
    if (!isStrongPassword(password)) {
      e.preventDefault();
      alert('비밀번호는 8자 이상, 영문/숫자/특수문자를 포함해야 합니다.');
      pwInput.focus();
    }
  });
});