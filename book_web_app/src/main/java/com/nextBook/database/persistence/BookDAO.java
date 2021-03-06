package com.nextBook.database.persistence;

import javassist.bytecode.ExceptionTable;
import com.nextBook.database.entity.Book;
import com.nextBook.database.entity.Author;
import org.apache.log4j.Logger;
import org.hibernate.*;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;

import java.util.ArrayList;
import java.util.List;

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
            book_id = (Integer) databaseSession.save(book);
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
            closeSession(databaseSession, "addBook");
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
            closeSession(databaseSession, "getBook");
        }

        return retrievedBook;
    }

    /**
     * Update a book in the database. If the book doesn't exist, it will be created.
     * @param bookUpdated the book data to update.
     * @return boolean if the book update was successful.
     */
    public boolean updateBook(Book bookUpdated) {
        boolean didUpdate = false;
        Session databaseSession = null;
        Transaction currentTransaction = null;

        try {
            databaseSession = SessionFactoryProvider.getSessionFactory().openSession();
            currentTransaction = databaseSession.beginTransaction();
            databaseSession.update(bookUpdated);
            currentTransaction.commit();
            didUpdate = true;
        } catch (HibernateException hibernateException) {
            String message = "Hibernate Exception in updateBook";
            rollbackTransaction(currentTransaction, message, hibernateException);
        } catch (Exception exception) {
            String message = "Exception in updateBook";
            rollbackTransaction(currentTransaction, message, exception);
        } finally {
            closeSession(databaseSession, "updateBook");
        }

        return didUpdate;
    }

    /**
     * Delete a book in the database using a book object.
     * @param book book object to delete.
     * @return the id on success, 0 if the book is not in the database, -1 for an error
     */
    public int deleteBook(Book book) {
        int retrieved_id = 0;
        Session databaseSession = null;
        Transaction currentTransaction = null;

        try {
            databaseSession = SessionFactoryProvider.getSessionFactory().openSession();
            currentTransaction = databaseSession.beginTransaction();
            databaseSession.delete(book);
            currentTransaction.commit();
            retrieved_id = book.getId();
        } catch (HibernateException hibernateException) {
            String message = "Hibernate Exception in deleteBook";
            rollbackTransaction(currentTransaction, message, hibernateException);
        } catch (Exception expcetion) {
            String message = "Exception in deleteBook";
            rollbackTransaction(currentTransaction, message, expcetion);
        } finally {
            closeSession(databaseSession, "deleteBook");
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
     * A method to retrieve a book object from the database based on a
     * title query.
     * @param bookTitle The title of the book object
     * @return a book object with all relevant information or null if not found
     */
    public List<Book> findBookByTitle(String bookTitle) {
        String hql = "FROM Book B WHERE B.title like '" + bookTitle + "'";
        //String hql = "From Book";
        Session databaseSession = null;
        Transaction currentTransaction = null;
        List results = null;

        try {
            databaseSession = SessionFactoryProvider.getSessionFactory().openSession();
            currentTransaction = databaseSession.beginTransaction();
            Query query = databaseSession.createQuery(hql);
            if (query != null) {
                results = query.list();
            }
            currentTransaction.commit();
        } catch (HibernateException hibernateException) {
            String message = "Hibernate Exception in findBookByTitle";
            rollbackTransaction(currentTransaction, message, hibernateException);
        } catch (Exception exceptiion) {
            String message = "Exception in findBookByTitle";
            rollbackTransaction(currentTransaction, message, exceptiion);
        } finally {
            closeSession(databaseSession, "findBookByTitle");
        }

        return results;
    }



    // TODO documentation
    public List<Book> searchBasedOnTitle(String title) {
        List<Book> results = null;
        Session databaseSession = null;

        try {
            databaseSession = SessionFactoryProvider.getSessionFactory().openSession();
            Criteria criteria = databaseSession.createCriteria(Book.class);
            criteria.add(Restrictions.ilike("title", title));
            results = criteria.list();
        } catch (HibernateException hibExcept) {
            log.error("Hibernate exception in searchBasedOnTitle", hibExcept);
        } catch (Exception except) {
            log.error("Exception in searchBasedOnTitle", except);
        } finally {
            closeSession(databaseSession, "searchBasedOnTitle");
        }

        return results;
    }


    // TODO Documentation
    public List<Book> searchBasedOnListOfAuthors(List<Author> list) {
        List<Book> results = null;

        Criterion[] authorCriteria = new Criterion[list.size()];

        Session databaseSession = null;
        try {
            databaseSession = SessionFactoryProvider.getSessionFactory().openSession();
            Criteria criteria = databaseSession.createCriteria(Book.class);
            for (int i = 0; i < list.size(); i++) {
                authorCriteria[i] = Restrictions.eq("fk_id_author", list.get(i));
            }
            Disjunction queryCriteria = Restrictions.disjunction(authorCriteria);
            criteria.add(queryCriteria);
            results = criteria.list();
        } catch (HibernateException hibExcept) {
            log.error("Hibernate Exception in searchBasedOnListOfAuthors", hibExcept);
        } catch (Exception except) {
            log.error("Exception in searchBasedOnListOfAuthors", except);
        } finally {
            closeSession(databaseSession, "searchBasedOnListOfAuthors");
        }

        return results;
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


    private void closeSession(Session session, String locationName) {
        try {
            if (session != null) {
                session.close();
            }
        } catch (Exception except) {
            log.error("Problem closing the database session in " + locationName);
        }
    }
}
