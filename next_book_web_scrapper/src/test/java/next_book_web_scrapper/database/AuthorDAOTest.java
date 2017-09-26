package next_book_web_scrapper.database;

import next_book_web_scrapper.entity.Author;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class AuthorDAOTest {

    private AuthorDAO authorDao;

    @Before
    public void initialize() {
        authorDao = new AuthorDAO();
    }

    @Test
    public void testAddAuthorUsingConstants() throws Exception {
        Author author = new Author("Billy", "Bob", 3);
        int returnCode = authorDao.addAuthor(author);
        assertTrue("An error occurred while trying to add",returnCode >= 0);
        assertTrue("The author wasn't added to the database",returnCode != 0);
    }

    @Test
    public void getAuthorTest() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("Dark");
        list.add("Fantasy");
        Author author = new Author("Rick", "Sanchez", 5);
        author.setGenres(list);
        author.setBookReviews("None");
        int author_id = authorDao.addAuthor(author);
        Author getAuthor = authorDao.getAuthor(author_id);

        assertEquals("Genres not the same\n", author.getGenres(), getAuthor.getGenres());
        assertEquals("First name not the same\n", author.getFirstName(), getAuthor.getFirstName());
        assertEquals("Last name not the same\n", author.getLastName(), getAuthor.getLastName());
        assertTrue("Rating not equal\n", author.getAverageRating() == getAuthor.getAverageRating());
        assertEquals("Reviews not the same", author.getBookReviews(), getAuthor.getBookReviews());
    }

    /*
    @Test
    public void getKnownAuthorFromDatabaseTest() throws Exception {
        Author anAuthor = authorDao.getAuthor(1);
        assertTrue(anAuthor != null);
    }
    */
    // For known entities in database
    /*
    @Test
    public void deleteAuthorAlreadyInDatabase()  throws Exception {
        int id = authorDao.deleteAuthor(1);
        assertTrue(id != 0);
    }
    */

    @Test
    public void deleteAuthorByClass() throws Exception {
        Author author = new Author("Jake", "Gambz", 5);
        int author_id = authorDao.addAuthor(author);
        assertTrue(author_id != 0);
        int id = authorDao.deleteAuthor(author);
        assertTrue(id != 0);
        assertTrue(id == author_id);
    }

    @Test
    public void deleteAuthorById() throws Exception {
        Author author = new Author("Jacobo", "Stroganoff", 7);
        int author_id = authorDao.addAuthor(author);
        int delete_id = authorDao.deleteAuthor(author_id);
        assertTrue("Delete fail\n",delete_id > 0);
        assertTrue("Ids not equal\n", author_id == delete_id);
    }

    @Test
    public void updateAuthorAddNewTest() throws Exception {
        Author author = new Author("updateNew", "Added", 10);
        authorDao.addAuthor(author);
        author.setLastName("Each Time");
        author.setAverageRating(567);

        boolean passed = authorDao.updateAuthor(author);
        assertTrue(passed);
    }
}