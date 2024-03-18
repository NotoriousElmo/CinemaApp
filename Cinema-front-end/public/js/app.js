function main () {

    let mainDiv = document.querySelector("#main");

    fetch('http://localhost:8080/api/tickets', {
        method: 'GET',
    })
    .then(response => response.json())
    .then(data => {

        let table = document.createElement('table');
        table.style.borderSpacing = "30px"; // Add more space between cells

        let thead = document.createElement('thead');
        let headerRow = document.createElement('tr');
        let headers = ['Kuupäev / Kell', 'Film', 'Ruum', 'Istekoht', 'Vanusepiirang','Keel', 'Pikkus minutites', 'Hind'];
        let dataHeaders = ['showing', 'movie', 'room', 'seat', 'age', 'language', 'length_minutes', 'price'];
        
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
            for (let j = 0; j < headers.length; j++) {
                let td = document.createElement('td');
        
                if (dataHeaders[j] === 'length_minutes') {
                    td.textContent = data[i][dataHeaders[j]];
                }
                else if (dataHeaders[j] === 'showing') {
                    let date = new Date(data[i][dataHeaders[j]]);
                    td.textContent = date.toLocaleDateString() + ' / ' + date.toLocaleTimeString();
                } else {
                    td.textContent = data[i][dataHeaders[j]];
                }
                row.appendChild(td);
            }
            tbody.appendChild(row);
        }
        table.appendChild(tbody);
        
        mainDiv.appendChild(table);        

    })
    .catch((error) => {
        console.error('Error:', error);
    });

}

// ==================== ENTRY POINT ===================
console.log("App startup...");
main();
