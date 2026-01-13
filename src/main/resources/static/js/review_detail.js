const likeBtn = document.getElementById('like-btn');
const likeCountEl = document.getElementById('like-count');
const reviewId = likeBtn.dataset.reviewId;

const token = document.querySelector("meta[name='_csrf']").getAttribute("content");
const header = document.querySelector("meta[name='_csrf_header']").getAttribute("content");

const editBtn = document.getElementById('edit-btn');
const deleteBtn = document.getElementById('delete-btn');

async function fetchLikeCount() {
    const res = await fetch(`/review/like?reviewId=${reviewId}`);
    if (!res.ok) throw new Error();
    const count = await res.json();
    likeCountEl.innerText = count;
}

async function checkLikeStatus() {
    const res = await fetch(`/review/like/check?reviewId=${reviewId}`);
    if (!res.ok) return;

    const liked = await res.json();

    if (liked) {
        likeBtn.classList.add('on');
    }
}



document.addEventListener('DOMContentLoaded', () => {
    fetchLikeCount();
    checkLikeStatus();
});



likeBtn.addEventListener('click', async () => {
    const isCurrentlyLiked = likeBtn.classList.contains('on');

    try {
        const res = await fetch(`/review/like?reviewId=${reviewId}`, {
            method: isCurrentlyLiked ? 'DELETE' : 'POST',
            headers: {
                [header]: token
            }
        });

        if (res.status === 401) {
            alert('로그인 후 이용해주세요.');
            return;
        }
        if (!res.ok) throw new Error();

        await fetchLikeCount();

        likeBtn.classList.toggle('on');

    } catch (e) {
        alert('오류 발생');
    }
});





const nameEl = document.getElementById('name');
const hashtagEl = document.getElementById('hashtag');
const photeLeftEl = document.querySelector('.photo-left');
const photeRight1El = document.querySelector('.photo-right1');
const photeRight2El = document.querySelector('.photo-right2');
const nickEl = document.getElementById('nick');
const genderEl = document.getElementById('gender');
const ageEl = document.getElementById('age');
const dateEl = document.getElementById('date');
const companionContentEl = document.getElementById('companion-content');
const contentEl = document.querySelector('.content-1');
const nick1El = document.getElementById('nick1');
const starEl = document.querySelectorAll('#star p');

document.addEventListener("DOMContentLoaded", () => {
  const starBox = document.getElementById("star");
  if (!starBox) return;

  const rating = Number(starBox.dataset.rating); // ex) 3
  const stars = starBox.querySelectorAll(".star");

  stars.forEach((star, index) => {
    if (index < rating) {
      star.classList.add("filled");
    }
  });
});


const recommendUlEl = document.getElementById('recommend-ul');
const recommendTempEl = document.getElementById('recommend-template');

function renderRecommend(count) {
  const nextReviews = reviews.slice(0, count);

  nextReviews.map((review) => {
    const cloneLi = recommendTempEl.content.firstElementChild.cloneNode(true);
    cloneLi.querySelector('.photo').src = review.imagePath[0];
    cloneLi.querySelector('.title').textContent = review.title;
    cloneLi.querySelector('.place').textContent = review.location;
    cloneLi.querySelector('.hash').textContent = review.hashtag
      .map((tag) => `#${tag}`)
      .join(' ');
    recommendUlEl.appendChild(cloneLi);
  });
}

renderRecommend(5);

// 상세 이미지 fallback 처리
document.querySelectorAll('#main-img img').forEach((img) => {
  // 이미지가 없거나 에러났을 때 처리
  const handleFallback = () => {
    img.style.display = 'none';

    const fallbackDiv = document.createElement('div');
    fallbackDiv.className = 'img-fallback';

    img.parentElement.appendChild(fallbackDiv);
  };

  if (!img.getAttribute('src') || img.getAttribute('src').trim() === '') {
    handleFallback(); // src가 빈 경우
  } else {
    img.onerror = handleFallback; // 로딩 실패한 경우
  }
});

// 아바타 이미지 fallback 처리
const avatarImg = document.querySelector('#avatar img');

if (avatarImg) {
  if (
    !avatarImg.getAttribute('src') ||
    avatarImg.getAttribute('src').trim() === ''
  ) {
    avatarImg.setAttribute('src', '../resources/icons/user.png');
  }

  avatarImg.onerror = function () {
    this.setAttribute('src', '../resources/icons/user.png');
  };
}

async function getUsers() {
  return fetch('../resources/data/user.json').then((res) => {
    return res.json();
  });
}
