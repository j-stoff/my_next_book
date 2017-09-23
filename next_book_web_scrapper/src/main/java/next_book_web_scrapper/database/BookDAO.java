package next_book_web_scrapper.database;

import next_book_web_scrapper.entity.Book;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.ArrayList;

public class BookDAO {

    private final Logger log = Logger.getLogger(this.getClass());


    /**
     *
     */
    public BookDAO() {

    }


    /**
     * An add for a book object to the database.
     * @param book book to be added to the database
     * @return the id of the book in the database, 0 if the book already exists, -1 for error
     */
    public int addBook(Book book) {
        if (!areNullFieldsValid(book)) {
            return -1;
        }

        int book_id = 0;
        Session databaseSession = null;
        Transaction currentTransaction = null;

        try {
            databaseSession = SessionFactoryProvider.getSessionFactory().openSession();
            currentTransaction = databaseSession.beginTransaction();
            book_id = (int)databaseSession.save(book);
            currentTransaction.commit();
        } catch (HibernateException hibernateException) {
            String message = "Hibernate Exception in addBook";
            rollbackTransaction(currentTransaction, message, hibernateException);
        } catch (Exception exception) {
            String message = "Exception in addBook";
            rollbackTransaction(currentTransaction, message, exception);
        } finally {
            try {
                if (databaseSession != null) {
                    databaseSession.close();
                }
            } catch (Exception exception) {
                log.error("Problem closing session in addBook", exception);
            }
        }

        return book_id;
    }

    /**
     * For changing a book's values in the database, the book's non-null values are expected to be
     * not null and contain some information.
     * @param book book object to be compared
     * @return true if non-nulls are valid, false if they are not.
     */
    private boolean areNullFieldsValid(Book book) {
        if (book.getId() <= 0) {
            return false;
        }

        if (book.getTitle() == null || book.getTitle().isEmpty()) {
            return false;
        }

        if (book.getAuthorName() == null || book.getAuthorName().isEmpty()) {
            return false;
        }

        if (book.getRating() <= 0 || book.getRating() > 5) {
            return false;
        }

        if (book.getGenre() == null || book.getGenre().isEmpty()) {
            return false;
        }

        if (book.getIsbn() <= 0 || Integer.toString(book.getIsbn()).length() != 10) {
            return false;
        }

        return true;
    }


    /**
     * Helper method to aid with error handling
     * @param currentTransaction the current transaction object in a session
     * @param message Output message
     * @param exception the exception thrown to be handled.
     */
    private void rollbackTransaction(Transaction currentTransaction,
                                     String message, Exception exception) {
        if (currentTransaction == null) {
            log.error(message + ", rollback not needed", exception);
            return;
        }

        try {
            currentTransaction.rollback();
            log.error(message + ", rollback committed", exception);
        } catch (Exception exceptionWithException) {
            log.error(message + ", exceptionWithException, rollback error", exceptionWithException);
        }


    }
}
