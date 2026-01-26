// review_write.js

let selectedRating = 0;
const stars = document.querySelectorAll('.star');
const starContent = document.getElementById('star-content');
const ratingsText = ['최악', '그저그럼', '보통', '좋음', '훌륭함'];

// 별점
function updateStars(rating) {
  stars.forEach((star, i) => {
    star.textContent =
      i < rating
        ? star.classList.add('rating')
        : star.classList.remove('rating');
  });
  starContent.textContent = ratingsText[rating - 1] || '';
}

stars.forEach((star, i) => {
  star.addEventListener('mouseover', () => {
    if (selectedRating === 0) updateStars(i + 1);
  });

  star.addEventListener('mouseout', () => {
    if (selectedRating === 0) updateStars(0);
  });

  star.addEventListener('click', () => {
    selectedRating = i + 1;
    updateStars(selectedRating);
  });
});

// 동행 버튼 - 중복 선택 불가, 다른 버튼 클릭시 옮겨짐
const companionBtn = document.querySelectorAll('.companion .companion-btn');
let selectedBtn = null;
// console.log(companionBtn);

companionBtn.forEach((button) => {
  button.addEventListener('click', function () {
    // 선택된 버튼이 있으면 선택 해제
    if (selectedBtn) {
      selectedBtn.classList.remove('selected');
    }
    // 현재 클릭된 버튼 선택 표시
    this.classList.add('selected');
    selectedBtn = this;

    // 선택된 값 가져오기 - 나중에 필요 할까봐
    // const selectedValue = this.dataset.value;
    // console.log('선택된 값:', selectedValue);
  });
});

// 해시태그
const hashtagBtn = document.querySelectorAll('.hashtag-btn');
const tagSselectedBtn = new Set(); // Set을 사용하여 중복 선택 방지

