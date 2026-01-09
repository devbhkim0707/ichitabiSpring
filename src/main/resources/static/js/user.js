// ================= 공통 =================
function isValidEmail(email) {
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  return emailRegex.test(email);
}

// ================= 회원가입 =================
document.addEventListener('DOMContentLoaded', () => {
  const signupForm = document.getElementById('signup-container');
  if (!signupForm) {
    console.info('회원가입 페이지가 아님 → 회원가입 JS 스킵');
    return;
  }

  const maleBtn = document.querySelector('.male-Btn');
  const femaleBtn = document.querySelector('.female-Btn');
  const emailInput = document.getElementById('email');
  const pwInput = document.getElementById('password');
  const rePwInput = document.getElementById('confirmPassword');
  const togglePassword = document.getElementById('togglePassword');
  const toggleRePassword = document.getElementById('toggleRePassword');
  const nameInput = document.getElementById('name');
  const birthInput = document.getElementById('birth');
  const agreeCheckbox = document.getElementById('agree');

  let selectedGender = null;

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

  maleBtn?.addEventListener('click', () => {
    selectedGender = 'male';
    maleBtn.classList.add('selected');
    femaleBtn?.classList.remove('selected');
    document.getElementById('gender').value = 'MALE';
  });

  femaleBtn?.addEventListener('click', () => {
    selectedGender = 'female';
    femaleBtn.classList.add('selected');
    maleBtn?.classList.remove('selected');
    document.getElementById('gender').value = 'FEMALE';
  });

  signupForm.addEventListener('submit', (e) => {
    const email = emailInput.value.trim();
    const password = pwInput.value.trim();
    const rePassword = rePwInput.value.trim();
    const nickname = nameInput.value.trim();
    const birth = birthInput.value.trim();

    function isStrongPassword(password) {
      const lengthCheck = password.length >= 8;
      const hasLatter = /[a-zA-Z]/.test(password);
      const hasNumber = /[0-9]/.test(password);
      const hasSpecial = /[!@#$%^&*(),.?":{}|<>]/.test(password);
      return lengthCheck && hasLatter && hasNumber && hasSpecial;
    }

    if (!email || !password || !rePassword || !nickname || !birth) {
      e.preventDefault();
      alert('모든 입력 칸을 채워주세요.');
      return;
    }

    if (!isValidEmail(email)) {
      e.preventDefault();
      alert('올바른 이메일 형식이 아닙니다.');
      return;
    }

    if (password !== rePassword) {
      e.preventDefault();
      alert('비밀번호가 일치하지 않습니다.');
      return;
    }

    if (!isStrongPassword(password)) {
      e.preventDefault();
      alert('비밀번호는 8자 이상, 영문/숫자/특수문자를 포함해야합니다.');
      pwInput.focus();
      return;
    }

    if (!/^\d{8}$/.test(birth)) {
      e.preventDefault();
      alert('생년월일은 8자리 숫자로 입력해주세요.(예:19990101)');
      birthInput.focus();
      return;
    }

    if (!selectedGender) {
      e.preventDefault();
      alert('성별을 선택해주세요.');
      return;
    }

    if (!agreeCheckbox.checked) {
      e.preventDefault();
      alert('약관에 동의해주세요.');
      return;
    }

  });
});

// ================= 로그인 =================
document.addEventListener('DOMContentLoaded', () => {
  const loginForm = document.getElementById('login-container');
  if (!loginForm) {
    console.info('로그인 페이지가 아님 → 로그인 JS 스킵');
    return;
  }

  const loginIdInput = document.getElementById('login-id');
  const loginPwInput = document.getElementById('login-password');
  const saveIdCheckbox = document.getElementById('save-id');

  // 저장된 아이디 자동 입력
  const savedId = localStorage.getItem('savedId');
  if (savedId) {
    loginIdInput.value = savedId;
    saveIdCheckbox.checked = true;
  }

  loginForm.addEventListener('submit', (e) => {
    const email = loginIdInput.value.trim();
    const pw = loginPwInput.value.trim();

    if (!email || !pw) {
      e.preventDefault();                       // validation 실패 시 form 의 POST method 호출 방지
      alert('이메일과 비밀번호를 입력해주세요.');
      return;
    }

    // 아이디 저장 처리 (로그인 시도 시)
    if (saveIdCheckbox.checked) {
      localStorage.setItem('savedId', email);
    } else {
      localStorage.removeItem('savedId');
    }
  });

  //모달관련
  const openModalBtn = document.getElementById('open-modal');
  const modal = document.getElementById('pw-modal');
  const closeModalBtn = document.getElementById('close-modal');

  openModalBtn.addEventListener('click', function (e) {
    e.preventDefault();
    modal.style.display = 'flex';
  });

  closeModalBtn.addEventListener('click', function () {
    modal.style.display = 'none';
  });

  // 배경 클릭 시 닫기
  window.addEventListener('click', function (e) {
    if (e.target === modal) {
      modal.style.display = 'none';
    }
  });
});
