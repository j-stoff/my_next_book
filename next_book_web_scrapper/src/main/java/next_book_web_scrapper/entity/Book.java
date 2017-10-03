package next_book_web_scrapper.entity;

import next_book_web_scrapper.util.ListConverter;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="books")
public class Book {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name="id_books")
    private int id;

    @Column(name="book_title")
    private String title;

    @Column(name="book_author")
    private String authorName;

    @Column(name="book_rating")
    private double rating;

    @Column(name="book_num_rating")
    private int numberOfRatings;

    @Column(name="book_num_review")
    private int numberOfReviews;

    @Column(name="book_genre")
    @Convert(converter = ListConverter.class)
    private List<String> genre;

    @Column(name="fk_id_author")
    private int fk_id_author;

    @Column(name="book_isbn")
    private String isbn;

    @Column(name="book_goodreads_id")
    private String goodreadsId;


    @ManyToOne
    @JoinColumn(name = "id_author", nullable = false)
    private Author bookAuthor;


    /**
     * No argument constructor
     */
    public Book() {

    }

    /**
     * Constructor for non-null fields. Testing purposes mainly.
     */
    public Book(int anId, String bookTitle, String anAuthorName,
                int aBookRating, List<String> bookGenres, String anIsbn) {
        this.id = anId;
        this.title = bookTitle;
        this.authorName = anAuthorName;
        this.rating = aBookRating;
        this.genre = bookGenres;
        this.isbn = anIsbn;
    }

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
     * Getter for the book's genre as a list.
     * @return genre as a list.
     */
    public List<String> getGenre() {
        return genre;
    }

    /**
     * Setter for the book's list of genres.
     * @param genre the new book genres in a list of strings.
     */
    public void setGenre(List<String> genre) {
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
    public String getIsbn() {
        return isbn;
    }

    /**
     * Setter for the book's isbn. 10 digit version.
     * @param isbn new book isbn (should not change).
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * Getter for the good reads id.
     * @return goodreadsId
     */
    public String getGoodreadsId() {
        return goodreadsId;
    }

    /**
     * Setter for the good reads id.
     * @param goodreadsId the new good reads id.
     */
    public void setGoodreadsId(String goodreadsId) {
        this.goodreadsId = goodreadsId;
    }


    public Author getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(Author bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    @Override
    public String toString() {
        String lineSeparator = System.getProperty("line.separator");
        String output = lineSeparator;
        output += "Database id: " + id + lineSeparator;
        output += "Title: " + title + lineSeparator;
        output += "Author: " + authorName + lineSeparator;
        output += "Rating: " + rating + lineSeparator;
        output += "Number of ratings: " + numberOfRatings + lineSeparator;
        output += "Number of reviews: " + numberOfReviews + lineSeparator;
        if (genre != null) {
            output += "Genres: " + genre.toString() + lineSeparator;
        } else {
            output += "Genres: None yet" + lineSeparator;
        }
        output += "Book ISBN: " + isbn + lineSeparator;
        output += "Goodreads ID: " + goodreadsId + lineSeparator;

        return output;
    }
}
