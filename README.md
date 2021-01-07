# ShowManager - Movie/Show Manager using omdb apis 

Developing an Android app to show a list of movies and tv shows to the user. The movie/show card contain the following details.
Name, Year, image.
On Click of the card user redirected to details page where app  show the following details and an option to return back to the main screen.
Name, Year, Genre, Director, image, IMDB rating, Actors.

API Detais can be found at: http://www.omdbapi.com/
Generate your own key from OMDB and mention it in https://github.com/anuj71307/ShowManager/edit/master/app/src/main/java/com/android/showmanager/utils/Constants.java file, for variable API_KEY
 
There is a search option in action bar for the user to search a movie/show.
Use the pagination feature of the api to get next results.

User can bookmark a movie. The bookmarked movie should be identified (may be a bookmark badge) in the home page list. Bookmarks are saved offline, so as whenever the app opens again, bookmarks are present. 
The bookmarked list will be a horizontal scrolling list at the top which will contain image and name.
The normal list will be vertical scrolling list which will contain the details given above. 

Design Pattern: MVP, 

Libarary for network call: Retrofit, 

Library for image download: Picasso