// 해시태그 버튼 - 중복 클릭 가능, 한번더 클릭시 원래대로
hashtagBtn.forEach((button) => {
  button.addEventListener('click', () => {
    const buttonText = button.textContent.trim();
    const textWithoutHash = buttonText.replace(/^#/, '');

    if (tagSselectedBtn.has(textWithoutHash)) {
      // 이미 선택된 버튼일 경우 선택 해제
      tagSselectedBtn.delete(textWithoutHash);
      button.classList.remove('selected');
    } else {
      // 선택되지 않은 버튼일 경우 선택
      tagSselectedBtn.add(textWithoutHash);
      button.classList.add('selected');
    }

    // console.log('선택된 버튼:', tagSselectedBtn);
  });
});

// 해시태그 추가히기
const hashtagList = document.getElementById('hashtag-list');
const hashtagInput = document.getElementById('hashtag-input');
const addHashtagBtn = document.getElementById('hashtag-add-btn');
const hashtags = [];
// const hashtagElement = document.createElement('button');

// 추가 버튼 클릭 이벤트 리스너
addHashtagBtn.addEventListener('click', addHashtag);

// 해시태그 추가 함수
function addHashtag() {
  const hashtagText = hashtagInput.value.trim();

  // console.log(hashtagText);
  // 태그 입력 확인
  if (hashtagText === '') {
    alert('해시태그를 입력해주세요.');
    return;
  }

  //모든 공백 제거
  const sanitizedHashtag = hashtagText.replace(/\s/g, '');

  // 특수문자 및 공백 확인
  const specialCharRegex = /[ `!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?~]/;
  if (specialCharRegex.test(sanitizedHashtag)) {
    alert('특수문자는 사용할 수 없습니다.');
    hashtagInput.value = '';
    return;
  }

  const hashtagsWithEun = ['맛집', '야경', '공원'];
  const hashtagsWithNeun = ['카페', '테마파크'];
  const predefinedHashtags = [...hashtagsWithEun, ...hashtagsWithNeun];

  // 기본 해시태그일 경우: 클릭 여부에 따라 안내
  if (predefinedHashtags.includes(sanitizedHashtag)) {
    const particle = hashtagsWithEun.includes(sanitizedHashtag) ? '은' : '는';

    if (tagSselectedBtn.has(sanitizedHashtag)) {
      alert(`${sanitizedHashtag}${particle} 위에서 이미 선택되었습니다.`);
    } else {
      alert(`${sanitizedHashtag}${particle} 위에서 선택해 주세요.`);
    }
    hashtagInput.value = '';
    return;
  } else if (hashtags.includes(sanitizedHashtag)) {
    alert('이미 추가된 해시태그입니다.');
    hashtagInput.value = '';
    return; // 사용자 입력 해시태그 중복 체크
  }

  // 해시태그 배열에 추가
  hashtags.push(sanitizedHashtag);

  // 해시태그 요소 생성 및 추가
  const hashtagWrapper = document.createElement('div');
  hashtagWrapper.classList.add('hashtag-item');

  const hashtagElement = document.createElement('span');
  hashtagElement.classList.add('add-hashtag');
  hashtagElement.textContent = `#${sanitizedHashtag}`;

  // 해시태그 삭제 버튼 추가
  // 삭제 버튼 생성
  const deleteButton = document.createElement('button');
  deleteButton.classList.add('delete-btn');
  deleteButton.textContent = 'x';

  // 삭제 버튼 클릭 이벤트
  deleteButton.addEventListener('click', function () {
    hashtagWrapper.remove(); // 삭제버튼 누르면 요소 삭제

    const index = hashtags.indexOf(sanitizedHashtag);
    if (index > -1) {
      hashtags.splice(index, 1);
    }
  });

  // hashtagWrapper에 조립
  hashtagWrapper.appendChild(hashtagElement);
  hashtagWrapper.appendChild(deleteButton);
  hashtagList.appendChild(hashtagWrapper);

  // 입력 필드 초기화
  hashtagInput.value = '';
}

// 엔터 키로도 해시태그 추가
hashtagInput.addEventListener('keyup', function (event) {
  if (event.key === 'Enter') {
    event.preventDefault(); // 중복되는 이벤트 한번 무효화 시키기
    // const inputValue = hashtagInput.value;
    // setTimeout(addHashtag(), 100);
    addHashtag();
  }
});

// 장소 이름 글자수 카운팅
const nameInput = document.getElementById('review-name');
const nameCount = document.getElementById('review-name-count');

nameInput.addEventListener('input', () => {
  nameCount.textContent = nameInput.value.length;
});

// 장소 이름 글자수 카운팅
const contentInput = document.getElementById('review-content');
const contentCount = document.getElementById('review-content-count');

contentInput.addEventListener('input', () => {
  contentCount.textContent = contentInput.value.length;
});

// 수정 -> 데이터 채우기
document.addEventListener('DOMContentLoaded', () => {
  // write.html 하단에 정의한 isEditMode와 existingData 변수 사용
  if (typeof isEditMode !== 'undefined' && isEditMode && existingData) {
    console.log("복구 데이터 확인 : " + existingData);

    // 기본 텍스트들
    nameInput.value = existingData.title || '';
    contentInput.value = existingData.content || '';

    // 별점 복구
    selectedRating = existingData.rating || 0;
    updateStars(selectedRating);

    // 시기 복구 - 제미나이 도움 받았지만 안받아와요 ㅜㅜㅜ
    if (existingData.date) {
          // 숫자만 추출하는 정규식 사용 (가장 안전함)
          // 예: "2025년 - 05월" -> ["2025", "05"]
          const dateNumbers = existingData.date.match(/\d+/g);

          if (dateNumbers && dateNumbers.length >= 2) {
            const yearVal = dateNumbers[0]; // "2025"
            const monthVal = parseInt(dateNumbers[1]); // "05" -> 5 (숫자로 변환해서 매칭)

            const yearSelect = document.querySelector('select[name="year"]');
            const monthSelect = document.querySelector('select[name="month"]');

            if (yearSelect) yearSelect.value = yearVal;
            if (monthSelect) monthSelect.value = monthVal.toString(); // "5"로 변환해서 매칭
          }
        }

    // 동행 버튼 복구
    const companionMap = { 1: "비즈니스", 2: "단독", 3: "가족", 4: "친구", 5: "연인" };
    const targetCompanion = companionMap[existingData.companion];

    companionBtn.forEach(btn => {
        if (btn.dataset.value === targetCompanion) {
            btn.click(); // 강제 클릭 이벤트 발생 (selected 클래스 추가됨)
        }
    });

    // 해시태그 복구
    if (existingData.hashtagList && existingData.hashtagList.length > 0) {
      existingData.hashtagList.forEach(tag => {
        const trimmedTag = tag.trim();
        // 상단 해시태그 확인
        let matchedBtn = Array.from(hashtagBtn).find(b => b.dataset.value === trimmedTag);

        if (matchedBtn) {
          matchedBtn.click(); // 이미 있는 버튼이면 클릭 처리
        } else {
          // 직접 입력했던 해시태그 추가
          hashtagInput.value = trimmedTag;
          addHashtag();
        }
      });
    }

    // 카운트 초기화
    nameCount.textContent = nameInput.value.length;
    contentCount.textContent = contentInput.value.length;
  }
});


// 작성하기 버튼 클릭시 확인창 보여주기 - 데이터 전송
function confirmAction() {

  const getCsrfFromCookie = () => {
          const name = "XSRF-TOKEN";
          const value = "; " + document.cookie;
          const parts = value.split("; " + name + "=");
          if (parts.length === 2) return parts.pop().split(";").shift();
  };

  const actionText = isEditMode ? '수정' : '작성';

  if (confirm(`${actionText}하시겠습니까?`)) {

    const csrfToken = getCsrfFromCookie();
    // CookieCsrfTokenRepository를 쓸 때는 헤더명이 보통 X-XSRF-TOKEN 입니다.
    const csrfHeader = "X-XSRF-TOKEN";

    const year = document.getElementsByName('year')[0].value;
    const month = document.getElementsByName('month')[0].value;

    if (year === 'none' || month === 'none') {
        alert("여행 시기를 선택해주세요!");
        return;
    }

    const reviewData = {
          id: isEditMode ? existingData.id : null, // 수정 -> 기존 ID를 포함
          title: document.getElementById('review-name').value,
          rating: selectedRating,
          date: `${year}년 - ${month}월`,
          companion: selectedBtn ? selectedBtn.dataset.value : '단독',
          content: document.getElementById('review-content').value,
          hashtags: [...tagSselectedBtn, ...hashtags]
        };

    console.log("================ [전송 데이터 확인] ================");
    console.log("전체 객체:", reviewData);
    console.table(reviewData); // 테이블 형태로 예쁘게 출력
    console.log("해시태그 목록:", reviewData.hashtags.join(", "));
    console.log("=================================================");

    // 데이터 검증
    if(!reviewData.title || reviewData.rating === 0) {
      alert("장소 이름과 평점을 입력해주세요!");
      return;
    }

    const headers = { 'Content-Type': 'application/json' };
    if (csrfToken) {
       headers[csrfHeader] = csrfToken;
    }

    // 서버 전송
    fetch('/review/write', {
          method: 'POST',
          headers: headers,
          body: JSON.stringify(reviewData)
        })
        .then(response => {
                if (!response.ok) {
                   if(response.status === 403) console.error("CSRF 토큰 혹은 권한 오류입니다.");
                   throw new Error('저장 실패');
                }
                return response.json(); // 서버에서 저장된 게시글의 ID 받아온다
            })
            .then(data => {
                alert(`${actionText}이 완료되었습니다!`);
                // 저장/수정 후 방금 작성한 글의 상세 페이지로 이동
                const moveId = (typeof data === 'object') ? data.id : data;
                location.href = '/review/detail/' + moveId;
            })
            .catch(error => {
                console.error('Error:', error);
                alert('처리 중 오류가 발생했습니다.');
            });

  } else {
    alert('작성을 이어서 해주세요!');
  }
}
