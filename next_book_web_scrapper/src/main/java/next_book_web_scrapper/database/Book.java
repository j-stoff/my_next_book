package next_book_web_scrapper.database;

public class Book {

    private int id;
    private String title;
    private String authorName;
    private double rating;
    private int numberOfRatings;
    private int numberOfReviews;
    private String genre;
    private int fk_id_author;
    private int isbn;
    private int goodreadsId;


    /**
     * Getter for book id in the book table.
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Setter for id field.
     * @param id new ID.
     */

    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter for the book's title.
     * @return title
     */

    public String getTitle() {
        return title;
    }

    /**
     * Setter for the book title.
     * @param title new book title.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Getter for the book's author.
     * @return authorName
     */
    public String getAuthorName() {
        return authorName;
    }

    /**
     * Setter for book author.
     * @param authorName new author name.
     */
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    /**
     * Getter for book's rating.
     * @return rating
     */
    public double getRating() {
        return rating;
    }

    /**
     * Setter for book's rating.
     * @param rating the new rating.
     */
    public void setRating(double rating) {
        this.rating = rating;
    }

    /**
     * Getter for number of ratings for this book.
     * @return numberOfRatings
     */
    public int getNumberOfRatings() {
        return numberOfRatings;
    }

    /**
     * Setter for the number of ratings of this book.
     * @param numberOfRatings new number of ratings
     */
    public void setNumberOfRatings(int numberOfRatings) {
        this.numberOfRatings = numberOfRatings;
    }

    /**
     * Getter for the number of book reviews.
     * @return numberOfReviews
     */
    public int getNumberOfReviews() {
        return numberOfReviews;
    }

    /**
     * Setter book's number of reviews.
     * @param numberOfReviews the new number of reviews.
     */
    public void setNumberOfReviews(int numberOfReviews) {
        this.numberOfReviews = numberOfReviews;
    }

    /**
     * Getter for the book's genre.
     * @return genre
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Setter for the book's genre.
     * @param genre the new book genre.
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }

    /**
     * Getter for the author's id from the author table.
     * @return fk_id_author
     */
    public int getFk_id_author() {
        return fk_id_author;
    }

    /**
     * Setter for the author's id from the author table.
     * @param fk_id_author the new author id (should not change).
     */
    public void setFk_id_author(int fk_id_author) {
        this.fk_id_author = fk_id_author;
    }

    /**
     * Getter for the book's isbn. The 10 digit version.
     * @return isbn
     */
    public int getIsbn() {
        return isbn;
    }

    /**
     * Setter for the book's isbn. 10 digit version.
     * @param isbn new book isbn (should not change).
     */
    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    /**
     * Getter for the good reads id.
     * @return goodreadsId
     */
    public int getGoodreadsId() {
        return goodreadsId;
    }

    /**
     * Setter for the good reads id.
     * @param goodreadsId the new good reads id.
     */
    public void setGoodreadsId(int goodreadsId) {
        this.goodreadsId = goodreadsId;
    }
}
