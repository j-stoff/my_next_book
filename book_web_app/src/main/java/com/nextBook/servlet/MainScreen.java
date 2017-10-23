package com.nextBook.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "MainScreen",
    urlPatterns = "/home")
public class MainScreen extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "/next_book/jsp/main.jsp";

        RequestDispatcher dispatcher =
                getServletContext().getRequestDispatcher(url);

        dispatcher.forward(request, response);
    }
}
