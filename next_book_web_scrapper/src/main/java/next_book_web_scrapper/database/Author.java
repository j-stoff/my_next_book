package next_book_web_scrapper.database;

import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import java.util.List;

@Entity
@Table(name="authors")
public class Author {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name="id_author")
    private int id;

    @Column(name="author_first_name")
    private String firstName;

    @Column(name="author_last_name")
    private String lastName;

    @Column(name="author_genre")
    @Convert(converter = ListConverter.class)
    private List<String> genres;

    @Column(name="author_average_rating")
    private double averageRating;

    @Column(name="author_book_reviews")
    private String bookReviews;


    /**
     * No argument constructor
     */
    public Author() {
    }

    /**
     * Argument constructor for testing convenience.
     * @param aFirstName a first name for the author.
     * @param aLastName a last name for the author.
     * @param anAverageRating an average book rating for this author.
     */
    public Author(String aFirstName, String aLastName, double anAverageRating) {
        this.firstName = aFirstName;
        this.lastName = aLastName;
        this.averageRating = anAverageRating;
    }

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
