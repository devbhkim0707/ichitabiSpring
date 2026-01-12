const token = document.querySelector("meta[name='_csrf']").getAttribute("content");
const header = document.querySelector("meta[name='_csrf_header']").getAttribute("content");

document.addEventListener('DOMContentLoaded', () => {
    const likeBtns = document.querySelectorAll('.like-btn');

    likeBtns.forEach(likeBtn => {
        const reviewId = likeBtn.dataset.reviewId;

        checkLikeStatus(likeBtn, reviewId);

        likeBtn.addEventListener('click', async (e) => {

            e.preventDefault();
            e.stopPropagation();

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

            } catch (e) {
                console.error(e);
                alert('오류 발생');
            }
        });

    });

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
            if (index < visibleCount) {
                item.style.display = '';
            }
        });

        if (visibleCount >= items.length) {
            document.getElementById('more-btn').style.display = 'none';
        }
    });
});


async function checkLikeStatus(likeBtn, reviewId) {
    const res = await fetch(`/review/like/check?reviewId=${reviewId}`);
    if (!res.ok) return;

    const liked = await res.json();
    if (liked) {
        likeBtn.classList.add('on');
    }
}
