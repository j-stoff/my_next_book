package com.nextBook.database.entity;

import com.nextBook.database.persistence.AuthorDAO;
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
        int deleteCode = authorDao.deleteAuthor(author);
        assertTrue("Delete failed for constraint Test", deleteCode > 0);
    }

    @Test
    public void getAuthorTest() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("Dark");
        list.add("Fantasy");
        Author author = new Author("Rick", "Sanchez", 5);
        author.setGenres(list);
        int author_id = authorDao.addAuthor(author);
        Author getAuthor = authorDao.getAuthor(author_id);

        assertEquals("Genres not the same\n", author.getGenres(), getAuthor.getGenres());
        assertEquals("First name not the same\n", author.getFirstName(), getAuthor.getFirstName());
        assertEquals("Last name not the same\n", author.getLastName(), getAuthor.getLastName());
        assertTrue("Rating not equal\n", author.getAverageRating() == getAuthor.getAverageRating());

        int deleteCode = authorDao.deleteAuthor(author);
        assertTrue("Delete fail for getAuthorTest", deleteCode > 0);
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
    public void updateAuthorAddNewTest() throws Exception {
        Author author = new Author("updateNew", "Added", 10);
        authorDao.addAuthor(author);
        author.setLastName("Each Time");
        author.setAverageRating(567);

        boolean passed = authorDao.updateAuthor(author);
        assertTrue(passed);

        int deleteCode = authorDao.deleteAuthor(author);
        assertTrue("Delete author failed for updateAuthor Test", deleteCode > 0);
    }

    @Test
    public void safeAddAuthorAlreadyInDatabaseTest() {
        Author author = new Author("My nane", "Is not needed", 5);
        int original_id = authorDao.safeAddAuthor(author);
        assertTrue( original_id > 0);
        int second_id = authorDao.safeAddAuthor(author);
        assertTrue( second_id == 0);

        int deleteCode = authorDao.safeDeleteAuthor(author);
        assertTrue("Delete author failed for safeAddAuthorTest", deleteCode > 0);
    }

    @Test
    public void safeUpdateAuthorTest() {
        Author author = new Author("Safe", "Update", 1);
        int author_id = authorDao.safeAddAuthor(author);
        assertTrue("Add failure", author_id > 0);

        author.setAverageRating(55);
        boolean passed = authorDao.safeUpdateAuthor(author);
        assertTrue("Did not pass", passed);

        int delete_id = authorDao.safeDeleteAuthor(author);
        assertTrue("Not the same id", author_id == delete_id);

        passed = authorDao.safeUpdateAuthor(author);
        assertFalse("The update went through but should not have", passed);
    }

    @Test
    public void safeDeleteAuthorTest() {
        Author author = new Author("For", "Safe Delete", 2);
        int author_id = authorDao.safeAddAuthor(author);
        assertTrue("Author not added", author_id > 0);

        int delete_id = authorDao.safeDeleteAuthor(author);
        assertTrue("Safe delete failure", delete_id > 0);
        assertTrue("Ids do not match", delete_id == author_id);
    }

    @Test
    public void findAuthorWithoutUsingId() {
        Author author = new Author("Gib", "Pence", 3);
        int author_id = authorDao.safeAddAuthor(author);
        assertTrue("Author not added", author_id > 0);


        boolean passed = authorDao.isAuthorInDatabaseQuery(author);
        assertTrue("Did not find the author",passed);

        int deleteCode = authorDao.safeDeleteAuthor(author);
        assertTrue("Delete author failed for findAuthorWithoutUsingId", deleteCode > 0);

    }

    @Test
    public void findAuthorAndGetId() {
        Author author = new Author("Locke", "Lamora", 5);
        int author_id = authorDao.safeAddAuthor(author);
        assertTrue("Author not added", author_id > 0);

        int found_id = authorDao.findAuthorByClass(author);
        assertTrue("Values not equal", found_id == author_id);

        int deleteCode = authorDao.safeDeleteAuthor(author);
        assertTrue("Delete fail in findAuthor", deleteCode > 0);
    }
}