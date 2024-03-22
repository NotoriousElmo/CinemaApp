function main() {
    let mainDiv = document.querySelector("#main");

    fetch('http://localhost:8080/api/tickets', {
        method: 'GET',
    })
    .then(response => response.json())
    .then(data => {
        let isHistoryPage = window.location.href.includes('history.html');
        let table = createTable(data, isHistoryPage);
        
        mainDiv.appendChild(table);        

    })
    .catch((error) => {
        console.error('Error:', error);
    });
}


function createTable(data, isHistoryPage) {
    let table = document.createElement('table');
    table.style.borderSpacing = "30px";

    let thead = document.createElement('thead');
    let headerRow = document.createElement('tr');
    let headers = ['Kuupäev ja Kellaaeg', 'Film', 'Ruum', 'Istekoht', 'Vanusepiirang', 'Keel', 'Pikkus minutites', 'Hind'];
    let dataHeaders = ['showing', 'movie', 'room', 'seat', 'age', 'language', 'length_minutes', 'price'];

    createTableHeaders(headers, headerRow);

    thead.appendChild(headerRow);
    table.appendChild(thead);

    let tbody = createTableBody(data, headers, dataHeaders, isHistoryPage);

    table.appendChild(tbody);
    return table;
}

function createTableBody(data, headers, dataHeaders, isHistoryPage) {
    let tbody = document.createElement('tbody');
    for (let i = 0; i < data.length; i++) {
        let showingDate = new Date(data[i]['showing']);
        let now = new Date();
        if ((isHistoryPage && showingDate < now) || (!isHistoryPage && showingDate >= now)) {
            let row = createRow(headers, dataHeaders, data, i, showingDate);
            tbody.appendChild(row);
        }
    }
    return tbody;
}




function createTableHeaders(headers, headerRow) {
    for (let header of headers) {
        let th = document.createElement('th');
        th.textContent = header;
        headerRow.appendChild(th);
    }
}

function createRow(headers, dataHeaders, data, i, showingDate) {
    let row = document.createElement('tr');
    for (let j = 0; j < headers.length; j++) {
        let td = createElement(dataHeaders, j, data, i, showingDate);
        row.appendChild(td);
    }
    return row;
}

function createElement(dataHeaders, j, data, i, showingDate) {
    let td = document.createElement('td');

    if (dataHeaders[j] === 'length_minutes') {
        td.textContent = data[i][dataHeaders[j]];
    }
    else if (dataHeaders[j] === 'showing') {
        formatDate(showingDate, td);
    } else {
        td.textContent = data[i][dataHeaders[j]];
    }
    return td;
}

function formatDate(showingDate, td) {
    let formattedDate = ("0" + showingDate.getDate()).slice(-2) + '/' + ("0" + (showingDate.getMonth() + 1)).slice(-2) + '/' + showingDate.getFullYear();
    let formattedTime = ("0" + showingDate.getHours()).slice(-2) + ':' + ("0" + showingDate.getMinutes()).slice(-2);
    td.textContent = formattedDate + ' ' + formattedTime;
}

// ==================== ENTRY POINT ===================
main();
