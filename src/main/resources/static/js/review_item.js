const token = document.querySelector("meta[name='_csrf']")?.getAttribute("content");
const header = document.querySelector("meta[name='_csrf_header']")?.getAttribute("content");

async function checkLikeStatus(likeBtn) {
    const reviewId = likeBtn.dataset.reviewId;
    if (!reviewId) return;

    const res = await fetch(`/review/like/check?reviewId=${reviewId}`);
    if (!res.ok) return;

    const liked = await res.json();
    if (liked) likeBtn.classList.add('on');
}

const observer = new MutationObserver(() => {
    const likeBtns = document.querySelectorAll('.like-btn:not(.checked)');

    likeBtns.forEach(btn => {
        btn.classList.add('checked'); // 중복 방지
        checkLikeStatus(btn);
    });
});

document.addEventListener('DOMContentLoaded', () => {

    observer.observe(document.body, {
        childList: true,
        subtree: true
    });

    document.addEventListener('click', async (e) => {
        const likeBtn = e.target.closest('.like-btn');
        if (!likeBtn) return;

        e.preventDefault();
        e.stopPropagation();

        const reviewId = likeBtn.dataset.reviewId;
        const isCurrentlyLiked = likeBtn.classList.contains('on');

        try {
            const res = await fetch(`/review/like?reviewId=${reviewId}`, {
                method: isCurrentlyLiked ? 'DELETE' : 'POST',
                headers: {
                    [header]: token
                }
            });

            if (res.status === 401 || res.status === 403) {
                alert('로그인 후 이용해주세요.');
                return;
            }
            if (!res.ok) throw new Error();

            likeBtn.classList.toggle('on');

        } catch (err) {
            console.error(err);
            alert('오류 발생');
        }
    });
});
