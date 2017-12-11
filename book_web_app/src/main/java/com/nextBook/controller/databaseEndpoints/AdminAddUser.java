package com.nextBook.controller.databaseEndpoints;import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
@WebServlet(
    name = "adminAddUserEndpoint", 
    urlPatterns = "/admin/adminAddUser")
public class AdminAddUser extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        // TODO add user to database

        response.sendRedirect("/book_app/admin/addUser");



    }
        
}