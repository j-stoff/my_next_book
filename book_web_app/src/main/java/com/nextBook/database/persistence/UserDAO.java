package com.nextBook.database.persistence;

import com.nextBook.database.entity.User_roles;
import org.apache.log4j.Logger;
import com.nextBook.database.entity.Users;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;


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

    public int addUserByRole(User_roles role) {
        int roleId = 0;
        Session databaseSession = null;
        Transaction currentTransction = null;

        try {
            databaseSession = SessionFactoryProvider.getSessionFactory().openSession();
            currentTransction = databaseSession.beginTransaction();
            roleId = (Integer) databaseSession.save(role);
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

        return roleId;
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

    public Users getUserByRole(int roleId) {
        User_roles role = getRole(roleId);
        if (role == null) {
            return null;
        }

        return role.getUser();
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

    public Users getUserByUserName(String userName) {
        List<Users> results = new ArrayList<Users>();
        Users userInDatabase = null;
        Session databaseSession = null;

        try {
            databaseSession = SessionFactoryProvider.getSessionFactory().openSession();
            // query based on criteria
            Criteria criteria = databaseSession.createCriteria(Users.class);
            criteria.add(Restrictions.eq("user_name", userName));
            results = criteria.list();
            userInDatabase = results.get(0);
        } catch (HibernateException hibExcept) {
            log.error("Hibernate exception when retrieving user", hibExcept);
        } catch (Exception except) {
            log.error("Esxception when retrieving user", except);
        } finally {
            closeSession(databaseSession, "getUserByUserName");
        }

        return userInDatabase;
    }

    public int deleteUser(Users user) {
        int userId = 0;
        Session databaseSession = null;
        Transaction currentTransaction = null;

        try {
            databaseSession = SessionFactoryProvider.getSessionFactory().openSession();
            currentTransaction = databaseSession.beginTransaction();
            databaseSession.delete(user);
            currentTransaction.commit();
            userId = user.getId();
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


    public List<Users> getAllUsersExceptAdmin() {
        List<Users> userList = null;
        List<User_roles> roleList = null;
        Session databaseSession = null;

        try {
            databaseSession = SessionFactoryProvider.getSessionFactory().openSession();
            Criteria criteria = databaseSession.createCriteria(User_roles.class);
            criteria.add(Restrictions.ne("role_type", "administrator"));
            roleList = criteria.list();
        } catch (HibernateException hibExcept) {
            log.error("Hibernate Exception in getAllUsersExceptAdmin");
        } catch (Exception except) {
            log.error("Exception in getAllusersExceptAdmin");
        } finally {
            closeSession(databaseSession, "getAllUsersExceptAdmin");
        }

        if (!roleList.isEmpty()) {
            userList = new ArrayList<>();
            for (User_roles role :
                    roleList) {
                userList.add(role.getUser());
            }
        }

        return userList;
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
