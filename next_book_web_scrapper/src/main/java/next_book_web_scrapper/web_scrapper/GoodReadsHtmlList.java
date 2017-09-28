package next_book_web_scrapper.web_scrapper;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * This class will parse through an HTML page to get book titles and authors
 * then return a list of said books upon completion of parsing
 */
public class GoodReadsHtmlList {

    private String target;
    private final Logger log = Logger.getLogger(this.getClass());
    private Document htmlPage;

    public GoodReadsHtmlList() {
    }

    public GoodReadsHtmlList(String url) {
        this();
        this.target = url;
    }

    private void connectToPage() {
        try {
            htmlPage = Jsoup.connect(target).get();
        } catch (IOException ioException) {
            log.error("IOException in connectToPage", ioException);
        } catch (Exception exception) {
            log.error("Exception in connectToPage", exception);
        }
    }

    public void go() {
        connectToPage();
    }
}
