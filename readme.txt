AllFifeBooks - READ ME

1- High Level Design
The high level design of the application one based on the probability of growth.
Each user operation is dealt with as a single Command object.
Commands operate independently from each other, and implement each implement a set of standard methods,
this ensures that the application can be extended easily.

A new command can be added and providing it implements the same Command interface it can be added
to the main AllFifeBooks class simply by adding a new option to the if statement in the runApplication method.

A Command interacts with the user via a classed called QuestionAsker and objects called a UserPrompts,
each of these UserPrompts objects contain:
          - A message the user is going to be shown.
          - A value that will be set from the users input
          - A BookField which relates to the corresponding value on a Book object that the value will be stored against
          - A requiresValidation flag, if this is set to true, the AllFifeBooks class will validated that input against its command, and act accordingly
          - The Question Asker simply asks the user the question set in the UserPrompts and returns the users answer.

Once a user has answered each of the user prompts, the save method on each command is called.
All the save methods in this application call back to the BookController class.

BookController is the class which:
         - Interacts with the PersistenceHelper
         - Validates input
         - converts a collection of UserPrompts into a Book Object
         - Alters an existing Book object to change it into a Sold/Binned Book
         - It also generates the book report.

The PersistenceHelper is a class which:
         - Opens the flat file containing books and loads these into Book Objects
         - The BookObjects are then stored in a HashMap with the BookId as a key
         - The HashMap of books is then returned to the BookController
         - When the BookController is ready to save the PersistenceHelper either:
                     -- Adds a new Book to the flat file
                     -- Alters a Book already in the flat file

2- Unit Tests
        - A set of unit tests have been provided using jUnit these mostly test the BookController and PersistenceHelper classes.

3- Acceptance Tests
        - A set of Unit tests have been provided using junit and mockito.
            Each of these tests the high level user requirement given in the spec.

4- Expansion
The scenario in the spec sets out this is a task that should be done in a day. As a result of this I tried to complete the task in about 9 hours,
as a result I did not have enough time to fully complete everything. If I had more time I would have:
       - Expanded the validation to make input checking more robust
       - Expanded the test suites to cover more failure condition
       - Expanded the Acceptance tests to cover validation, i.e. entered wrong data, received a message then corrected it.
       - Replacing the database backend with a database would vastly improve the application.

5- How to Run
    - Download the master.zip file from http://gihhub.com/thePhan7om/AllFifeBooks
    - Extract the zip
    - From the root directory of the zip, run the following Maven command to run the application
                         mvn clean package
    - This will compile the application and run the unit and acceptance tests. It will also download the required supporting jars
    - Once compilation is successful run the following command to run the program
                         java -jar target/allfifebooks-1.0-SNAPSHOT.jar
    - A Menu will be shown offering a number of options, select an option and follow the commands on the screen.
            - The list of books is stored in 'inventory/allBooks.txt'
