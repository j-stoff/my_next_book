package com.nextBook.servlet;

import com.nextBook.database.entity.Book;
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

        String bookTitleParameter = request.getParameter("bookTitle");
        String bookIdParameter = request.getParameter("bookId");

        List<Book> results = null;

        BookDAO bookDao = new BookDAO();

        if (bookTitleParameter != null && !bookTitleParameter.isEmpty()) {
            // Search by title
            results = (List<Book>) bookDao.findBookByTitle(bookTitleParameter);
        } else if (bookIdParameter != null && !bookIdParameter.isEmpty()) {
            int requestId = Integer.parseInt(bookIdParameter);
            // Get the associated book and add it to the list
            Book specifiedBook = bookDao.getBook(requestId);

            if (specifiedBook != null) {
                results = new ArrayList<Book>();
                results.add(specifiedBook);
            }
        }



        HttpSession session = request.getSession();

        session.setAttribute("resultList", results);

        String url = "/next_book/jsp/results.jsp";

        RequestDispatcher dispatcher =
                getServletContext().getRequestDispatcher(url);

        dispatcher.forward(request, response);
    }

}