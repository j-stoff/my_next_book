package com.nextBook.database.persistence;

import org.apache.log4j.Logger;
import com.nextBook.database.entity.Users;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserDAO {
    private final Logger log = Logger.getLogger(this.getClass());

    public UserDAO() {
    }

    public int addUser(Users user) {
        int userId = 0;
        Session databaseSession = null;
        Transaction currentTranaction = null;

        try {
            databaseSession = SessionFactoryProvider.getSessionFactory().openSession();
            currentTranaction = databaseSession.beginTransaction();
            userId = (Integer) databaseSession.save(user);
            currentTranaction.commit();
        } catch (HibernateException hibExcept) {
            String message = "Hibernate exception in addUser";
            rollbackTransaction(currentTranaction, message, hibExcept);
        } catch (Exception except) {
            String message = "Exception in addUser";
            rollbackTransaction(currentTranaction, message, except);
        } finally {
            closeSession(databaseSession, "addUser");
        }


        return userId;
    }


    public Users getUser(int userId) {
        Users userInDatabase = null;
        Session databaseSession = null;

        try {
            databaseSession = SessionFactoryProvider.getSessionFactory().openSession();
            userInDatabase = (Users) databaseSession.get(Users.class, userId);
        } catch (HibernateException hibExcept) {
            log.error("Hibernate Error when getting a user from the database.", hibExcept);
        } catch (Exception except) {
            log.error("Exception in getUser.", except);
        } finally {
            closeSession(databaseSession, "getUser");
        }

        return userInDatabase;
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


    private void closeSession(Session session, String location) {
        try {
            if (session != null) {
                session.close();
            }
        } catch (Exception except) {
            log.error("Problem closing the database session in " + location);
        }
    }
}
