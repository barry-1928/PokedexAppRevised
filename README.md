# PokedexAppRevised

The following are the general information and guidelines regarding the "Pokedex" app.

1. The Pokedex App is an unofficial, free app which lets you search details for any Pokemon you desire, alongwith an image of the Pokemon.
2. The app first opens with a splash screen for 2 seconds.
3. The exhaustive list of the name of the Pokemons can be found by clicking on the "List" menu item on the Action Bar of this page.
4. The details of the app has been given in the "App Details" menu item of the Action Bar.
5. The app provides an edittext in which you may type the name of a Pokemon and click on the Search button to view the details and an image of the Pokemon.
6. The app uses an API "https://pokeapi.co/api/v2/pokemon/Pokemon-name" to retrieve the information for the Pokemon.
7. The app uses "http://assets.pokemon.com/assets/cms2/img/pokedex/full/Pokemon-id" for getting the image of the Pokemon. The image is loaded through the "Picasso" image-caching library in the activity. The Pokemon-id is extracted from the API mentioned in point no 6.
8. The app contains a floating button at the bottom right end of the screen. Clicking on it redirects you to another page which contains your recent search history.
9. The recent search history page is implemented using a "Recycler View". This list contains the list of pokemons along with their images, which are loaded from the sprite url that is extracted from the API in point no 6 using the "Picasso" image-caching library.
10. Individual items on the page can be deleted by long-clicking on the item that you desire to remove.
11. A "Delete" option is provided as the first icon on the action bar, which deletes all the items in the Search History at a time.
12. A "Help" option is also provided as the second icon on the action bar, which gives you details regarding the Search History page.
13. I have used an SQLite database to store the list of the recently searched Pokemons names and their sprite image URLs.
14. I have made use AsyncTask to do the background thread operations of connecting the client(your mobile device) to the server(providing the API).  


Hope you enjoy the app.

Thank You. 

Developed by Rajat Bhatta. 

106116066
