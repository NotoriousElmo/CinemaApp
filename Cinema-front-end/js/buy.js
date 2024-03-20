function purchaseTicket() {
    console.log(JSON.parse(localStorage.getItem('data')));
}


// ==================== ENTRY POINT ===================
console.log("Purchasing ticket...");
purchaseTicket();
