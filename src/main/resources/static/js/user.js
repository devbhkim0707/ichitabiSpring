// ================= 공통 =================
function isValidEmail(email) {
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  return emailRegex.test(email);
}

// ================= 회원가입 =================
document.addEventListener('DOMContentLoaded', () => {
  const signupForm = document.getElementById('signupForm');
  if (!signupForm) {
    console.info('회원가입 페이지가 아님 → 회원가입 JS 스킵');
    return;
  }

  const maleBtn = document.querySelector('.male-Btn');
  const femaleBtn = document.querySelector('.female-Btn');
  const emailInput = document.getElementById('email');
  const pwInput = document.getElementById('password');
  const rePwInput = document.getElementById('re-password');
  const nameInput = document.getElementById('name');
  const birthInput = document.getElementById('birth-date');
  const agreeCheckbox = document.getElementById('agree');

  let selectedGender = null;

  maleBtn?.addEventListener('click', () => {
    selectedGender = 'male';
    maleBtn.classList.add('selected');
    femaleBtn?.classList.remove('selected');
  });

  femaleBtn?.addEventListener('click', () => {
    selectedGender = 'female';
    femaleBtn.classList.add('selected');
    maleBtn?.classList.remove('selected');
  });

  signupForm.addEventListener('submit', (e) => {
    const email = emailInput.value.trim();
    const password = pwInput.value.trim();
    const rePassword = rePwInput.value.trim();
    const nickname = nameInput.value.trim();
    const birth = birthInput.value.trim();

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
  const loginForm = document.getElementById('loginForm');
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
});
