// festivals_list.js

const select = document.getElementById('season-select');

// 변경 시 이동
select.addEventListener('change', () => {
  const season = select.value;
  location.href = season
    ? `/festival/list?season=${season}`
    : `/festival/list`;
});