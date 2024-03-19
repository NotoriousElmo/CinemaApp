function removeAllChildNodes(parent) {
    while (parent.firstChild) {
        parent.removeChild(parent.firstChild);
    }
}

function showResults(ageLimit, genres, date, language) {
    let mainDiv = document.querySelector("#main");


    let backButton = document.createElement("button");
    backButton.innerText = "Back";
    backButton.style.backgroundColor = 'lightblue';
    backButton.style.borderStyle = 'solid';
    backButton.style.borderWidth = '1px';
    backButton.style.borderColor = "blue"

    backButton.addEventListener('click', function() {
        removeAllChildNodes(mainDiv);
        main();
        console.log("back");
        this.style.backgroundColor = "yellow";
    });

    mainDiv.appendChild(backButton);


    fetch('http://localhost:8080/api/showings', {
        method: 'GET',
    })
    .then(response => response.json())
    .then(data => {

        
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


        let scrollableDiv = document.createElement('div');
        scrollableDiv.style.width = '800px'
        scrollableDiv.style.height = '500px';
        scrollableDiv.style.overflowY = 'auto';
        scrollableDiv.style.border = '1px solid #ccc';
    
        let table = document.createElement('table');
        table.style.borderSpacing = "30px";
    
        let thead = document.createElement('thead');
        let headerRow = document.createElement('tr');
        let headers = ['Kuupäev / Kell', 'Film', 'Ruum', 'Kino', 'Vanusepiirang', 'Keel', 'Pikkus minutites', 'Hind'];
        let dataHeaders = ['start', 'movie', 'room', 'cinema', 'age', 'language', 'length_minutes', 'price'];
    
        for (let header of headers) {
            let th = document.createElement('th');
            th.textContent = header;
            headerRow.appendChild(th);
        }
        thead.appendChild(headerRow);
        table.appendChild(thead);
    
        let tbody = document.createElement('tbody');
        for (let i = 0, len = data.length; i < len; i++) {
            let row = document.createElement('tr');
            for (let j = 0; j < dataHeaders.length; j++) {
                let td = document.createElement('td');
    
                if (dataHeaders[j] === 'start') {
                    let showingDate = new Date(data[i][dataHeaders[j]]);
                    td.textContent = showingDate.toLocaleDateString() + ' / ' + showingDate.toLocaleTimeString();
                } else {
                    td.textContent = data[i][dataHeaders[j]];
                }
                row.appendChild(td);
            }
            tbody.appendChild(row);
        }
        table.appendChild(tbody);
    
        scrollableDiv.appendChild(table);
    
        mainDiv.appendChild(scrollableDiv);
    })
    .catch((error) => {
        console.error('Error:', error);
    });
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
    ageLimitLabel.textContent = "Age limit: ";
    let ageLimitInput = document.createElement("input");
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
    dateTimeLabel.textContent = "Date and Time: ";
    let dateTimeInput = document.createElement("input");
    dateTimeInput.type = "datetime-local";
    dateTimeContainer.appendChild(dateTimeLabel);
    dateTimeContainer.appendChild(dateTimeInput);

    let languageContainer = createInputContainer();
    let languageLabel = document.createElement("label");
    languageLabel.textContent = "Language: ";
    let languageInput = document.createElement("input");
    languageInput.type = "text";
    languageInput.placeholder = "Language";
    languageContainer.appendChild(languageLabel);
    languageContainer.appendChild(languageInput);


    let buttonContainer = createInputContainer();
    buttonContainer.style.textAlign = 'right';
    let recommendButton = document.createElement("button");
    recommendButton.innerText = "Filter";
    recommendButton.style.backgroundColor = 'lightblue';
    recommendButton.style.borderStyle = 'solid';
    recommendButton.style.borderWidth = '1px';
    recommendButton.style.borderColor = "blue";
    recommendButton.style.marginTop = '20px';
    recommendButton.addEventListener('click', function() {

        let ageLimit = ageLimitInput.value;
        let genres = Array.from(document.querySelectorAll('input[type="checkbox"]:checked')).map(cb => cb.value);
        let date = dateTimeInput.value;
        let language = languageInput.value;

        removeAllChildNodes(mainDiv);
        showResults(ageLimit, genres, date, language);
        console.log("recommend");
    });
    buttonContainer.appendChild(recommendButton);

    mainDiv.appendChild(ageLimitContainer);
    mainDiv.appendChild(genreContainer);
    mainDiv.appendChild(dateTimeContainer);
    mainDiv.appendChild(languageContainer);
    mainDiv.appendChild(buttonContainer);
}

// ==================== ENTRY POINT ===================
console.log("App startup...");
main();
