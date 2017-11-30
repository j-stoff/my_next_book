package com.nextBook.database.persistence;

import com.nextBook.database.entity.UserBooksEntity;
import org.apache.log4j.Logger;
import org.hibernate.Transaction;

public class UserBooksDAO {
    private Logger log = Logger.getLogger(this.getClass());

    public int addUserBook(UserBooksEntity userBook) {
        return 0;
    }

    public int deleteUserBook(UserBooksEntity userBook) {
        return 0;
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
