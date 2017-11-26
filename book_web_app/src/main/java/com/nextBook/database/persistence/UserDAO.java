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

    public int deleteUser(Users user) {
        int userId = user.getId();
        Session databaseSession = null;
        Transaction currentTransaction = null;

        try {
            databaseSession = SessionFactoryProvider.getSessionFactory().openSession();
            currentTransaction = databaseSession.beginTransaction();
            databaseSession.delete(user);
            currentTransaction.commit();
        } catch (HibernateException hibExcept) {
            String message = "Hibernate exception in deleteUser";
            rollbackTransaction(currentTransaction, message, hibExcept);
        } catch (Exception except) {
            String message = "Exception in deleteUser";
            rollbackTransaction(currentTransaction, message, except);
        } finally {
            closeSession(databaseSession, "deleteUser");
        }

        return userId;
    }


    public boolean updateUser(Users user) {
        boolean didUpdate = false;
        Session databaseSession = null;
        Transaction currentTransction = null;

        try {
            databaseSession = SessionFactoryProvider.getSessionFactory().openSession();
            currentTransction = databaseSession.beginTransaction();
            databaseSession.saveOrUpdate(user);
            currentTransction.commit();
            didUpdate = true;
        } catch (HibernateException hibExcept) {
            String message = "Hibernate exception in updateUser";
            rollbackTransaction(currentTransction, message, hibExcept);
        } catch (Exception exception) {
            String message = "Exception caught in updateUser";
            rollbackTransaction(currentTransction, message, exception);
        } finally {
            closeSession(databaseSession, "updateUser");
        }

        return didUpdate;
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
