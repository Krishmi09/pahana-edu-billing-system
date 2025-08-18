package com.pahana.edu.servlet;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;
import com.pahana.edu.dao.UserDAO;
/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        UserDAO dao = new UserDAO();
        boolean valid = dao.validate(username, password);

        if (valid) {
            HttpSession session = req.getSession();
            session.setAttribute("username", username);
            res.sendRedirect("dashboard.jsp");
        } else {
            res.sendRedirect("login.jsp?error=true");
        }
    }
}

