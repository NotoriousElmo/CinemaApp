function removeAllChildNodes(parent) {
    while (parent.firstChild) {
        parent.removeChild(parent.firstChild);
    }
}

function purchaseTicket() {
    let showing = JSON.parse(localStorage.getItem('data'));
    const numberTickets = localStorage.getItem(showing['id']);
    let mainDiv = document.querySelector("#main");


    let instructions = document.createElement("p");
    instructions.innerText = "1) Valitud istekohad on kollased.\n2) Sa ei saa valida rohkem istekohtasid kui sa deklareerisid.\n3) Teiste istekohtade valimiseks vajutage kollaste istekohtade peale, et need siniseks muuta ja siis valige uued.";
    mainDiv.appendChild(instructions);

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
    });

    mainDiv.appendChild(backButton);

    
    let table = document.createElement('table');
    table.style.borderSpacing = "30px";

    fetch('http://localhost:8080/api/seats', {
        method: 'GET',
    })
    .then(response => response.json())
    .then(data => {

        let seats = data.map(element => ({
            seatId: element['id'],
            seatCode: element['seatCode'],
            room: element['room'],
            isTaken: Math.random() < 0.2,
        }));

        let tbody = document.createElement('tbody');
        let selectedSeats = recommendSeats(seats, numberTickets);

        let screen = document.createElement('tr');
        screen.innerText = '------------------------- Ekraan -------------------------\n\n'
        screen.style.textAlign = 'center';
        tbody.appendChild(screen);

        for (let index = 0; index < 5; index++) {            
            let row = document.createElement('tr');
        
            for (let i = index * 10; i < (index + 1) * 10; i++) {
                const element = seats[i];
                let seatButton = document.createElement('button');

                seatButton.style.borderStyle = 'solid';
                seatButton.style.borderWidth = '1px';
                seatButton.style.borderColor = 'blue';
                seatButton.style.marginTop = '5px';
                seatButton.style.width = '40px';
        
                if (element.isTaken) {
                    seatButton.style.backgroundColor = 'grey';
                    seatButton.innerText = '-';
                    seatButton.disabled = true;
                } else {
                    if (selectedSeats.includes(element)) {
                        seatButton.style.backgroundColor = 'yellow';
                    } else {                        
                        seatButton.style.backgroundColor = 'lightblue';
                    }
                    seatButton.innerText = element.seatCode;
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
                    });
                }
        
                row.appendChild(seatButton);
            }
        
            tbody.appendChild(row);
        }
        table.appendChild(tbody);
        mainDiv.appendChild(table);

        let purchaseButton = createPurchaseButton(selectedSeats, mainDiv, showing);

        mainDiv.appendChild(purchaseButton);
    })
    .catch((error) => {
        console.error('Error:', error);
    });
}

function recommendSeats(seats, numberOfSeatsNeeded) {

    const targetIndex = 25;

    for (let i = targetIndex; i > 15 + parseInt(numberOfSeatsNeeded); i--) {
        let group = seats.slice(i - parseInt(numberOfSeatsNeeded), i);
        if (group.every(seat => !seat.isTaken)) {

            return group;
        }
    }

    for (let i = targetIndex; i < 35 - parseInt(numberOfSeatsNeeded); i++) {
        let group = seats.slice(i, i + parseInt(numberOfSeatsNeeded));
        if (group.every(seat => !seat.isTaken)) {

            return group;
        }
    }

    for (let i = targetIndex; i > parseInt(numberOfSeatsNeeded); i--) {
        let group = seats.slice(i - parseInt(numberOfSeatsNeeded), i);
        if (group.every(seat => !seat.isTaken)) {

            return group;
        }
    }

    for (let i = 35 - parseInt(numberOfSeatsNeeded); i < 50; i++) {
        let group = seats.slice(i, i + parseInt(numberOfSeatsNeeded));
        if (group.every(seat => !seat.isTaken)) {

            return group;
        }
    }

}

function createPurchaseButton(selectedSeats, mainDiv, showing) {
    let purchaseButton = document.createElement('button');

    purchaseButton.innerText = "Edasi";
    purchaseButton.style.backgroundColor = 'lightblue';
    purchaseButton.style.borderStyle = 'solid';
    purchaseButton.style.borderWidth = '1px';
    purchaseButton.style.borderColor = "blue";
    purchaseButton.style.marginTop = '5px';

    purchaseButton.addEventListener('click', function () {
        if (selectedSeats.length >= 1) {
            removeAllChildNodes(mainDiv);

            endPurchaseScreen(selectedSeats, showing);
        } else {
            alert('Vähemalt üks istekoht peab olema valitud!');
        }
    });
    return purchaseButton;
}

function endPurchaseScreen(selectedSeats, showing) {

    const price = showing['price'];
    let movie = showing['movie'];
    
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

    let date = new Date(showing['start']);

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

    for (let i = 0; i < selectedSeats.length; i++) {
        let element = selectedSeats[i];
        let url = 'http://localhost:8080/api/tickets';
        let data = {price: price, 
            seatId: element['seatId'], 
            seat: element['seatCode'], 
            room: element['room'], 
            showingId: showing['id'], 
            showing: showing['start'], 
            movie: showing['movie'], 
            age: showing['age'], 
            language: showing['language'], 
            length_minutes: showing['length_minutes']};
        
        fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Connection': 'keep-alive',
            },
            body: JSON.stringify(data)
        })
        .then(response => response.json())
        .catch((error) => {
            console.error('Error:', error);
        });
    }

    window.location.href = '../index.html';
    })

    mainDiv.appendChild(purchaseButton);
}


// ==================== ENTRY POINT ===================
purchaseTicket();
