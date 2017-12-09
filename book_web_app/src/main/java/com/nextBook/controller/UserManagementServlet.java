package com.nextBook.controller;

import com.nextBook.database.entity.Users;
import com.nextBook.database.persistence.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
@WebServlet(
    name = "UserManagement", 
    urlPatterns = "/admin/userManagement")
public class UserManagementServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {

        UserDAO userDAO = new UserDAO();

        List<Users> userList = userDAO.getAllUsersExceptAdmin();

        request.setAttribute("userList", userList);


        String url = "/next_book/admin/user-management.jsp";

        RequestDispatcher dispatcher =
            getServletContext().getRequestDispatcher(url);

        dispatcher.forward(request, response);
    }
        
}