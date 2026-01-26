const likeBtn = document.getElementById('like-btn');
const likeCountEl = document.getElementById('like-count');
const festivalId = likeBtn.dataset.festivalId;

const token = document.querySelector("meta[name='_csrf']").getAttribute("content");
const header = document.querySelector("meta[name='_csrf_header']").getAttribute("content");

async function fetchLikeCount() {
    const res = await fetch(`/festival/like?festivalId=${festivalId}`);
    if (!res.ok) throw new Error();
    const count = await res.json();
    likeCountEl.innerText = count;
}

async function checkLikeStatus() {
    const res = await fetch(`/festival/like/check?festivalId=${festivalId}`);
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
        const res = await fetch(`/festival/like?festivalId=${festivalId}`, {
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