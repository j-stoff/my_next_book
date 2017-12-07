package com.nextBook.servlet;

import com.nextBook.database.entity.Author;
import com.nextBook.database.entity.Book;
import com.nextBook.database.persistence.AuthorDAO;
import com.nextBook.database.persistence.BookDAO;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "searchResults", urlPatterns = "/next_book/results")
public class SearchResults extends HttpServlet {

    private final Logger log = Logger.getLogger(this.getClass());

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String selection = request.getParameter("selectCriteria");
        String query = request.getParameter("query");

        List<Book> results = null;
        BookDAO bookDao = new BookDAO();


        if (query != null && !query.isEmpty()) {
            if (selection.equals("title")) {
                results = bookDao.searchBasedOnTitle(query);
            }
            if (selection.equals("author")) {
                AuthorDAO authorDAO = new AuthorDAO();
                List<Author> authorList = authorDAO.searchBasedOnAuthorLastName(query);
                if (authorList.isEmpty()) {
                    authorList = authorDAO.searchBasedOnAuthorFirstName(query);
                }
                results = bookDao.searchBasedOnListOfAuthors(authorList);
            }
        }


        HttpSession session = request.getSession();

        session.setAttribute("bookList", results);

        String url = "/next_book/jsp/results.jsp";

        RequestDispatcher dispatcher =
                getServletContext().getRequestDispatcher(url);

        dispatcher.forward(request, response);
    }


}