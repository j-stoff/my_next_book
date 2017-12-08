package com.nextBook.controller.databaseEndpoints;

import com.nextBook.database.entity.Book;
import com.nextBook.database.entity.Users;
import com.nextBook.database.persistence.BookDAO;
import com.nextBook.database.persistence.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
    name = "AddUserBook", 
    urlPatterns = "/next_book/addUserBook")
public class AddUserBook extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        BookDAO bookDAO = new BookDAO();
        Users currentUser = (Users)request.getSession().getAttribute("currentUser");
        Book bookFromDatabase = bookDAO.getBook(id);

        currentUser.getBooksForUser().add(bookFromDatabase);

        UserDAO userDAO = new UserDAO();

        userDAO.updateUser(currentUser);

        response.getWriter().write("User books updated");
    }
        
}