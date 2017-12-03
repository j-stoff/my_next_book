package com.nextBook.servlet;

import com.nextBook.database.entity.Users;
import com.nextBook.database.persistence.UserDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "MainScreen",
    urlPatterns = "/next_book/main")
public class MainScreen extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getAttribute("currentUser") == null) {
            //TODO
            // Get user object
            String userName = request.getRemoteUser();
            UserDAO userDAO = new UserDAO();
            Users user = userDAO.getUserByUserName(request.getRemoteUser());
            request.getSession().setAttribute("currentUser", user);
        }

        String url = "/next_book/jsp/main.jsp";

        RequestDispatcher dispatcher =
                getServletContext().getRequestDispatcher(url);

        dispatcher.forward(request, response);
    }
}
