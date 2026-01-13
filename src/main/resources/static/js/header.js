document.addEventListener('DOMContentLoaded', () => {
    const input = document.getElementById("search-top-input");
    const searchBtn = document.getElementById("search-top");

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
});