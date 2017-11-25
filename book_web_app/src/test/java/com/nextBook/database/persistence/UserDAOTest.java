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
    public void addUserTest() throws Exception {
        Users user = new Users(1000, "TheBeebs", "thebeebs", "beebs@hotmail.com");
        int userId = userDAO.addUser(user);

        assertTrue("The user id should be greater than 0", userId >= 0);

        cleanUp(user);
    }

    @Test
    public void getUserTest() throws Exception {
        int userId = insertSingleUserReturnInt();

        assertTrue("Test user not inserted correctly.", userId >= 0);

        Users retreivedUser = userDAO.getUser(userId);

        assertTrue("The id is not the same as the user inserted", retreivedUser.getId() == userId);
        assertEquals("The name is not the same as the one inserted", retreivedUser.getUser_name(), "CptMarvel");
        assertEquals("The password is different from the one inserted.", retreivedUser.getUser_password(), "shazam");
        assertNotNull("The email of the user is null", retreivedUser.getEmail());
        assertEquals("The email is different from the one inserted", retreivedUser.getEmail(), "immaCaptain@hotmail.com");

        cleanUp(retreivedUser);
    }

    @Test
    public void deleteUserTest() throws Exception {
        Users user = insertSingleUserReturnUser();

        assertTrue("Test user not inserted correctly", user.getId() >= 0);

        int retrievedId = userDAO.deleteUser(user);

        assertTrue("The deleted id is not the same as the original", user.getId() == retrievedId);

        Users getUser = userDAO.getUser(retrievedId);

        assertNull("The user was found in the database", getUser);
    }


    private int insertSingleUserReturnInt() {
        Users user = new Users(555, "CptMarvel", "shazam", "immaCaptain@hotmail.com");

        return userDAO.addUser(user);
    }

    private Users insertSingleUserReturnUser() {
        Users user = new Users(555, "CptMarvel", "shazam", "immaCaptain@hotmail.com");

        userDAO.addUser(user);

        return user;
    }

    private void cleanUp(Users userToDelete) {
        int deleteCode = userDAO.deleteUser(userToDelete);

        assertTrue("The clean up deletion from the getUserTest failed", deleteCode >= 0);
    }



}