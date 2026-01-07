// main.js
isMainPage = true;

// 간토 지방 클릭 이벤트 href
//const regionKanto = document.getElementsByClassName('kiqJ8lXv0XRSXSa_ThjML')[2];
//
//regionKanto.addEventListener('click', () => {
//  window.location.href = './reviews/map.html';
//});

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
  const defaultHash = document.querySelector('#hash-div p[data-hash="맛집"]');
  if (defaultHash) {
    loadReviews('맛집', defaultHash);
  }
});



seasons.forEach((season, i) => {
  season.addEventListener('click', () => {
    const festivalsListUl = document.querySelector('#festivals-list ul');

    if (seasons[i].classList.contains('active')) {
      seasons[i].classList.remove('active');
      currentIndex = 0;
      festivalsListUl.style.transform = 'translateX(0px)';
      renderFestivals();
      return;
    }

    seasons.forEach((festival) => {
      festival.classList.remove('active');
    });

    seasons[i].classList.add('active');
    currentIndex = 0;
    festivalsListUl.style.transform = 'translateX(0px)';
    renderFestivals(i + 1);
  });
});

renderFestivals();

// 축제 영역 렌더링
const seasonEnum = {
  1: 'spring',
  2: 'summer',
  3: 'fall',
  4: 'winter',
};

const festivalsUl = document.querySelector('#festivals-list ul');
const festivalsTemplateEl = document.getElementById('festival-item-template');

async function renderFestivals(season) {
  const festivals = await getFestivalsData();
  festivalsListUl.innerHTML = '';

  if (!season) {
    festivals.map((festival) => {
      appendLi(festival);
    });
  } else if (seasonEnum[season] === 'spring') {
    festivals.map((festival) => {
      if (seasonEnum[festival.season] === 'spring') appendLi(festival);
    });
  } else if (seasonEnum[season] === 'summer') {
    festivals.map((festival) => {
      if (seasonEnum[festival.season] === 'summer') appendLi(festival);
    });
  } else if (seasonEnum[season] === 'fall') {
    festivals.map((festival) => {
      if (seasonEnum[festival.season] === 'fall') appendLi(festival);
    });
  } else if (seasonEnum[season] === 'winter') {
    festivals.map((festival) => {
      if (seasonEnum[festival.season] === 'winter') appendLi(festival);
    });
  }

  function appendLi(festival) {
    const cloneTemplateLi =
      festivalsTemplateEl.content.firstElementChild.cloneNode(true);

    cloneTemplateLi.querySelector('.festival-photo img').src =
      festival.imagePath;
    cloneTemplateLi.querySelector('.festival-photo img').alt = festival.title;
    cloneTemplateLi.querySelector('.title').textContent = festival.title;
    festivalsUl.appendChild(cloneTemplateLi);
  }
}

function getFestivalsData() {
  return fetch('./resources/data/festivals.json').then((res) => {
    return res.json();
  });
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
