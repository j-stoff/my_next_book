package next_book_web_scrapper.web_scrapper;

import next_book_web_scrapper.entity.Book;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.jsoup.nodes.Element;
import org.apache.log4j.Logger;
import org.jsoup.select.Elements;

import javax.el.ELException;
import javax.sound.midi.SysexMessage;
import java.io.IOException;
import java.net.URL;

/**
 * This class is designed to parse a response from a GoodReads API call
 * and fill a book object based on that response.
 */
public class GoodReadsResponseBookTitle {

    private Book currentText;
    private String developerKey;
    private String targetURL;
    private Document response;
    private final Logger log = Logger.getLogger(this.getClass());

    /**
     * No argument constructor
     */
    public GoodReadsResponseBookTitle() {
    }

    /**
     * Constructor to take in a Book object to complete
     * @param partialBook the book to be queried and completed.
     */
    public GoodReadsResponseBookTitle(Book partialBook, String apiKey,
        String baseURL) {
        this();
        currentText = partialBook;
        developerKey = apiKey;
        targetURL = baseURL;
    }


    /**
     * To generate a url based on the book title and author name
     * @return Fully qualified URL to connect to.
     */
    private String generatePageURL() {
        String url = targetURL;
        String authorName = currentText.getAuthorName().replace(' ',
                '+');
        String title = currentText.getTitle();
        int parenthesis = title.indexOf("(");
        if (parenthesis >= 0) {
            if (title.charAt(parenthesis - 1) == ' ') {
                title = title.substring(0, parenthesis - 1);
            } else {
                title = title.substring(0, parenthesis);
            }
        }


        title = title.replace(' ', '+');

        url += "?author=" + authorName;
        url += "&key=" + developerKey;
        url += "&title=" + title;

        return url;
    }

    /**
     * Open a connection to the page based on book title and author.
     */
    private void visitPage() {
        response = null;
        try {
            response = Jsoup.parse(new URL(generatePageURL()).openStream(),
                    "UTF-8", "", Parser.xmlParser());
        } catch (IOException ioexception) {
            log.error("IOException in visitPage for XML response", ioexception);
        } catch (Exception exception) {
            log.error("Exception in visitPage for XML response", exception);
        }
    }

    /**
     * To fill a List of Strings in order to add to the book's genre field.
     * @param shelves the shelves element on the page.
     */
    private void parseShelvesForGenres(Element shelves) {
        Elements allShelves = shelves.getElementsByTag("shelf");

        for (Element shelf: allShelves) {
            System.out.println(shelf.attr("name"));
        }
    }

    /**
     * To fill in the empty values in the book object in order to add it to the database.
     */
    private void addValuesToBook() {
        // rating
        // number of ratings
        // number of reviews
        // genre
        // isbn
        // good reads id
        Element bookElement = response.select("book").first();
        Element idElement = bookElement.select("id").first();
        Element isbnElement = bookElement.select("isbn").first();
        Element averageRatingElement = bookElement.select("average_rating").first();
        Element numberOfRatingsElement = bookElement.select("ratings_count").first();
        Element numberOfReviewsElement = bookElement.select("text_reviews_count").first();
        Element popularShelvesElement = bookElement.select("popular_shelves").first();


        if (idElement.hasText()) {
            currentText.setGoodreadsId(idElement.html());
        }

        if (isbnElement.hasText()) {
            currentText.setIsbn(isbnElement.html());
        } else {
            currentText.setIsbn("0000000000");
        }

        if (averageRatingElement.hasText()) {
            currentText.setRating(Double.parseDouble(averageRatingElement.html()));
        }

        if (numberOfRatingsElement.hasText()) {
            currentText.setNumberOfRatings(Integer.parseInt(numberOfRatingsElement.html()));
        }

        if (numberOfReviewsElement.hasText()) {
            currentText.setNumberOfReviews(Integer.parseInt(numberOfReviewsElement.html()));
        }

        parseShelvesForGenres(popularShelvesElement);

        System.out.println(currentText.toString());



        System.out.println("Book values added");
    }

    /**
     * The main running method of this class to do the work.
     */
    public void fillInBook() {

        System.out.println(generatePageURL());

        visitPage();

        addValuesToBook();

        System.out.println("Books filled in");
    }
}
