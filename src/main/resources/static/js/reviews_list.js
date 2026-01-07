document.addEventListener('DOMContentLoaded', () => {
  const items = document.querySelectorAll('.review-list');
  const moreBtn = document.querySelector('#more-btn button');
  const SHOW_COUNT = 4;

  let visibleCount = 12;

  items.forEach((item, index) => {
    if (index >= visibleCount) {
      item.style.display = 'none';
    }
  });

    if (items.length <= visibleCount) {
      document.getElementById('more-btn').style.display = 'none';
      return;
    }

  moreBtn.addEventListener('click', () => {
    visibleCount += SHOW_COUNT;

    items.forEach((item, index) => {
          if (index < visibleCount) item.style.display = '';
        });

        if (visibleCount >= items.length) {
          document.getElementById('more-btn').style.display = 'none';
        }
      });
});


