package com.nextBook.database.persistence;

import com.nextBook.database.entity.User_roles;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.xml.transform.TransformerConfigurationException;

public class User_rolesDAO {

    private final Logger log = Logger.getLogger(this.getClass());

    public User_rolesDAO() {
    }


    public int addUserRole(User_roles role) {
        User_roles roleFromDatabase = null;
        int roleId = 0;
        Session databaseSession = null;
        Transaction currentTransction = null;

        try {
            databaseSession = SessionFactoryProvider.getSessionFactory().openSession();
            currentTransction = databaseSession.beginTransaction();
            roleFromDatabase = (User_roles) databaseSession.save(role);
            currentTransction.commit();
        } catch (HibernateException hibExcept) {
            String message = "Hibernate excepetion in addUserRole";
            rollbackTransaction(currentTransction, message, hibExcept);
        } catch (Exception except) {
            String message = "Exception in addUserRole";
            rollbackTransaction(currentTransction, message, except);
        } finally {
            closeSession(databaseSession, "addUserRole");
        }

        return roleFromDatabase.getId();
    }


    public User_roles getRole(int roleId) {
        User_roles roleFromDatabase = null;
        Session databaseSession = null;

        try {
            databaseSession = SessionFactoryProvider.getSessionFactory().openSession();
            roleFromDatabase = (User_roles) databaseSession.get(User_roles.class, roleId);
        } catch (HibernateException hibExcept) {
            log.error("Hibernate exception in getRole", hibExcept);
        } catch (Exception except) {
            log.error("Exception in getRole", except);
        } finally {
            closeSession(databaseSession, "getRole");
        }

        return roleFromDatabase;
    }

    public int deleteRole(User_roles role) {
        int deletedRole = 0;
        Session databaseSession = null;
        Transaction currentTransction = null;

        try {
            databaseSession = SessionFactoryProvider.getSessionFactory().openSession();
            currentTransction = databaseSession.beginTransaction();
            databaseSession.delete(role);
            currentTransction.commit();
            deletedRole = role.getId();
        } catch (HibernateException hibExcept) {
            String message = "Hibernate Exception in deleteRole";
            rollbackTransaction(currentTransction, message, hibExcept);
        } catch (Exception except) {
            String message = "Exception in deleteRole";
            rollbackTransaction(currentTransction, message, except);
        } finally {
            closeSession(databaseSession, "deleteRole");
        }

        return deletedRole;
    }

    public boolean updateRole(User_roles role) {
        boolean didUpdate = false;
        Session databaseSession = null;
        Transaction currentTransction = null;

        try {
            databaseSession = SessionFactoryProvider.getSessionFactory().openSession();
            currentTransction = databaseSession.beginTransaction();
            databaseSession.saveOrUpdate(role);
            currentTransction.commit();
            didUpdate = true;
        } catch (HibernateException hibExcept) {
            String message = "Hibernate Exception in updateRole";
            rollbackTransaction(currentTransction, message, hibExcept);
        } catch (Exception except) {
            String message = "Exception in updateRole";
            rollbackTransaction(currentTransction, message, except);
        } finally {
            closeSession(databaseSession, "updateRole");
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
