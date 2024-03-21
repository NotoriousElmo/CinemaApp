function removeAllChildNodes(parent) {
    while (parent.firstChild) {
        parent.removeChild(parent.firstChild);
    }
}

function showResults(ageLimit, genres, date, language) {
    let mainDiv = document.querySelector("#main");
    let backButton = document.createElement("button");
    let scrollableDiv = document.createElement('div');

    scrollableDiv.style.width = '900px'
    scrollableDiv.style.height = '500px';
    scrollableDiv.style.overflowY = 'auto';
    scrollableDiv.style.border = '1px solid #ccc';

    backButton.innerText = "Tagasi";
    backButton.style.backgroundColor = 'lightblue';
    backButton.style.borderStyle = 'solid';
    backButton.style.borderWidth = '1px';
    backButton.style.borderColor = "blue"

    backButton.addEventListener('click', function() {
        removeAllChildNodes(mainDiv);
        main();
    });

    mainDiv.appendChild(backButton);


    fetch('http://localhost:8080/api/showings', {
        method: 'GET',
    })
    .then(response => response.json())
    .then(data => {

        data = filterData(ageLimit, data, language, date, genres);
    
        createTable(data, scrollableDiv, mainDiv);
    })
    .catch((error) => {
        console.error('Error:', error);
    });
}

function createTable(data, scrollableDiv, mainDiv) {

    let table = document.createElement('table');
    let thead = document.createElement('thead');
    let headerRow = document.createElement('tr');
    let tbody = document.createElement('tbody');

    let headers = ['Kuupäev ja Kellaaeg', 'Film', 'Ruum', 'Kino', 'Vanus', 'Keel', 'Pikkus minutites', 'Hind', 'Osta Pilet'];
    let dataHeaders = ['start', 'movie', 'room', 'cinema', 'age', 'language', 'length_minutes', 'price'];

    table.style.borderSpacing = "30px";

    for (let header of headers) {
        let th = document.createElement('th');
        th.textContent = header;
        headerRow.appendChild(th);
    }
    thead.appendChild(headerRow);
    table.appendChild(thead);

    for (let i = 0, len = data.length; i < len; i++) {
        let row = document.createElement('tr');
        for (let j = 0; j < dataHeaders.length; j++) {
            let td = document.createElement('td');

            if (dataHeaders[j] === 'start') {
                let showingDate = new Date(data[i][dataHeaders[j]]);

                let formattedDate = ("0" + showingDate.getDate()).slice(-2) + '/' + ("0" + (showingDate.getMonth() + 1)).slice(-2) + '/' + showingDate.getFullYear();
                let formattedTime = ("0" + showingDate.getHours()).slice(-2) + ':' + ("0" + showingDate.getMinutes()).slice(-2);

                td.textContent = formattedDate + ' ' + formattedTime;
            } else {
                td.textContent = data[i][dataHeaders[j]];
            }
            row.appendChild(td);
        }

        let input = document.createElement('input');
        let purchaseButton = document.createElement('button');

        input.type = 'number';
        input.min = 1;
        input.max = 5;
        input.value = 1;
        input.id = 'ticketCount' + i;

        localStorage.setItem(data[i]['id'], input.value);

        input.addEventListener('change', function() {
            localStorage.setItem(data[i]['id'], input.value);
        });

        purchaseButton.innerText = "Osta";
        purchaseButton.style.backgroundColor = 'lightblue';
        purchaseButton.style.borderStyle = 'solid';
        purchaseButton.style.borderWidth = '1px';
        purchaseButton.style.borderColor = "blue";
        purchaseButton.style.marginTop = '5px';
        purchaseButton.addEventListener('click', function() {
            removeAllChildNodes(mainDiv);
            localStorage.setItem('data', JSON.stringify(data[i]));
            window.location.href = 'buy.html';
        });

        row.appendChild(input);
        row.appendChild(purchaseButton);
        tbody.appendChild(row);
    }

    table.appendChild(tbody);
    scrollableDiv.appendChild(table);
    mainDiv.appendChild(scrollableDiv);
}

function filterData(ageLimit, data, language, date, genres) {

    if (ageLimit) {
        data = data.filter(showing => showing.age === ageLimit);
    }

    if (language) {
        data = data.filter(showing => showing.language.toLowerCase() === language.toLowerCase());
    }

    if (date) {
        const selectedDate = new Date(date);
        data = data.filter(showing => {
            const showingDate = new Date(showing.start);
            return showingDate >= selectedDate;
        }).sort((a, b) => {
            const dateA = new Date(a.start), dateB = new Date(b.start);
            return dateA - dateB;
        });
    }

    if (genres.length > 0) {
        const lowerCaseGenres = genres.map(genre => genre.toLowerCase());

        data = data.filter(showing => {
            const showingGenres = showing.genres.map(genre => genre.toLowerCase());
            return lowerCaseGenres.every(genre => showingGenres.includes(genre));
        });
    }
    return data;
}

