package next_book_web_scrapper.database;

import next_book_web_scrapper.entity.Author;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

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
            author_id = (int) databaseSession.save(author);
            currentTransaction.commit();
        } catch (HibernateException hibernateException) {
            String message = "Hibernate Exception in addAuthor";
            rollbackTransaction(currentTransaction, message, hibernateException);
        } catch (Exception exception) {
            String message = "Exception in addAuthor";
            rollbackTransaction(currentTransaction, message, exception);
        } finally {
            try {
                if (databaseSession != null) {
                    databaseSession.close();
                }
            } catch (Exception exception) {
                log.error("Problem closing session in addAuthor()", exception);
            }
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
            try {
                if (databaseSession != null) {
                    databaseSession.close();
                }
            } catch (Exception exception) {
                log.error("Problem closing session in getAuthor()", exception);
            }
        }

        return retrievedAuthor;
    }

    /**
     * Delete an author within the database using the database author id.
     * Calls the getAuthor in order to popular an Author class to utilize delete from a session.
     * @param author_id author's id in the database.
     * @return author id on success, 0 if the author is not in the database, -1 for error.
     */
    /*
    public int deleteAuthor(int author_id) {
        int db_author_id;
        Author authorToDelete = getAuthor(author_id);

        if (authorToDelete == null) {
            return 0;
        } else {
            db_author_id = authorToDelete.getId();
        }

        Session databaseSession = null;
        Transaction currentTransaction = null;

        try {
            databaseSession = SessionFactoryProvider.getSessionFactory().openSession();
            currentTransaction = databaseSession.beginTransaction();
            databaseSession.delete(authorToDelete);
            currentTransaction.commit();
        } catch (HibernateException hibernateException) {
            String message = "Hibernate Exception in deleteAuthor";
            rollbackTransaction(currentTransaction, message, hibernateException);
        } catch (Exception exception) {
            String message = "Exception in deleteAuthor";
            rollbackTransaction(currentTransaction, message, exception);
        } finally {
            try {
                if (databaseSession != null) {
                    databaseSession.close();
                }
            } catch (Exception exception) {
                log.error("Problem with closing session in deleteAuthor()", exception);
            }
        }

        return db_author_id;
    }
    */

    /**
     * Delete an author based on a class. Mostly testing purposes. Assumes non-null author.
     * @param author the author class to be deleted.
     * @return the author's id on success, 0 if the author is not in the database, -1 for error.
     */
    public int deleteAuthor(Author author) {
        int db_author_id = author.getId();

        Session databaseSession = null;
        Transaction currentTransaction = null;

        try {
            databaseSession = SessionFactoryProvider.getSessionFactory().openSession();
            currentTransaction = databaseSession.beginTransaction();
            databaseSession.delete(author);
            currentTransaction.commit();
        } catch (HibernateException hibernateException) {
            String message = "Hibernate exception within deleteAuthor";
            rollbackTransaction(currentTransaction, message, hibernateException);
        } catch (Exception exception) {
            String message = "Exception within deleteAuthor";
            rollbackTransaction(currentTransaction,message, exception);
        } finally {
            try {
                if (databaseSession != null) {
                    databaseSession.close();
                }
            } catch (Exception exception) {
                log.error("Problem closing session in deleteAuthor", exception);
            }
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
            return addAuthor(author);
        } else {
            Author isAuthorInDatabase = getAuthor(author.getId());
            if (isAuthorInDatabase != null) {
                return 0;
            }
            return addAuthor(author);
        }
    }



    /**
     * Delete an author safely from the database. Uses getAuthor to retrieve an author if
     * one exists.
     * @param author_id the id of the record to be deleted.
     * @return -1 for error, 0 if author is not in the database. Author Id returned on success.
     */
    /*
    public int safeDeleteAuthor(int author_id) {
        if (author_id <= 0) {
            return -1;
        }
        Author isAuthorInDatabase = getAuthor(author_id);
        if (isAuthorInDatabase != null) {
            return deleteAuthor(author_id);
        }
        return 0;
    }
    */

    /**
     * Update an author's information in the database using the author class.
     * Error detection done on the author class.
     * @param author the full author class.
     * @return true if updates commited to database, or false if errors were found.
     */
    public boolean updateAuthor(Author author) {
        Session databaseSession = null;
        Transaction currentTransaction = null;
        try {
            databaseSession = SessionFactoryProvider.getSessionFactory().openSession();
            currentTransaction = databaseSession.beginTransaction();
            databaseSession.update(author);
            currentTransaction.commit();
        } catch (HibernateException hibernateException) {
            String message = "Hibernate Exception in updateAuthor";
            rollbackTransaction(currentTransaction, message, hibernateException);
        } catch (Exception exception) {
            String message = "Exception in updateAuthor";
            rollbackTransaction(currentTransaction, message, exception);
        } finally {
            try {
                if (databaseSession != null) {
                    databaseSession.close();
                }
            } catch (Exception exception) {
                log.error("Problem with closing session in updateAuthor", exception);
            }
        }

        return true;
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
}
