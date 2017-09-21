package next_book_web_scrapper.database;

import java.util.List;

public class Author {

    private int id;
    private String firstName;
    private String lastName;
    private List<String> genres;
    private double averageRating;
    private String bookReviews;

    /**
     * Getter for author's id.
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Setter for the author's id.
     * @param id new author id for the author table.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter for the author's first name.
     * @return firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Setter for the author's first name.
     * @param firstName new author first name.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Getter for the author's last name.
     * @return lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Setter for author's last name.
     * @param lastName new author last name.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Getter for a list of author's book genres
     * @return genres as a list of Strings.
     */
    public List<String> getGenres() {
        return genres;
    }

    /**
     * Setter for a list of author's book genres as a list.
     * @param genres new list of genres.
     */
    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    /**
     * Getter for the average rating of all the author's books.
     * @return averageRating
     */
    public double getAverageRating() {
        return averageRating;
    }

    /**
     * Setter for the average rating of all author's books.
     * @param averageRating new average book rating of all authored books.
     */
    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    /**
     * Getter for books reviewed by the author.
     *      Subject to change
     * @return bookReviews
     */
    public String getBookReviews() {
        return bookReviews;
    }

    /**
     * Setter for books reviewed by the author.
     *      Subject to change.
     * @param bookReviews reviews done by the author of non-authored books.
     */
    public void setBookReviews(String bookReviews) {
        this.bookReviews = bookReviews;
    }
}
