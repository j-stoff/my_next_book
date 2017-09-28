package next_book_web_scrapper.web_scrapper;

import next_book_web_scrapper.entity.Book;
import org.apache.log4j.Logger;
import org.hibernate.engine.jdbc.ReaderInputStream;

import javax.sound.midi.SysexMessage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

public class WebScraper {

    private ArrayList<Book> booksFromList;
    private ArrayList<Book> fullBooks;
    private GoodReadsHtmlList htmlDocument;
    private String targetURL;
    private Properties properties;
    private final Logger log = Logger.getLogger(this.getClass());
    private static final int VALID_COMMAND_LINE_ARGUMENTS = 1;

    /**
     * No Argument constructor
     */
    public WebScraper() {
        booksFromList = new ArrayList<Book>();
        fullBooks = new ArrayList<Book>();
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


        loadProperties(arguments[0]);
        htmlDocument = new GoodReadsHtmlList(properties.getProperty("list.target.url"));

    }

    // Send the
}
