CINEMA WEB APP

In order to start the application a few things need to be set up first.

BACK-END (Java 21.0.2)
* Back-end is developed with Spring Boot.
* Back-end has controllers, repositories, and services for all the entities in database.
* Open back-end in your chosen environment (developed in Intellij IDEA 2022.3.2), open docker desktop. Create docker container by running the file docker-compose.yml or using command `docker-compose up`.
* Connect to the database at `port 5433`, username: `postgres`, password: `secret`. Connect to the database.
* Go to `src/main/resources/db.migrations` and run the migrations as they are ordered.
* Now go to `src/main/java/com.app.cinema/Config/SQLqueries` and populate the database with example data.
* Run CinemaApplication.
* For the purpose of development and ease I have currently enabled all CrossOrigin requests in the back-end API so that shouldn't hopefully be a problem.

FRONT-END
* Front end is created almost entirely by Javascript which constructs the pages.
* All of the data is transferred from API by GET requests and the data that is being inputted is transferred via POST request to the back-end.
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

DEVELOPMENT PROCESS
* I started planning and preparing for this project in the beginning of March.
* I learned the basics of Spring Boot using mostly Youtube tutorials and looking up different topics in google.
* Then I created the initial database schema.
* Programming of the app began at 15th of March.
* In the beginning most of the time went to setting up docker and the database.
* In total the whole development process took probably around 30 hours + the time it took to learn.
* Main difficulties I faced was fixing different bugs and errors that occured, initially it was setting up the database and later it was fetching data from and to the back-end API.

USED TUTORIALS
* https://www.youtube.com/watch?v=9SGDpanrc8U
* https://www.youtube.com/watch?v=8SGI_XS5OPw
* https://www.youtube.com/watch?v=CJjHdchLY9Y&t=1952s
* https://www.youtube.com/watch?v=E7_a-kB46LU
* https://www.youtube.com/watch?v=LSEYdU8Dp9Y

PROBLEMS WITH THE APPLICATION
* No user login. (Can potentially build upon the Spring Boot users, roles and repositories logic. Need to redo the database)
* No integration with movie sites. (Can use the IMDB API. Currently don't have time to figure that out)
* Data doesn't come with and fill the text boxes when going back. (Could add logic that would send the data back between pages and fill the boxes with the last enterd data. Need to rethink the current page design)
* Randomly occupying seats instead of taking the seats that have been reserved by ticket purchase. (Would need to occupy the seats when the showing is being created and change the seat entity to potentially have another field called occupied(1, 0), this although assumes I would need some kind of user logic as well)
* When purchasing a ticket that ticket doesn't occupy the purchased seat when going back to the showing and trying to purchase another ticket. Can trigger an error in the back-end with buying the same seat at the same showing twice. (Could add a check to the logic. So that when the seats are being drawn it checks if the seat has a ticket assigned to it in that showing)
* Automatically assigning seats is stricly intertwined with the current cinema layout. (Need to rethink the logic behind the automatic seat recommendation)
* Problems with the database logic. (Need to redo the database)
* When asking for recommendations the application sends separate request to the database for each movie to find out the genres of that movie, leading to heavy load on the databasea and slowing the application. (Could add similar query logic that I have in ShowingDataAccessService to the MovieDataAccessService that would return a array of genres for each movie with something like `array_agg(g.name) as genres`)
* Too much logic in the front-end. (Need to refactor the application and move the logic to the back-end and create more API endpoints)
* Messy and rushed code design. (Sadly, currently just don't have the time. I have already fallen a little behind in school, because of the time I invested in this project)