package next_book_web_scrapper.web_scrapper;

import next_book_web_scrapper.entity.Book;

/**
 * This class is designed to parse a response from a GoodReads API call
 * and fill a book object based on that response.
 */
public class GoodReadsResponseBookTitle {

    private Book currentText;
    private String developerKey;
    private String targetURL;

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
     * The main running method of this class to do the work.
     */
    public void fillInBook() {

        System.out.println(generatePageURL());

        System.out.println("Books filled in");
    }
}
