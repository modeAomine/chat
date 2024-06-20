function toggleSubCategories(collapseId) {
    const collapse = document.getElementById(collapseId);
    const subCategories = document.querySelectorAll('.sub-categories');
    for (var i = 0; i < subCategories.length; i++) {
        if (subCategories[i] !== collapse) {
            subCategories[i].style.display = 'none';
        }
    }
    collapse.style.display = collapse.style.display === 'block' ? 'none' : 'block';
    const expandIcon = document.querySelector('#' + collapseId).previousElementSibling.querySelector('.expand-icon');
    expandIcon.classList.toggle('rotate');
}