function main() {
    let mainDiv = document.querySelector("#main");

    
    function createInputContainer() {
        let container = document.createElement('div');
        container.style.marginBottom = '10px';
        return container;
    }

    let ageLimitContainer = createInputContainer();
    let ageLimitLabel = document.createElement("label");
    let ageLimitInput = document.createElement("input");

    ageLimitLabel.textContent = "Vanusepiirang: ";
    ageLimitInput.type = "text";
    ageLimitContainer.appendChild(ageLimitLabel);
    ageLimitContainer.appendChild(ageLimitInput);

    let genreContainer = createInputContainer();
    let genres = ["Action", "Adventure", "Thriller", "Drama", "Comedy", "Sci-Fi", "Romance"];
    genres.forEach(genre => {
        let label = document.createElement("label");
        let checkbox = document.createElement("input");
        checkbox.type = "checkbox";
        checkbox.value = genre;
        label.appendChild(checkbox);
        label.append(` ${genre}`);
        genreContainer.appendChild(label);
        genreContainer.appendChild(document.createElement("br"));
    });

    let dateTimeContainer = createInputContainer();
    let dateTimeLabel = document.createElement("label");
    dateTimeLabel.textContent = "Kuupäev ja kellaaeg: ";
    let dateTimeInput = document.createElement("input");
    dateTimeInput.type = "datetime-local";
    dateTimeContainer.appendChild(dateTimeLabel);
    dateTimeContainer.appendChild(dateTimeInput);

    let languageContainer = createInputContainer();
    let languageLabel = document.createElement("label");
    languageLabel.textContent = "Keel: ";
    let languageInput = document.createElement("input");
    languageInput.type = "text";
    languageInput.placeholder = "Language";
    languageContainer.appendChild(languageLabel);
    languageContainer.appendChild(languageInput);


    let buttonContainer = createInputContainer();
    buttonContainer.style.textAlign = 'right';
    let searchButton = document.createElement("button");
    searchButton.innerText = "Otsi";
    searchButton.style.backgroundColor = 'lightblue';
    searchButton.style.borderStyle = 'solid';
    searchButton.style.borderWidth = '1px';
    searchButton.style.borderColor = "blue";
    searchButton.style.marginTop = '20px';
    searchButton.addEventListener('click', function() {

        let ageLimit = ageLimitInput.value;
        let genres = Array.from(document.querySelectorAll('input[type="checkbox"]:checked')).map(cb => cb.value);
        let date = dateTimeInput.value;
        let language = languageInput.value;

        removeAllChildNodes(mainDiv);
        showResults(ageLimit, genres, date, language); // Search for given movie
    });


    let recommendButton = document.createElement("button");
    recommendButton.innerText = "Soovita vaatamiste põhja";
    recommendButton.style.backgroundColor = 'orange';
    recommendButton.style.borderStyle = 'solid';
    recommendButton.style.borderWidth = '1px';
    recommendButton.style.borderColor = "blue";
    recommendButton.style.marginTop = '20px';
    recommendButton.addEventListener('click', function() {

        findRecommendations(); // Search based on history

    });

    buttonContainer.appendChild(searchButton);
    buttonContainer.appendChild(recommendButton);

    mainDiv.appendChild(ageLimitContainer);
    mainDiv.appendChild(genreContainer);
    mainDiv.appendChild(dateTimeContainer);
    mainDiv.appendChild(languageContainer);
    mainDiv.appendChild(buttonContainer);
}

function findRecommendations() {

    let genreFrequencyMap = new Map();
    let mainDiv = document.querySelector("#main");

    removeAllChildNodes(mainDiv);

    let backButton = document.createElement("button");
    let scrollableDiv = document.createElement('div');

    scrollableDiv.style.width = '900px'
    scrollableDiv.style.height = '500px';
    scrollableDiv.style.overflowY = 'auto';
    scrollableDiv.style.border = '1px solid #ccc';

    backButton.innerText = "Tagasi";
    backButton.style.backgroundColor = 'lightblue';
    backButton.style.borderStyle = 'solid';
    backButton.style.borderWidth = '1px';
    backButton.style.borderColor = "blue"

    backButton.addEventListener('click', function() {
        removeAllChildNodes(mainDiv);
        main();
    });

    mainDiv.appendChild(backButton);

    fetch('http://localhost:8080/api/tickets', {
        method: 'GET',
    })
    .then(response => response.json())
    .then(data => {
        let promises = data.map(ticket => {
            return fetch('http://localhost:8080/api/movies/genre/' + ticket['movie'], {
                method: 'GET',
            })
            .then(response => response.json())
            .then(genres => {
                for (let genre of genres) {
                    let count = genreFrequencyMap.get(genre.name) || 0;
                    genreFrequencyMap.set(genre.name, count + 1);
                }
            });
        });

        Promise.all(promises)
        .then(() => {
            let genreFrequencies = Array.from(genreFrequencyMap.entries());

            genreFrequencies.sort((a, b) => b[1] - a[1]);

            fetch('http://localhost:8080/api/showings', {
                method: 'GET',
            })
            .then(response => response.json())
            .then(data => {

                let genres = [];

                if (genreFrequencies.length >= 2) {
                    genres = [genreFrequencies[0][0], genreFrequencies[1][0]];
                } else if (genreFrequencies.length === 1) {
                    genres = [genreFrequencies[0][0]];
                } else {
                    alert("Teil ei ole eelnevaid vaatmisi.")
                    return showResults("", [], "", "");
                }
    
                const lowerCaseGenres = genres.map(genre => genre.toLowerCase());

                data = data.filter(showing => {
                    const showingGenres = showing.genres.map(genre => genre.toLowerCase());
                    return lowerCaseGenres.some(genre => showingGenres.includes(genre));
                });                
            
                createTable(data, scrollableDiv, mainDiv);
            })
            .catch((error) => {
                console.error('Error:', error);
            });
        });
    })
    .catch((error) => {
        console.error('Error:', error);
    });
}


// ==================== ENTRY POINT ===================
main();
