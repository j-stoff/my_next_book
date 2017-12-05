package com.nextBook.database.persistence;

import com.nextBook.database.entity.Author;
import com.nextBook.database.entity.Book;
import com.nextBook.database.entity.User_roles;
import com.nextBook.database.entity.Users;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class UserDAOTest {

    private UserDAO userDAO;
    private AuthorDAO authorDAO;
    @Before
    public void setUpTest() throws Exception {
        userDAO = new UserDAO();
        authorDAO = new AuthorDAO();
    }
    @Test
    public void addUserTest() throws Exception {
        Users user = new Users(1000, "TheBeebs", "thebeebs", "beebs@hotmail.com");
        int userId = userDAO.addUser(user);

        assertTrue("The user id should be greater than 0", userId > 0);

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
    public void addUserWithRoleTest() throws Exception {
        User_roles role = insertUserWithRole();

        assertNotNull("The role returned was null", role);
        Users user = role.getUser();
        assertNotNull("The user inside of the role is null", user);
        assertEquals("The user names for the retreived role and user are not the same", role.getUser_name(), user.getUser_name());

        cleanUp(user);
    }

    @Test
    public void getRoleTest() throws Exception {
        User_roles role = insertUserWithRole();

        User_roles databaseRole = userDAO.getRole(role.getId());

        assertEquals("The roles are different", databaseRole, role);

        cleanUp(role.getUser());
    }

    @Test
    public void getUserFromRoleTest() throws Exception {
        User_roles role = insertUserWithRole();

        Users databaseUser = userDAO.getUserByRole(role.getId());

        assertEquals("The user from the database matches the one with that role", role.getUser(), databaseUser);

        cleanUp(role.getUser());
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


    @Test
    public void addBookToUserList() throws Exception {
        Users user = new Users(555, "CptMarvel", "shazam", "immaCaptain@hotmail.com");

        Author author =  new Author("Kyle", "Martin", 5.0);
        int authorId = authorDAO.addAuthor(author);
        //Author author = authorDAO.getAuthor(2);
        List<String> genres = new ArrayList<String>();
        genres.add("fantasty");
        genres.add("dark");

        Book book1 = new Book( "Anything really", author.getFirstName(), 5, genres, "1231312312");
        Book book2 = new Book("Anything else", author.getFirstName(), 5, genres, "1234567890");
        book1.setFk_id_author(author);
        book2.setFk_id_author(author);


        user.getBooksForUser().add(book1);
        user.getBooksForUser().add(book2);

        int id = userDAO.addUser(user);

        assertTrue("The user was not added properly", id > 0);

        cleanUp(user);
        assertTrue("The author was not deleted", authorDAO.deleteAuthor(author) > 0);
    }

    @Test
    public void getUserWithUserName() throws Exception {
        Users user = insertSingleUserReturnUser();

        Users returnedUser = userDAO.getUserByUserName(user.getUser_name());

        assertNotNull("The returned user was null", returnedUser);
        assertEquals("The users are not the same", returnedUser, user);

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

    private User_roles insertUserWithRole() {
        Users user = insertSingleUserReturnUser();
        User_roles role = new User_roles(user.getUser_name(), "registered-user");
        role.setUser(user);

        int roleId = userDAO.addUserByRole(role);

        assertTrue("User and role were not inserted correctly", roleId > 0);
        role.setRole_id(roleId);

        return role;
    }



}