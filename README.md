Cinema Web App

In order to start the application a few things need to be set up first.

BACK-END
* Open back-end in your chosen environment (developed in Intellij IDEA 2022.3.2), open docker desktop. Create docker container by running the file docker-compose.yml or using command `docker-compose up`.
* Connect to the database at `port 5433`, username: `postgres`, password: `secret`. Connect to the database.
* Go to `src/main/resources/db.migrations` and run the migrations as they are ordered.
* Now go to `src/main/java/com.app.cinema/Config/SQLqueries` and populate the database with example data.
* Run CinemaApplication.
* For the purpose of development and ease I have currently enabled all CrossOrigin requests in the back-end API so that shouldn't hopefully be a problem.

FRONT-END
* Open front-end in your chosen environment (developed in VScode) and use command `http-server -c-1`.
* Open the created application in one of the given links.
* The application should open fine.

APPLICATION
* The application has 3 main windows "PILETID", "FILMID", "AJALUGU".
* PILETID - shows all of your upcoming movies that you have purchased tickets for.
* FILMID - enables you to search for your movie based on age limit, genre, date of showing or language.
    * Also enables you to ask for recommendation using the button "Soovita vaatamiste põhjal". If you currently have no tickets than it will just show you the default all results.
    * After searching or asking for recommended movies you are greeted by all the results that were found based on your inputs or already purchased tickets.
    * Int the table you will see the details of all the showings that are upcoming in the cinema and you can buy 1 or up to 5 tickets buy pressing the button "Osta".
    * After pressing the button "Osta" you will be taken to the screen where you can see the automatically recommended seat, which you can change, based on the instructions on the page.
      * Grey marks taken seats, which are automatically designated.
      * Lightblue marks free seats.
      * Yellow marks selected seats.
      * You can't select more seats than you previously selected.
      * If the seats are okay, you can press the button "Edasi", which will take you to the reciept page where you can finalize your order.
        * After pressing "Osta", the data is sent through a POST request to the back-end API, which will save this ticket to the database.
        * Congratulations you have purchased a ticket and have now been redirected to the front page, where you can see all of your tickets.
* AJALUGU - Shows the movies you have been to in the past.

USED TUTORIALS
* https://www.youtube.com/watch?v=9SGDpanrc8U
* https://www.youtube.com/watch?v=8SGI_XS5OPw
* https://www.youtube.com/watch?v=CJjHdchLY9Y&t=1952s
* https://www.youtube.com/watch?v=E7_a-kB46LU
* https://www.youtube.com/watch?v=LSEYdU8Dp9Y
* https://stackoverflow.com/questions/41140789/cors-enabled-but-still-getting-cors-error

PROBLEMS WITH THE APPLICATION
* No user login.
* No integration with movie sites.
* Data doesn't come with and fill the text boxes when going back.
* Randomly occupying seats instead of taking the seats that have been reserved by ticket purchase.
* When purchasing a ticket that ticket doesn't occupy the purchased seat when going back to the showing and trying to purchase another ticket. Can trigger an error in the back-end with buying the same seat at the same showing twice.
* Automatically assigning seats is stricly intertwined with the current cinema layout.
* Problems with the database logic.
* When asking for recommendations the application sends separate request to the database for each movie to find out the genres of that movie, leading to heavy load on the databasea and slowing the application.
* Too much logic in the front-end.
* Messy and rushed code design.
