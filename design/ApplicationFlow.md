# Flow of the next_book App

## Before User interaction

Some set up is required to train the Machine Learning Application that I will be using, and in order to do so I need copious amounts of data. My solution is write a web-scrapping app that will access the GoodReads website in order to sift through and pull out data related to books such as author, ratings, and genres.

Part 1 - Training the machine
1. Write the web scrapping app using the GoodReads API to parse useful information
2. Write retreived information into a database, specifically one for training the machine used
3. Configure the machine
    * I honestly do not have solid footing on how this is going to work as it deals heavily with statisical models used - and I don't understand it completely. Though I do understand the general flow and have consulted several guides on the process.

4. Pump as much data into the machine for 'training'. Amazon's Machine Learning recommends a 70% time spent learning then 30% in use.



Part 2 - Generating the recommendation
1. The user will need to log in to the main application using some UI interface complete with text fields and login buttons.
2. After logging in, a new user will need to submit some data such as previous books read, preferred authors and genres, as well as basic contact information for record keeping.
3. Returning users and users finished with data submission will go to the home screen which will have three main areas. 
    *Recommend a book which will generate book recommendations
    *my history which displays books read on the app and serves to change preferences for the recommendation criteria
    *my information to change or update contact information.
4. Go to the various page selected and preform ascribed operations
    - Ex: Recommendation page will generate a recommendation complete with name, author, and a picture of the book
5. Enjoy the app

