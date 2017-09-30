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

    public GoodReadsHtmlList() {
    }

    public GoodReadsHtmlList(String url, int numberOfPages) {
        this();
        this.target = url;
        booksOnPage = new ArrayList<Book>();
        maxPages = numberOfPages;
    }

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

    private void getBooksFromRow() {
        Book current = null;
        Elements titleAnchor = null;
        Elements titleSpan = null;
        Elements authorAnchor = null;
        Elements authorSpan = null;


        for (Element row : tableRows) {
            current = new Book();
            titleAnchor = row.getElementsByClass("bookTitle");
            for (Element anchor: titleAnchor) {
                titleSpan = anchor.getElementsByAttributeValueMatching(
                        "itemprop", "name");
                current.setTitle(titleSpan.html());
            }
            authorAnchor = row.getElementsByClass("authorName");
            for (Element anchor: authorAnchor) {
                authorSpan = anchor.getElementsByAttributeValueMatching(
                        "itemprop", "name");
                current.setAuthorName(authorSpan.html());
            }
            booksOnPage.add(current);
        }

    }

    private void visitAllPages() {
        String currentURL = null;
        for(int index = 2; index <= maxPages; index++) {
            currentURL = nextPageName(index);
            System.out.println(currentURL);
            connectToPage(currentURL);
            getBooksFromRow();
        }
    }

    private String nextPageName(int pageNumber) {
        return target + "?page=" + pageNumber;
    }

    private void printAllBooks() {
        for (Book current: booksOnPage) {
            System.out.println(current.toString());
        }
    }

    public List<Book> getBooksWithAuthors() {
        return booksOnPage;
    }

    public void go() {
        boolean firstPageConnection = connectToPage(target);

        if (!firstPageConnection) {
            System.out.println("wrong url");
            return;
        }

        getBooksFromRow();

        //visitAllPages();

        printAllBooks();

    }
}
