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

        int retrievedId = userDAO.deleteUser(user);

        assertTrue("The deleted id is not the same as the original", user.getId() == retrievedId);

        Users getUser = userDAO.getUser(retrievedId);

        assertNull("The user was found in the database", getUser);
    }

    @Test
    public void updateUserTest() throws Exception {
        Users user = insertSingleUserReturnUser();

        String originalUserName = user.getUser_name();

        user.setUser_name("CptPlanet");

        assertTrue("The update did not work", userDAO.updateUser(user));

        Users updatedUser = userDAO.getUser(user.getId());

        assertNotNull("Get user failed after updating", updatedUser);
        assertTrue("The retrieved user has a different ID", updatedUser.getId() == user.getId());
        assertNotEquals("The user name hasn't changed", originalUserName, updatedUser.getUser_name());

        cleanUp(user);
    }


    private int insertSingleUserReturnInt() {
        Users user = new Users(555, "CptMarvel", "shazam", "immaCaptain@hotmail.com");
        int id = userDAO.addUser(user);
        assertTrue("Test user not inserted correctly", id >= 0);
        return id;
    }

    private Users insertSingleUserReturnUser() {
        Users user = new Users(555, "CptMarvel", "shazam", "immaCaptain@hotmail.com");
        int id = userDAO.addUser(user);
        assertTrue("Test user not inserted correctly", id >= 0);
        user.setUser_id(id);
        return user;
    }

    private void cleanUp(Users userToDelete) {
        int deleteCode = userDAO.deleteUser(userToDelete);

        assertTrue("The clean up deletion from the getUserTest failed", deleteCode >= 0);
    }



}