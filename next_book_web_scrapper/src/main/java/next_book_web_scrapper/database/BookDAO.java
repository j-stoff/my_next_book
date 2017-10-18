package next_book_web_scrapper.database;

import next_book_web_scrapper.entity.Book;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

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
        int book_id = 0;
        Session databaseSession = null;
        Transaction currentTransaction = null;

        try {
            databaseSession = SessionFactoryProvider.getSessionFactory().openSession();
            currentTransaction = databaseSession.beginTransaction();
            book_id = (int)databaseSession.save(book);
            currentTransaction.commit();
        } catch (ConstraintViolationException alreadyInDatabase) {
            log.info("Book is already in the database: " + book.getTitle());
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
     * Retrieve a book based on the database id.
     * @param book_id id in the database.
     * @return a Book object filled with information from the database.
     */
    public Book getBook(int book_id) {
        Book retrievedBook = null;
        Session databaseSession = null;

        try {
            databaseSession = SessionFactoryProvider.getSessionFactory().openSession();
            retrievedBook = (Book) databaseSession.get(Book.class, book_id);
        } catch (HibernateException hibernateException) {
            log.error("Hibernate Exception in getBook", hibernateException);
        } catch (Exception exception) {
            log.error("Exception in getBook", exception);
        } finally {
            try {
                if (databaseSession != null) {
                    databaseSession.close();
                }
            } catch (Exception exception) {
                log.error("Problem closing session in getBook", exception);
            }
        }

        return retrievedBook;
    }

    /**
     * Update a book in the database. If the book doesn't exist, it will be created.
     * @param bookUpdated the book data to update.
     * @return boolean if the book update was successful.
     */
    public boolean updateBook(Book bookUpdated) {
        Session databaseSession = null;
        Transaction currentTransaction = null;

        try {
            databaseSession = SessionFactoryProvider.getSessionFactory().openSession();
            currentTransaction = databaseSession.beginTransaction();
            databaseSession.update(bookUpdated);
            currentTransaction.commit();
        } catch (HibernateException hibernateException) {
            String message = "Hibernate Exception in updateBook";
            rollbackTransaction(currentTransaction, message, hibernateException);
        } catch (Exception exception) {
            String message = "Exception in updateBook";
            rollbackTransaction(currentTransaction, message, exception);
        } finally {
            try {
                if (databaseSession != null) {
                    databaseSession.close();
                }
            } catch (Exception exception) {
                log.error("Problem with closing session in updateBook", exception);
            }
        }

        return true;
    }

    /**
     * Delete a book in the database using the id.
     * @param book_id the database id of the book to delete.
     * @return the id on success, 0 if the book is not in the database, -1 for an error
     */
    /*
    public int deleteBook(int book_id) {
        if (book_id <= 0) {
            return -1;
        }
        int retrievedId;
        Book deletedBook = getBook(book_id);
        if (deletedBook == null) {
            return 0;
        } else {
            retrievedId = deletedBook.getId();
        }


        Session databaseSession = null;
        Transaction currentTransaction = null;

        try {
            databaseSession = SessionFactoryProvider.getSessionFactory().openSession();
            currentTransaction = databaseSession.beginTransaction();
            databaseSession.delete(deletedBook);
            currentTransaction.commit();
        } catch (HibernateException hibernateException) {
            String message = "Hibernate Exception in deleteBook";
            rollbackTransaction(currentTransaction, message, hibernateException);
        } catch (Exception exception) {
            String message = "Exception in deleteBook";
            rollbackTransaction(currentTransaction, message, exception);
        } finally {

            try {
                if (databaseSession != null) {
                    databaseSession.close();
                }
            } catch  (Exception exception) {
                log.error("Problem closing the session in deleteBook", exception);
            }
        }

        return retrievedId;
    }
    */
    /**
     * Delete a book in the database using a book object.
     * @param book book object to delete.
     * @return the id on success, 0 if the book is not in the database, -1 for an error
     */
    public int deleteBook(Book book) {
        int retrieved_id = book.getId();
        Session databaseSession = null;
        Transaction currentTransaction = null;

        try {
            databaseSession = SessionFactoryProvider.getSessionFactory().openSession();
            currentTransaction = databaseSession.beginTransaction();
            databaseSession.delete(book);
            currentTransaction.commit();
        } catch (HibernateException hibernateException) {
            String message = "Hibernate Exception in deleteBook";
            rollbackTransaction(currentTransaction, message, hibernateException);
        } catch (Exception expcetion) {
            String message = "Exception in deleteBook";
            rollbackTransaction(currentTransaction, message, expcetion);
        } finally {
            try {
                if (databaseSession != null) {
                    databaseSession.close();
                }
            } catch (Exception exception) {
                log.error("Problem closing the session deleteBook", exception);
            }
        }

        return retrieved_id;
    }


    /**
     * Wrapper method to safely add a book to the database. Non-null checking and
     * error checking occur.
     * @param bookToAdd Book object to add to the database.
     * @return Book id on success, 0 for duplicate found, -1 for error.
     */
    public int safeAddBook(Book bookToAdd) {
        if (bookToAdd == null) {
            return -1;
        }

        /*
        if (!areNullFieldsValid(bookToAdd)) {
            return -1;
        }
        */
        if (bookToAdd.getId() == 0) {
            return addBook(bookToAdd);
        } else {
            Book isBookInDatabase = getBook(bookToAdd.getId());
            if (isBookInDatabase != null) {
                return 0;
            }
            return addBook(bookToAdd);
        }
    }

    /**
     * Wrapper method to safely delete a book using a Book object.
     * @param bookToDelete the book to delete
     * @return book's id on success, 0 if the book wasn't in the database, -1 for error.
     */
    public int safeDeleteBook(Book bookToDelete) {
        if (bookToDelete == null) {
            return -1;
        }
        Book isBookInDatabase = getBook(bookToDelete.getId());
        if (isBookInDatabase == null) {
            return 0;
        }

        return deleteBook(bookToDelete);
    }

    /**
     * Wrapper method to safely update a book if it exists in the database.
     * @param bookToUpdate the book object to update.
     * @return false if the book was not update, true if the book was successfully updated.
     */
    public boolean safeUpdateBook(Book bookToUpdate) {
        if (bookToUpdate == null) {
            return false;
        }

        /*
        if (!areNullFieldsValid(bookToUpdate)) {
            return false;
        }
        */
        if (bookToUpdate.getId() == 0) {
            return updateBook(bookToUpdate);
        } else  {
            Book isBookInDatabase = getBook(bookToUpdate.getId());
            if (isBookInDatabase == null) {
                return false;
            }
            return updateBook(bookToUpdate);
        }
    }

    /**
     * For changing a book's values in the database, the book's non-null values are expected to be
     * not null and contain some information.
     * @param book book object to be compared
     * @return true if non-nulls are valid, false if they are not.
     */
    private boolean areNullFieldsValid(Book book) {
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

        if (book.getIsbn().length() != 10) {
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
