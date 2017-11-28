package com.nextBook.servlet;

import com.nextBook.database.entity.User_roles;
import com.nextBook.database.entity.Users;
import com.nextBook.database.persistence.UserDAO;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebServlet(
    name = "AddNewUser", 
    urlPatterns = "/addNewUser")
public class AddNewUser extends HttpServlet {

    private final Logger log = Logger.getLogger(this.getClass());

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        UserDAO userDAO = new UserDAO();
        Users user = new Users();
        user.setUser_id(99999999);
        String userName = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        user.setUser_name(userName);
        user.setUser_password(password);
        user.setEmail(email);

        User_roles role = new User_roles(user.getUser_name(), "registered-user");
        role.setUser(user);

        user.getRoles().add(role);

        userDAO.addUser(user);

        String url = "/book_app/main";

        response.sendRedirect(url);

    }
        
}