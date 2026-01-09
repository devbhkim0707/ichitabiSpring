// common.js

// 메인 페이지 외 헤더 영역 검색 메뉴 표시를 위한 변수
let isMainPage = false;

// 헤더 영역 렌더링
$(function () {

  $(function () {
    const navBarRegionBtn = $('#region-btn');
    const dropdown = $('#dropdown');

    if (navBarRegionBtn.length && dropdown.length) {
      navBarRegionBtn.on('mouseenter', () => {
        dropdown.stop().fadeIn(200);
      });

      navBarRegionBtn.on('mouseleave', () => {
        dropdown.stop().fadeOut(200);
      });

      dropdown.on('mouseenter', () => {
        dropdown.stop().fadeIn(200);
      });

      dropdown.on('mouseleave', () => {
        dropdown.stop().fadeOut(200);
      });
    }
  });

  $('#header').load('/fragment/header.html', function () {
    const searchInputEl = document.getElementById('header-search');

    // 헤더 영역의 검색 메뉴 표시 여부 결정
    if (isMainPage) {
      searchInputEl.style.display = 'none';
    } else if (!isMainPage) {
      searchInputEl.style.display = 'block';
    }

  });

  // 게시글 작성 플로팅 버튼
  if (isLoggedInBoolean) {
    $('#floating-btn').load('../components/floating_button.html', () => {
      const floatBtn = $('#review-write-btn');
      const toolTip = $('#speech-bubble');

      floatBtn.on('mouseenter', () => {
        toolTip.stop().fadeIn();
      });

      floatBtn.on('mouseleave', () => {
        toolTip.stop().fadeOut();
      });
    });
  }

  $('#footer').load('../components/footer.html');
});
