package com.nextBook.database.persistence;

import com.nextBook.database.entity.Users;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserDAOTest {

    private UserDAO userDAO;
    @Before
    public void setUpTest() throws Exception {
        userDAO = new UserDAO();
    }
    @Test
    public void addUser() throws Exception {
        Users user = new Users(1000, "TheBeebs", "thebeebs", "beebs@hotmail.com");
        int userId = userDAO.addUser(user);

        assertTrue("The user id should be greater than 0", userId >= 0);
    }



}