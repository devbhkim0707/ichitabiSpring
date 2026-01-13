// main.js
isMainPage = true;

const hashs = document.querySelectorAll('#hash-div p');
const reviewUl = document.getElementById('review-ul');

async function loadReviews(hashtag, clickedHash) {
  hashs.forEach(h => h.classList.remove('active'));
  if (clickedHash) clickedHash.classList.add('active');

  try {
    const response = await fetch(
      '/review/?hashtag=' + encodeURIComponent(hashtag)
    );
    const html = await response.text();
    reviewUl.innerHTML = html;
  } catch (e) {
    console.error(e);
  }
}

hashs.forEach(hash => {
  hash.addEventListener('click', () => {
    loadReviews(hash.dataset.hash, hash);
  });
});

document.addEventListener('DOMContentLoaded', () => {
    const input = document.getElementById("search-main-input");
    const searchBtn = document.getElementById("search-main");

    function goSearch() {
          const keyword = input.value.trim();

          if (!keyword) {
            alert("검색어를 입력해주세요.");
            input.focus();
            return;
          }

          location.href = "/review/search?keyword=" + encodeURIComponent(keyword);
    }

        searchBtn.addEventListener("click", (e) => {
              e.preventDefault();
              goSearch();
        });

        input.addEventListener("keydown", (e) => {
          if (e.key === "Enter") {
            e.preventDefault();
            goSearch();
          }
        });

  const defaultHash = document.querySelector('#hash-div p[data-hash="맛집"]');
  if (defaultHash) {
    loadReviews('맛집', defaultHash);
  }

  renderFestivals(null);
});


const seasonContainer = document.getElementById('hash-season');
const seasonButtons = seasonContainer.querySelectorAll('p');
const festivalsUl = document.querySelector('#festivals-list ul');

seasonButtons.forEach((btn) => {
  btn.addEventListener('click', () => {
    const isActive = btn.classList.contains('active');

    seasonButtons.forEach((el) => el.classList.remove('active'));

    if (isActive) {
      currentSeason = null;
    } else {
      btn.classList.add('active');
      currentSeason = btn.dataset.season;
    }

    currentIndex = 0;
    festivalsListUl.style.transform = 'translateX(0px)';
    renderFestivals(currentSeason);
  });
});

async function renderFestivals(season) {
  try {
    const seasonParam = (season != null) ? season : 'ALL';

    const response = await fetch(
      '/festival/?season=' + seasonParam
    );

    const html = await response.text();
    festivalsUl.innerHTML = html;
  } catch (e) {
    console.error(e);
  }
}

// 축제 영역 캐러셀
const festivalNextBtn = document.getElementById('btn-next-festival');
const festivalPrevBtn = document.getElementById('btn-prev-festival');
const festivalsListUl = document.querySelector('#festivals-list ul');

let currentIndex = 0;

festivalNextBtn.addEventListener('click', () => {
  currentIndex = slide(festivalsListUl, 'left', currentIndex);
});

festivalPrevBtn.addEventListener('click', () => {
  currentIndex = slide(festivalsListUl, 'right', currentIndex);
});

function slide(element, direction, index) {
  const slideWidth = 300;
  const transitionSpeed = 300;
  const elementLength = element.querySelectorAll('li').length;

  const visibleItems = 4;

  if (direction === 'left') {
    if (index === elementLength - visibleItems || elementLength <= 4) {
      setTimeout(() => {
        bounce(direction), 200;
      });
    } else {
      index++;
    }
  } else if (direction === 'right') {
    if (index === 0 || elementLength <= 4) {
      setTimeout(() => {
        bounce(direction), 200;
      });
    } else {
      index--;
    }
  }
  setTimeout(() => {
    element.style.transform = `translateX(${-(slideWidth * index)}px)`;
    element.style.transition = `all ${transitionSpeed}ms ease`;
  }, 100);
  return index;

  function bounce(direction) {
    if (direction === 'left') {
      element.style.transform = `translateX(${-(slideWidth * index - 100)}px)`;
    } else if (direction === 'right') {
      element.style.transform = `translateX(${-(slideWidth * index + 100)}px)`;
    }
    element.style.transition = `all ${transitionSpeed}ms ease`;
  }
}
