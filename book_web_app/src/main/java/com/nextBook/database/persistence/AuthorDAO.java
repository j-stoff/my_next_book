package com.nextBook.database.persistence;

import com.nextBook.database.entity.Author;
import org.apache.log4j.Logger;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;

public class AuthorDAO {

    private final Logger log = Logger.getLogger(this.getClass());

    /**
     * No argument constructor
     */
    public AuthorDAO() {
    }

    /**
     * Add an author to the database. Assumes first name, last name,
     * and average book rating fields are filled in or -1 is returned.
     *
     *
     * @param author is the author to be added to the database
     * @return the id of the added author, -1 for error, and 0 for
     *      bad add to database.
     */
    public int addAuthor(Author author) {
        int author_id = 0;
        Session databaseSession = null;
        Transaction currentTransaction = null;

        try {
            databaseSession = SessionFactoryProvider.getSessionFactory().openSession();
            currentTransaction = databaseSession.beginTransaction();
            author_id = (Integer) databaseSession.save(author);
            currentTransaction.commit();
        } catch (HibernateException hibernateException) {
            String message = "Hibernate Exception in addAuthor";
            rollbackTransaction(currentTransaction, message, hibernateException);
        } catch (Exception exception) {
            String message = "Exception in addAuthor";
            rollbackTransaction(currentTransaction, message, exception);
        } finally {
            closeSession(databaseSession, "addAuthor");
        }

        return author_id;
    }

    /**
     * Retrieve information about an author using an id's database id.
     * @param author_id author id from the database.
     * @return author class with all information from the database.
     */
    public Author getAuthor(int author_id) {
        Author retrievedAuthor = null;
        Session databaseSession = null;

        try {
            databaseSession = SessionFactoryProvider.getSessionFactory().openSession();
            retrievedAuthor = (Author) databaseSession.get(Author.class, author_id);
        } catch (HibernateException hibernateException) {
            log.error("Hibernate Exception in getAuthor()", hibernateException);
        } catch (Exception exception) {
            log.error("Exception in getAuthor()", exception);
        } finally {
            closeSession(databaseSession, "getAuthor");
        }

        return retrievedAuthor;
    }



    public boolean isAuthorInDatabaseQuery(Author author) {
        String sql = "SELECT id_author FROM authors WHERE author_first_name='" + author.getFirstName() + "' AND author_last_name='" + author.getLastName() + "'";

        Query query = null;
        Session databaseSession = null;
        List results = null;
        try {
            databaseSession = SessionFactoryProvider.getSessionFactory().openSession();
            query = databaseSession.createSQLQuery(sql);
            results = query.list();
            if (results != null) {
                if (!results.isEmpty()) {
                    return true;
                } else {
                    return false;
                }
            }

        } catch (HibernateException hibernateException) {
            log.error("Hibernate error in databaseQuery", hibernateException);
        } catch (Exception exception) {
            log.error("Exception in databaseQuery", exception);
        } finally {
            closeSession(databaseSession, "isAuthorInDatabaseQuery");
        }

        return false;
    }



    public int findAuthorByClass(Author author) {
        String sql = "SELECT * FROM authors WHERE author_first_name='" + author.getFirstName() + "' AND author_last_name='" + author.getLastName() + "'";

        SQLQuery query = null;
        Session databaseSession = null;
        List results = null;
        Transaction currentTransaction = null;
        try {
            databaseSession = SessionFactoryProvider.getSessionFactory().openSession();
            currentTransaction = databaseSession.beginTransaction();
            query = databaseSession.createSQLQuery(sql);
            query.addEntity(Author.class);
            results = query.list();
            if (results != null) {
                Author returnedAuthor = (Author) results.get(0);
                return returnedAuthor.getId();
            }
            currentTransaction.commit();

        } catch (HibernateException hibernateException) {
            log.error("Hibernate error in databaseQuery", hibernateException);
        } catch (Exception exception) {
            log.error("Exception in databaseQuery", exception);
        } finally {
            closeSession(databaseSession, "findAuthorByClass");
        }
        return 0;
    }


    /**
     * Delete an author based on a class. Mostly testing purposes. Assumes non-null author.
     * @param author the author class to be deleted.
     * @return the author's id on success, 0 if the author is not in the database, -1 for error.
     */
    public int deleteAuthor(Author author) {
        int db_author_id = 0;

        Session databaseSession = null;
        Transaction currentTransaction = null;

        try {
            databaseSession = SessionFactoryProvider.getSessionFactory().openSession();
            currentTransaction = databaseSession.beginTransaction();
            databaseSession.delete(author);
            currentTransaction.commit();
            db_author_id = author.getId();
        } catch (HibernateException hibernateException) {
            String message = "Hibernate exception within deleteAuthor";
            rollbackTransaction(currentTransaction, message, hibernateException);
        } catch (Exception exception) {
            String message = "Exception within deleteAuthor";
            rollbackTransaction(currentTransaction,message, exception);
        } finally {
            closeSession(databaseSession, "deleteAuthor");
        }

        return db_author_id;
    }

