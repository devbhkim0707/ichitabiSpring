// admin.js
const token = document.querySelector("meta[name='_csrf']").getAttribute("content");
const header = document.querySelector("meta[name='_csrf_header']").getAttribute("content");

// Season Selection
$('#season-select').on('change', function () {
  const season = $(this).val();
  if (season != null && season != "") {
    location.href = `/admin/festival?season=${season}`;
  } else {
    location.href = `/admin/festival`;
  }
});

// delete festival event listener w/ confirm
document.addEventListener("DOMContentLoaded", () => {
    const buttons = document.querySelectorAll(".btn-delete-festival");

    buttons.forEach(button => {
        button.addEventListener("click", (event) => {
            const festivalId = event.currentTarget.dataset.id;

            if (!confirm("정말로 삭제하시겠습니까?")) {
                return;
            }

            fetch(`/admin/festival/delete/${festivalId}`, {
                method: "POST",
                headers: {
                    [header]: token
                }
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error("삭제 실패");
                }
                alert("삭제되었습니다.");
            })
            .catch(err => {
                console.error(err);
                alert("삭제 중 오류가 발생했습니다.");
            });
        });
    });
});

// Pagination
const pages = document.querySelectorAll('.page');
const pageBtns = document.querySelectorAll('.page-btn');

pages.forEach((page) => {
  page.addEventListener('click', () => {
    pages.forEach((p) => p.classList.remove('active'));
    page.classList.add('active');
  });
});

pageBtns.forEach((page) => {
  page.addEventListener('click', () => {
    pageBtns.forEach((p) => p.classList.remove('active'));
    page.classList.add('active');
  });
});

// festival edit
const input = document.getElementById('imgUrl');
const preview = document.getElementById('preview-img');

input.addEventListener('input', () => {
  preview.src = input.value;
});
