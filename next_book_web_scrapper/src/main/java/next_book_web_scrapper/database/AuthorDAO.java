package next_book_web_scrapper.database;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;


import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

public class AuthorDAO {

    private final Logger log = Logger.getLogger(this.getClass());

    /// Not null fields
    /// first name
    /// last name
    /// avg rating

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
        if (author.getAverageRating() <= 0) {
            return -1;
        }

        if (author.getFirstName() == null
                || author.getFirstName().isEmpty()) {
            return -1;
        }

        if (author.getLastName() == null
                || author.getLastName().isEmpty()) {
            return -1;
        }

        int author_id = 0;
        Session databaseSession = null;
        Transaction currentTransaction = null;

        try {
            databaseSession = SessionFactoryProvider.getSessionFactory().openSession();
            currentTransaction = databaseSession.beginTransaction();
            author_id = (int) databaseSession.save(author);
            currentTransaction.commit();
            log.info("END OF TRANSACTION");
        } catch (HibernateException hibernateException) {
            if (currentTransaction != null) {
                try {
                    currentTransaction.rollback();
                } catch (Exception hibernateExceptionWithException) {
                    log.error("Hibernate exception with exception in addAuthor(), rollback error", hibernateExceptionWithException);
                }
                log.error("Hibernate Exception in addAuthor(), rollback committed", hibernateException);
            }
            log.error("Hibernate Exception in addAuthor(), rollback not needed", hibernateException);
        } catch (Exception exception) {
            if (currentTransaction != null) {
                try {
                    currentTransaction.rollback();
                } catch (Exception exceptionWithException) {
                    log.error("Exception with Exception in addAuthor(), rollback error", exceptionWithException);
                }
                log.error("Exception in addAuthor(), rollback committed", exception);
            }
            log.error("Exception in addAuthor, rollback not needed", exception);
        }

        return author_id;
    }

    private boolean checkNullFields(Author author) {
        return false;
    }
}
