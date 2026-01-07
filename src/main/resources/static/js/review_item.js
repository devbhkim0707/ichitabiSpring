document.addEventListener('click', (e) => {
  const btn = e.target.closest('.favo button');
  if (!btn) return;
  btn.classList.toggle('on');
});
