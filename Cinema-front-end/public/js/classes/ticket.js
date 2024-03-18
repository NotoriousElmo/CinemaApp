export default class ticket {

    id = null;
    price = null;
    seat = null;
    showing = null;
    showingName = null;
    seatCode = null;
    seatRoom = null;
    date = null;
    movie = null;

    constructor(id, price, seat, showing,) {
        this.id = id;
        this.price = price;
        this.seat = seat;
        this.showing = showing;
    }
}