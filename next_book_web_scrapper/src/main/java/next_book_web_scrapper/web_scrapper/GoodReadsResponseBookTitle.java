package next_book_web_scrapper.web_scrapper;

import next_book_web_scrapper.entity.Book;
import next_book_web_scrapper.entity.Author;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.jsoup.nodes.Element;
import org.apache.log4j.Logger;
import org.jsoup.select.Elements;

import javax.el.ELException;
import javax.sound.midi.SysexMessage;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is designed to parse a response from a GoodReads API call
 * and fill a book object based on that response.
 */
public class GoodReadsResponseBookTitle {

    private Book currentText;
    private Author bookAuthor;
    private String developerKey;
    private String targetURL;
    private Document response;
    private final Logger log = Logger.getLogger(this.getClass());

    /**
     * No argument constructor
     */
    public GoodReadsResponseBookTitle() {
        bookAuthor = new Author();
    }

    // Send the
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

    private boolean checkShelf(String genre) {
        if (genre.contains("audiobook")) {
            return false;
        } else if (genre.contains("to-read")) {
            return false;
        } else  if (genre.contains("default")) {
            return false;
        } else if (genre.contains("ebook")) {
            return false;
        } else if (genre.contains("read-in")) {
            return false;
        } else if (genre.contains("favorites")) {
            return false;
        } else if (genre.contains("on-hold")) {
            return false;
        }

        return true;
    }

    /**
     * To fill a List of Strings in order to add to the book's genre field.
     * @param shelves the shelves element on the page.
     */
    private void parseShelvesForGenres(Element shelves) {
        Elements allShelves = shelves.getElementsByTag("shelf");
        List<String> genres = new ArrayList<String>();

        for (Element shelf: allShelves) {
            if (checkShelf(shelf.attr("name"))) {
                genres.add(shelf.attr("name"));
            }

            if (genres.size() == 3) {
                break;
            }
        }

        currentText.setGenre(genres);

        System.out.println(genres.toString());
    }

    /**
     * To fill in the empty values in the book object in order to add it to the database.
     */
    private void addValuesToBook() {
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

    }


    private void addNameToAuthor() {
        String fullName = currentText.getAuthorName();
        String firstName = fullName.split(" ")[0];
        bookAuthor.setFirstName(firstName);

        String[] name = fullName.split(" ");
        int size = Array.getLength(name);
        StringBuilder nameString = new StringBuilder(name[size - 1]);
        //String lastName = name[size - 1];

        for (int index = size - 2; index > 0; index --) {
            //lastName += ", " + name[index];
            nameString.append(", " + name[index]);
        }

        String lastName = nameString.toString();

        bookAuthor.setLastName(lastName);
    }

    private void addValuesToAuthor() {
        // Split the author name already in the book
        addNameToAuthor();
        Element authorElement = response.select("author").first();
        Element nameElement = authorElement.select("average_rating").first();

        if (nameElement.hasText()) {
            bookAuthor.setAverageRating(Double.parseDouble(nameElement.html()));
        }

        System.out.println(bookAuthor.toString());

    }

    public Author getAuthorOfBook() {
        return bookAuthor;
    }

    public Book getCurrentText() {
        return currentText;
    }

    /**
     * The main running method of this class to do the work.
     */
    public void fillInBook() {

        System.out.println(generatePageURL());

        visitPage();

        addValuesToBook();

        addValuesToAuthor();

        currentText.setFk_id_author(bookAuthor);

        System.out.println("Books filled in");
    }
}
