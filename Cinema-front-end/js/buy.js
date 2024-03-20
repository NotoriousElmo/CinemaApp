function removeAllChildNodes(parent) {
    while (parent.firstChild) {
        parent.removeChild(parent.firstChild);
    }
}

function purchaseTicket() {

    let mainDiv = document.querySelector("#main");


    let backButton = document.createElement("button");
    backButton.innerText = "Tagasi";
    backButton.style.backgroundColor = 'lightblue';
    backButton.style.borderStyle = 'solid';
    backButton.style.borderWidth = '1px';
    backButton.style.borderColor = "blue"

    backButton.addEventListener('click', function() {
        removeAllChildNodes(mainDiv);
        localStorage.clear();
        window.location.href = 'movies.html';
        console.log("back");
    });

    mainDiv.appendChild(backButton);
    let table = document.createElement('table');
    table.style.borderSpacing = "30px";


    let showing = JSON.parse(localStorage.getItem('data'));
    const numberTickets = localStorage.getItem(showing['id']);
    const ticketPrice = showing['price'];

    fetch('http://localhost:8080/api/seats', {
        method: 'GET',
    })
    .then(response => response.json())
    .then(data => {

        let tbody = document.createElement('tbody');
        let selectedSeats = [];
        let screen = document.createElement('tr');
        screen.innerText = '------------------------- Ekraan -------------------------\n\n'
        screen.style.textAlign = 'center';
        tbody.appendChild(screen);

        for (let index = 0; index < 5; index++) {            
            let row = document.createElement('tr');
        
            for (let i = index * 10; i < (index + 1) * 10; i++) {
                const element = data[i];
                let seatButton = document.createElement('button');
                seatButton.style.backgroundColor = Math.random() < 0.2 ? 'grey' : 'lightblue';
                seatButton.style.borderStyle = 'solid';
                seatButton.style.borderWidth = '1px';
                seatButton.style.borderColor = 'blue';
                seatButton.style.marginTop = '5px';
                seatButton.style.width = '40px';
        
                if (seatButton.style.backgroundColor === 'grey') {
                    seatButton.innerText = '-';
                    seatButton.disabled = true;
                } else {
                    seatButton.innerText = element['seatCode'];
                    seatButton.addEventListener('click', function() {
                        if (this.style.backgroundColor === 'yellow') {
                            this.style.backgroundColor = 'lightblue';
                            const seatIndex = selectedSeats.indexOf(element);
                            if (seatIndex > -1) {
                                selectedSeats.splice(seatIndex, 1);
                            }
                        } else {
                            if (selectedSeats.length < numberTickets) {
                                this.style.backgroundColor = 'yellow';
                                selectedSeats.push(element);
                            }
                        }
                        console.log(selectedSeats.length);
                        console.log(numberTickets);
                    });
                }
        
                row.appendChild(seatButton);
            }
        
            tbody.appendChild(row);
        }

        table.appendChild(tbody);
        mainDiv.appendChild(table);

        let purchaseButton = document.createElement('button');
        purchaseButton.innerText = "Edasi";
        purchaseButton.style.backgroundColor = 'lightblue';
        purchaseButton.style.borderStyle = 'solid';
        purchaseButton.style.borderWidth = '1px';
        purchaseButton.style.borderColor = "blue";
        purchaseButton.style.marginTop = '5px';
        purchaseButton.addEventListener('click', function() {
            if (selectedSeats.length >= 1) {
                console.log("Edasi");
                removeAllChildNodes(mainDiv);

                endPurchaseScreen(selectedSeats, ticketPrice, showing['movie'], showing['start'], showing['id']);
            } else {
                alert('Vähemalt üks istekoht peab olema valitud!');
            }
        });

        mainDiv.appendChild(purchaseButton);
    })
    .catch((error) => {
        console.error('Error:', error);
    });

}

function endPurchaseScreen(selectedSeats, price, movie, originalDate, showingId) {
    
    let mainDiv = document.querySelector("#main");


    let backButton = document.createElement("button");
    backButton.innerText = "Tagasi";
    backButton.style.backgroundColor = 'lightblue';
    backButton.style.borderStyle = 'solid';
    backButton.style.borderWidth = '1px';
    backButton.style.borderColor = "blue"

    backButton.addEventListener('click', function() {
        removeAllChildNodes(mainDiv);
        purchaseTicket();
        console.log("back");
    });

    mainDiv.appendChild(backButton);

    let table = document.createElement('table');
    table.style.borderSpacing = "30px";
    table.style.fontSize = '20px';

    let headers = ['Kuupäev', 'Film', 'Istekoht', 'Hind'];

    let headerRow = document.createElement('tr');
    let thead = document.createElement('thead');

    for (let header of headers) {
        let th = document.createElement('th');
        th.textContent = header;
        headerRow.appendChild(th);
    }

    thead.appendChild(headerRow);
    table.appendChild(thead);

    let date = new Date(originalDate);

    let formattedDate = ("0" + date.getDate()).slice(-2) + '/' + ("0" + (date.getMonth() + 1)).slice(-2) + '/' + date.getFullYear();

    let formattedTime = ("0" + date.getHours()).slice(-2) + ':' + ("0" + date.getMinutes()).slice(-2);
    
    selectedSeats.forEach(element => {
        let row = document.createElement('tr');

        let d = document.createElement('td');
        d.innerText = formattedDate + ' ' + formattedTime;
        row.appendChild(d);

        let m = document.createElement('td');
        m.innerText = '"' + movie + '"';
        row.appendChild(m);

        let s = document.createElement('td');
        s.innerText = element['seatCode'];
        row.appendChild(s);

        let p = document.createElement('td');
        p.innerText = price + '€';
        row.appendChild(p);
        table.appendChild(row);
    });

    mainDiv.appendChild(table);

    let recieptRow = document.createElement('p');
    recieptRow.innerText = 'Summa kokku: ' + price * selectedSeats.length + '€';
    recieptRow.style.fontSize = '20px';
    recieptRow.style.fontWeight = 'bold';

    mainDiv.appendChild(recieptRow);
    
    let purchaseButton = document.createElement("button");
    purchaseButton.innerText = "Osta";
    purchaseButton.style.fontSize = '20px';

    purchaseButton.addEventListener('click', function() {
        console.log('Ostetud');
        console.log(selectedSeats[0]);

    for (let i = 0; i < selectedSeats.length; i++) {
        let url = 'http://localhost:8080/api/tickets';
        console.log(selectedSeats[i]['id']);
        console.log(showingId);
        console.log(price);
        let data = {seat: selectedSeats[i]['id'], showing: showingId, price: price};
        
        fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Connection': 'keep-alive'
            },
            body: JSON.stringify(data)
        })
        .then(response => response.json())
        .then(data => console.log(data))
        .catch((error) => {
            console.log(response);
            console.log(data);
            console.error('Error:', error);
        });
    }

    window.location.href = '../index.html';
    })

    mainDiv.appendChild(purchaseButton);
}


// ==================== ENTRY POINT ===================
console.log("Purchasing ticket...");
purchaseTicket();
