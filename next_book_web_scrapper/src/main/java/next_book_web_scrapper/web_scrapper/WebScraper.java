package next_book_web_scrapper.web_scrapper;

import next_book_web_scrapper.entity.Book;
import next_book_web_scrapper.entity.Author;
import next_book_web_scrapper.database.AuthorDAO;
import next_book_web_scrapper.database.BookDAO;
import org.apache.log4j.Logger;
import org.hibernate.engine.jdbc.ReaderInputStream;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static java.lang.System.exit;

public class WebScraper {

    private List<Book> booksFromList;
    private List<Book> fullBooks;
    private GoodReadsHtmlList htmlDocument;
    private String targetURL;
    private Properties properties;
    private final Logger log = Logger.getLogger(this.getClass());
    private static final int VALID_COMMAND_LINE_ARGUMENTS = 1;
    private BookDAO bookDao;
    private AuthorDAO authorDAO;

    /**
     * No Argument constructor
     */
    public WebScraper() {
        booksFromList = new ArrayList<Book>();
        fullBooks = new ArrayList<Book>();
        bookDao = new BookDAO();
        authorDAO = new AuthorDAO();
    }

    private void loadProperties(String propertiesFileLocation) {
        properties = new Properties();

        try {
            properties.load(this.getClass().getResourceAsStream(propertiesFileLocation));
        } catch (IOException ioException) {
            log.error("IOException in loadProperties", ioException);
        } catch (Exception exception) {
            log.error("Exception in loadProperties", exception);
        }
    }



    // Create a class to scrape the html to get author and book title

    public void run(String[] arguments) {
        // Entry point to the program.
        if (arguments.length != VALID_COMMAND_LINE_ARGUMENTS) {
            log.info("Please put in the proper number of arguments into the run configuration\n");
            return;
        }

        System.out.println("Starting scrape");

        loadProperties(arguments[0]);
        htmlDocument = new GoodReadsHtmlList(properties.getProperty("list.target.url"),
                Integer.parseInt(properties.getProperty("list.number.pages")));
        htmlDocument.go();

        booksFromList = htmlDocument.getBooksWithAuthors();


        /*
        Book aBook = booksFromList.get(0);
        GoodReadsResponseBookTitle response = new GoodReadsResponseBookTitle(aBook,
                properties.getProperty("goodreads.api.key"),
                properties.getProperty("book.base.target"));
        response.fillInBook();

        Book finishedBook = response.getCurrentText();
        Author bookAuthor = response.getAuthorOfBook();
        addValuesToDatabase(finishedBook, bookAuthor);
        //visitAllBookPages();
        */

        if (booksFromList != null) {
            visitAllBookPages();

            System.out.println("Finished parsings");
            exit(0);
        } else {
            log.info("Book list is null from list web page");
            exit(-1);
        }
    }

    private void visitAllBookPages() {
        GoodReadsResponseBookTitle response = null;
        Book finishedBook = null;
        Author bookAuthor = null;
        for (Book currentBook: booksFromList) {
            response = new GoodReadsResponseBookTitle(currentBook,
                    properties.getProperty("goodreads.api.key"),
                    properties.getProperty("book.base.target"));
            if (response.fillInBook()) {
                finishedBook = response.getCurrentText();
                bookAuthor = response.getAuthorOfBook();
                addValuesToDatabase(finishedBook, bookAuthor);
            }
        }
    }

    private void addValuesToDatabase(Book book, Author author) {
        //Author must be added first
        int id = authorDAO.safeAddAuthor(author);
        if (id < 0) {
            log.error("=================Author was not added to the database due to error======================");
            log.error("Author name: " + author.getFirstName() + " " + author.getLastName());
        }


        if (id == 0) {
            id = authorDAO.findAuthorByClass(author);
            author.setId(id);
        } else {
            author.setId(id);
        }

        id = bookDao.safeAddBook(book);
        if (id < 0) {
            log.error("=======================Book was not added to database================================");
            log.error("Book title: " + book.getTitle());
        }

    }
}
