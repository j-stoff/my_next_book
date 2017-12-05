package com.nextBook.database.persistence;

import com.nextBook.database.entity.Author;
import com.nextBook.database.entity.Book;
import com.nextBook.database.persistence.AuthorDAO;
import com.nextBook.database.persistence.BookDAO;
import org.junit.After;
import org.junit.Test;
import org.junit.Before;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class BookDAOTest {

    private BookDAO bookDao;
    private AuthorDAO authorDao;
    private Author author;
    @Before
    public void setup() {
        bookDao = new BookDAO();
        authorDao = new AuthorDAO();

        author = new Author("Ben", "Jamin", 4);
        int addReturnCode = authorDao.safeAddAuthor(author);
        assertTrue("Couldn't add author to the database", addReturnCode > 0);
    }


    @After
    public void cleanUp() {
        int deleteCode = authorDao.safeDeleteAuthor(author);
        assertTrue("Delete author didn't work", deleteCode > 0);
    }

    @Test
    public void addBookStaticBookTest() throws Exception {
        //assertTrue("Author not added to the database.",id > 0);
        List<String> genreList = new ArrayList<String>();
        genreList.add("Classical");
        genreList.add("Rock");
        Book book = new Book();
        book.setAuthorName("Meseeks");
        book.setTitle("Beep a de boo bop");
        book.setGenre(genreList);
        book.setIsbn("1234567890");
        book.setRating(5);
        book.setFk_id_author(author);

        bookDao.deleteBook(book);
        int book_id = bookDao.addBook(book);
        assertTrue("Validation failure", book_id >= 0);
        assertTrue("Book was not added to the database", book_id != 0);

        //int authorDelete = authorDao.deleteAuthor(author);
        //assertTrue("Delete author failed for addBookStaticTest", authorDelete > 0);
    }



    @Test
    public void getBookInDatabase() throws Exception {
        //Author author = new Author("Ben", "Jamin", 4);
        List<String> genreList = new ArrayList<String>();
        genreList.add("Classical");
        genreList.add("Rock");
        Book book = new Book();
        book.setAuthorName("Meseeks");
        book.setTitle("Differet student book");
        book.setGenre(genreList);
        book.setIsbn("1234567890");
        book.setRating(5);
        book.setFk_id_author(author);


        int book_id = bookDao.addBook(book);
        Book inDatabase = bookDao.getBook(book_id);



        bookDao.deleteBook(book);
        assertTrue("Get book failure", inDatabase != null);
        //assertTrue(book.getAuthorName().equals("Kansas"));
    }

    /*
    @Test
    public void updateBookAlreadyInDatabase() {
        Book book = bookDao.getBook(1);

        assertTrue("Get fail for book update", book != null);

        book.setRating(3);
        book.setTitle("Rick and morty");

        boolean passed = bookDao.updateBook(book);
        assertTrue(passed);
    }
    */

    @Test
    public void deleteBookTest() {
        //Author author = new Author("Ben", "Jamin", 4);
        ArrayList<String> genres = new ArrayList<String>();
        genres.add("Contemporary");
        genres.add("Hello");
        Book book = new Book();
        book.setAuthorName("Rick James");
        book.setTitle("To be nuked");
        book.setRating(5);
        book.setIsbn("1234567890");
        book.setGenre(genres);
        book.setFk_id_author(author);

        int book_id = bookDao.addBook(book);
        assertTrue("Fail to add in delete Test", book_id > 0);


        int other_id = bookDao.deleteBook(book);

        assertTrue("Ids do not match in delete",other_id == book_id);
    }

    /*
    @Test
    public void deleteBookById() {
        ArrayList<String> genres = new ArrayList<String>();
        genres.add("comedy");
        genres.add("relationships");
        Book book = new Book();
        book.setAuthorName("Rick James");
        book.setTitle("Deleted 2");
        book.setRating(5);
        book.setIsbn(1234567890);
        book.setGenre(genres);
        book.setFk_id_author(3);
        int book_id = bookDao.addBook(book);
        assertTrue("Fail to add in delete Test", book_id > 0);

        int delete_id = bookDao.deleteBook(book_id);
        assertTrue( delete_id > 0);
        assertTrue(delete_id == book_id);
    }
    */
    @Test
    public void safeAddBookTest() {
        //Author author = new Author("Ben", "Jamin", 4);
        ArrayList<String> genres = new ArrayList<String>();
        genres.add("Dark");
        genres.add("Action");
        Book book = new Book();
        book.setAuthorName("Morty");
        book.setTitle("Adventures of Rick and morty season 99");
        book.setRating(5);
        book.setIsbn("1234567891");
        book.setGenre(genres);
        book.setFk_id_author(author);

        int book_id = bookDao.safeAddBook(book);
        assertTrue("Error occurred\n", book_id >= 0);
        assertTrue("Addition failure\n", book_id > 0);

        Book inDatabase = bookDao.getBook(book_id);

        assertTrue("Id was never set", inDatabase.getId() != 0);

        int another_id = bookDao.safeAddBook(inDatabase);
        assertTrue("Add went through\n", another_id == 0);

        bookDao.safeDeleteBook(book);
    }

    @Test
    public void safeDeleteBookTest() {
        //Author author = new Author("Ben", "Jamin", 4);
        ArrayList<String> genres = new ArrayList<String>();
        genres.add("Dark");
        genres.add("Action");
        Book book = new Book();
        book.setAuthorName("Morty");
        book.setTitle("Guiness book of world records");
        book.setRating(5);
        book.setIsbn("1234567891");
        book.setGenre(genres);
        book.setFk_id_author(author);

        int book_id = bookDao.safeAddBook(book);
        assertTrue("Error occurred\n", book_id >= 0);
        assertTrue("Addition failure\n", book_id > 0);

        int delete_id = bookDao.safeDeleteBook(book);
        assertTrue("Delete failed\n", delete_id > 0);
        assertTrue("Ids are not the same", delete_id == book_id);
    }

    @Test
    public void safeUpdateBookTest() {
        //Author author = new Author("Ben", "Jamin", 4);
        ArrayList<String> genres = new ArrayList<String>();
        genres.add("Dark");
        genres.add("Comedy");
        Book book = new Book();
        book.setAuthorName("Morty");
        book.setTitle("Adventures of Rick and Morty");
        book.setRating(5);
        book.setIsbn("1234567891");
        book.setGenre(genres);
        book.setFk_id_author(author);


        int book_id = bookDao.safeAddBook(book);

        book.setIsbn("1111111111");

        boolean passed = bookDao.safeUpdateBook(book);
        assertTrue("Update Failed", passed);

        bookDao.safeDeleteBook(book);
    }



    @Test
    public void testForFindBookByTitle() {
        List<Book> results = (List<Book>)bookDao.findBookByTitle("Dune");

        assertNotNull("Query failed", results);
        for (Book book : results) {
            System.out.println(book.toString());
        }
    }


    @Test
    public void searchBasedOnTitleTest() throws Exception {
        Author authorOfBook = addAuthorToDatabase();
        Book bookToAdd = addBookToDatabase(authorOfBook);

        List<Book> results = bookDao.searchBasedOnTitle(bookToAdd.getTitle());

        assertNotNull("The results returned were null", results);
        assertTrue("The list does not contain the book", results.contains(bookToAdd));

        assertTrue("Book was not cleaned from database", bookDao.deleteBook(bookToAdd) > 0);
        assertTrue("Author was not cleaned from database", authorDao.deleteAuthor(authorOfBook) > 0);
    }

    private Author addAuthorToDatabase() {
        Author author = new Author("Stephen", "King", 4.5);

        int id = authorDao.addAuthor(author);
        author.setId(id);

        assertTrue("Author not added to database", id > 0);

        return author;
    }

    private Book addBookToDatabase(Author author) {
        List<String> genres = new ArrayList<>();
        genres.add("sci-fi");
        genres.add("cult thriller");
        Book book = new Book("Ready player one", "Ernest Cline", 5, genres, "1234567890");
        book.setFk_id_author(author);

        int id = bookDao.addBook(book);

        book.setId(id);


        assertTrue("Book was not added to database", id > 0);

        return book;
    }

}