    /**
     * Delete an author safely from the database. Uses getAuthor to retrieve an author if
     * one exists.
     * @param author the record to be deleted
     * @return -1 for an error, 0 if the author is not in the database.
     */
    public int safeDeleteAuthor(Author author) {
        if (author == null) {
            return -1;
        }
        Author isAuthorInDatabase = getAuthor(author.getId());
        if (isAuthorInDatabase == null) {
            return 0;
        }
        return  deleteAuthor(author);
    }

    /**
     * Safely update author if it exists within the database. Non-null fields must be
     * valid.
     * @param author the author to update.
     * @return false on failure, true for success.
     */
    public boolean safeUpdateAuthor(Author author) {
        if (author == null) {
            return false;
        }

        if (!areNullFieldsValid(author)) {
            return false;
        }

        if (author.getId() == 0) {
            return updateAuthor(author);
        } else {
            Author isAuthorInDatabase = getAuthor(author.getId());
            if (isAuthorInDatabase == null) {
                return false;
            }

            return updateAuthor(author);
        }
    }

    /**
     * Wrapper method that will call other methods in order to safely add objects to the database.
     * Calls get in order to verify no object exists in database.
     * @param author to be added to the database.
     * @return new author id on success, 0 if the author is already in the database, and -1 for an error.
     */
    public int safeAddAuthor(Author author) {
        if (author == null) {
            return -1;
        }

        if (!areNullFieldsValid(author)) {
            return -1;
        }

        if (author.getId() == 0) {
            if (isAuthorInDatabaseQuery(author)) {
                return 0;
            } else {
                return addAuthor(author);
            }
        } else {
            Author isAuthorInDatabase = getAuthor(author.getId());
            if (isAuthorInDatabase != null) {
                return 0;
            }
            return addAuthor(author);
        }
    }



    /**
     * Update an author's information in the database using the author class.
     * Error detection done on the author class.
     * @param author the full author class.
     * @return true if updates commited to database, or false if errors were found.
     */
    public boolean updateAuthor(Author author) {
        boolean didUpdate = false;
        Session databaseSession = null;
        Transaction currentTransaction = null;
        try {
            databaseSession = SessionFactoryProvider.getSessionFactory().openSession();
            currentTransaction = databaseSession.beginTransaction();
            databaseSession.update(author);
            currentTransaction.commit();
            didUpdate = true;
        } catch (HibernateException hibernateException) {
            String message = "Hibernate Exception in updateAuthor";
            rollbackTransaction(currentTransaction, message, hibernateException);
        } catch (Exception exception) {
            String message = "Exception in updateAuthor";
            rollbackTransaction(currentTransaction, message, exception);
        } finally {
            closeSession(databaseSession, "updateAuthor");
        }

        return didUpdate;
    }


    // TODO documentation
    public List<Author> searchBasedOnAuthorFirstName(String firstName) {
        List<Author> results = new ArrayList<>();
        Session databaseSession = null;

        try {
            databaseSession = SessionFactoryProvider.getSessionFactory().openSession();
            Criteria criteria = databaseSession.createCriteria(Author.class);
            criteria.add(Restrictions.ilike("firstName", firstName));
            results = criteria.list();
        } catch (HibernateException hibExcept) {
            log.error("Hibernate Exception in searchBasedOnAuthorFirstName", hibExcept);
        } catch (Exception except) {
            log.error("Exception in searchBasedOnAuthorFirstName", except);
        } finally {
            closeSession(databaseSession, "searchBasedOnAuthorFirstName");
        }

        return results;
    }

    // TODO documentation
    public List<Author> searchBasedOnAuthorLastName(String lastName) {
        List<Author> results = new ArrayList<>();
        Session databaseSession = null;

        try {
            databaseSession = SessionFactoryProvider.getSessionFactory().openSession();
            Criteria criteria = databaseSession.createCriteria(Author.class);
            criteria.add(Restrictions.ilike("lastName", lastName));
            results = criteria.list();
        } catch (HibernateException hibExcept) {
            log.error("Hibernate Exception in searchBasedOnAuthorFirstName", hibExcept);
        } catch (Exception except) {
            log.error("Exception in searchBasedOnAuthorFirstName", except);
        } finally {
            closeSession(databaseSession, "searchBasedOnAuthorFirstName");
        }

        return results;
    }

    /**
     * Check for fields that cannot be null in the database. If these fields are null or empty,
     * false is returned. Otherwise true is returned. Use inverse for checking input author objects.
     * @param author the author object to check
     * @return true if null fields are fine, false if they are not.
     */
    private boolean areNullFieldsValid(Author author) {
        if (author.getAverageRating() < 0) {
            return false;
        }

        if (author.getFirstName() == null
                || author.getFirstName().isEmpty()) {
            return false;
        }

        if (author.getLastName() == null
                || author.getLastName().isEmpty()) {
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
