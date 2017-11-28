package com.nextBook.servlet;import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
@WebServlet(
    name = "UserInformation", 
    urlPatterns = "/userInfo")
public class UserInformationServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
        String url = "/next_book/jsp/user_info.jsp";

        RequestDispatcher dispatcher =
            getServletContext().getRequestDispatcher(url);

        dispatcher.forward(request, response);
    }
        
}