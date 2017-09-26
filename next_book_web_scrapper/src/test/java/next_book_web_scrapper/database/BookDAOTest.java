package next_book_web_scrapper.database;

import next_book_web_scrapper.entity.Book;
import org.junit.Test;
import org.junit.Before;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class BookDAOTest {

    private BookDAO bookDao;
    @Before
    public void setup() {
        bookDao = new BookDAO();
    }

    @Test
    public void addBookStaticBookTest() throws Exception {
        List<String> genreList = new ArrayList<String>();
        genreList.add("Classical");
        genreList.add("Rock");
        Book book = new Book();
        book.setAuthorName("Meseeks");
        book.setTitle("Amazing book");
        book.setGenre(genreList);
        book.setIsbn(1234567890);
        book.setRating(5);
        book.setFk_id_author(3);
        int book_id = bookDao.addBook(book);
        assertTrue("Validation failure", book_id >= 0);
        assertTrue("Book was not added to the database", book_id != 0);
    }



    @Test
    public void getBookInDatabase() throws Exception {
        Book book = bookDao.getBook(1);

        assertTrue("Get book failure", book != null);
        //assertTrue(book.getAuthorName().equals("Kansas"));
    }

    @Test
    public void updateBookAlreadyInDatabase() {
        Book book = bookDao.getBook(1);

        assertTrue("Get fail for book update", book != null);

        book.setRating(3);
        book.setTitle("Rick and morty");

        boolean passed = bookDao.updateBook(book);
        assertTrue(passed);
    }

    @Test
    public void deleteBookTest() {
        ArrayList<String> genres = new ArrayList<String>();
        genres.add("Contemporary");
        genres.add("Hello");
        Book book = new Book();
        book.setAuthorName("Rick James");
        book.setTitle("To be nuked");
        book.setRating(5);
        book.setIsbn(1234567890);
        book.setGenre(genres);
        book.setFk_id_author(3);
        int book_id = bookDao.addBook(book);
        assertTrue("Fail to add in delete Test", book_id > 0);


        int other_id = bookDao.deleteBook(book);

        assertTrue("Ids do not match in delete",other_id == book_id);
    }

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
}