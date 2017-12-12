package com.nextBook.controller.databaseEndpoints;

import com.nextBook.database.entity.Users;
import com.nextBook.database.persistence.UserDAO;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.servlet.RequestDispatcher;

@WebServlet(name = "adminDeleteUserEndpoint", urlPatterns = "/admin/adminDeleteUser")
public class AdminDeleteUser extends HttpServlet {

    private final Logger log = Logger.getLogger(this.getClass());

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain");
        UserDAO userDAO = new UserDAO();

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Users user = userDAO.getUser(id);
            int returnId = userDAO.deleteUser(user);

            if (id == returnId) {
                response.getWriter().write(user.getUser_name() + " deleted");
            } else {
                response.getWriter().write("");
            }
        } catch (Exception except) {
            log.error("Exception in adminDeleteUser", except);
        }
    }

}