document.addEventListener('DOMContentLoaded', function() {
    let searchInput = document.getElementById('searchInput');
    let searchResultsContainer = document.getElementById('searchResultsContainer');
    let searchButton = document.querySelector('.btn-outline-light');

    searchInput.addEventListener('input', function() {
        let query = searchInput.value;
        if (query.length > 0) {
            fetch('/search?query=' + encodeURIComponent(query))
                .then(response => response.json())
                .then(results => {
                    searchResultsContainer.innerHTML = '';
                    results.forEach(result => {
                        let resultElement = document.createElement('a');
                        resultElement.classList.add('dropdown-item');
                        resultElement.textContent = result.name;
                        resultElement.href = result.url;
                        resultElement.addEventListener('click', function(event) {
                            event.preventDefault();
                            window.location.href = result.url;
                        });
                        searchResultsContainer.appendChild(resultElement);
                    });
                })
                .catch(error => {
                    console.error('Ошибка при получении результатов поиска:', error);
                });
        } else {
            searchResultsContainer.innerHTML = '';
        }
    });

    // Обработка нажатия на кнопку "Поиск"
    searchButton.addEventListener('click', function(event) {
        event.preventDefault();
        let query = searchInput.value;
        if (query.length > 0) {
            fetch('/search?query=' + encodeURIComponent(query))
                .then(response => response.json())
                .then(results => {
                    if (results.length > 0) {
                        window.location.href = results[0].url;
                    } else {
                        alert('Результатов не найдено');
                    }
                })
                .catch(error => {
                    console.error('Ошибка при получении результатов поиска:', error);
                });
        }
    });
});

document.addEventListener('DOMContentLoaded', function() {
    let searchInput = document.getElementById('searchInput');
    let searchResultsContainer = document.getElementById('searchResultsContainer');

    searchInput.addEventListener('input', function() {
        let query = searchInput.value;
        if (query.length === 0) {
            // Если поле поиска пустое, скрываем контейнер с результатами
            searchResultsContainer.style.display = 'none';
        } else {
            // Если есть запрос, показываем контейнер с результатами
            searchResultsContainer.style.display = 'block';
        }
    });
});