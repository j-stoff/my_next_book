package next_book_web_scrapper.web_scrapper;

import next_book_web_scrapper.entity.Book;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class will parse through an HTML page to get book titles and authors
 * then return a list of said books upon completion of parsing
 */
public class GoodReadsHtmlList {

    private String target;
    private int maxPages;
    private final Logger log = Logger.getLogger(this.getClass());
    private Document htmlPage;
    private Elements tableRows;
    private List<Book> booksOnPage;

    /**
     * Default no argument constructor
     */
    public GoodReadsHtmlList() {
    }

    /**
     * Constructor with url and number of pages for that URL.
     * @param url the base url to connect to.
     * @param numberOfPages Number of pages associated with the base url.
     */
    public GoodReadsHtmlList(String url, int numberOfPages) {
        this();
        this.target = url;
        booksOnPage = new ArrayList<Book>();
        maxPages = numberOfPages;
    }

    /**
     * To connect to an html page using Jsoup. Parses for Book objects on
     * the page. Exceptions are caught if the page is malformed.
     * @param pageURL The page to connect to.
     * @return true if the connection worked, false if it did not.
     */
    private boolean connectToPage(String pageURL) {
        try {
            htmlPage = Jsoup.connect(pageURL).get();

            tableRows = htmlPage.getElementsByAttributeValueMatching(
                    "itemtype", "http://schema.org/Book");

            if (tableRows == null) {
                return false;
            }
        } catch (IOException ioException) {
            log.error("IOException in connectToPage", ioException);
        } catch (Exception exception) {
            log.error("Exception in connectToPage", exception);
        }

        return true;
    }

    /**
     * Given the table rows from an html page, go through the necessary
     * elements in order to extract information about the book title
     * and author name.
     */
    private void getBooksFromRow() {
        Book current = null;
        Elements titleAnchor = null;
        Elements titleSpan = null;
        Elements authorAnchor = null;
        Elements authorSpan = null;
        boolean addBook = false;


        for (Element row : tableRows) {
            current = new Book();
            titleAnchor = row.getElementsByClass("bookTitle");
            for (Element anchor: titleAnchor) {
                titleSpan = anchor.getElementsByAttributeValueMatching(
                        "itemprop", "name");
                if (titleSpan.hasText()) {
                    current.setTitle(titleSpan.html());
                    addBook = true;
                }
            }
            authorAnchor = row.getElementsByClass("authorName");
            for (Element anchor: authorAnchor) {
                authorSpan = anchor.getElementsByAttributeValueMatching(
                        "itemprop", "name");
                if (authorSpan.hasText()) {
                    current.setAuthorName(authorSpan.html());
                    addBook = true;
                }
            }
            if (addBook) {
                booksOnPage.add(current);
                addBook = false;
            }
        }

    }

    /**
     * To visit all pages given the max pages set in the constructor.
     */
    private void visitAllPages() {
        String currentURL = null;
        for(int index = 2; index <= maxPages; index++) {
            currentURL = nextPageName(index);
            System.out.println(currentURL);
            if (connectToPage(currentURL)) {
                getBooksFromRow();
            }
        }
    }


    private void visitSelectNumberOfPages() {
        String currentURL = null;
        for(int index = 2; index <= 10; index++) {
            currentURL = nextPageName(index);
            System.out.println(currentURL);
            if (connectToPage(currentURL)) {
                getBooksFromRow();
            }
        }
    }

    /**
     * Helper method to format the next page's url
     * @param pageNumber current page to query.
     * @return The full URL string for the page.
     */
    private String nextPageName(int pageNumber) {
        return target + "?page=" + pageNumber;
    }

    /**
     * Debugging to print all book values
     */
    private void printAllBooks() {
        for (Book current: booksOnPage) {
            System.out.println(current.toString());
        }
    }

    /**
     * Getter method to return the list books found on the pages.
     * @return List of book items with book title and author fields.
     */
    public List<Book> getBooksWithAuthors() {
        return booksOnPage;
    }

    /**
     * Main running method to use other methods.
     */
    public void go() {
        boolean firstPageConnection = connectToPage(target);

        if (!firstPageConnection) {
            System.out.println("wrong url");
            return;
        }

        getBooksFromRow();

        //visitAllPages();

        //visitSelectNumberOfPages();

        //printAllBooks();

    }
}
