package com.pahana.edu.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;


@WebServlet(name = "DeleteItemServlet", urlPatterns = { "/DeleteItemServlet" })
public class DeleteItemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	 protected void doGet(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {

	        String id = request.getParameter("id");

	        try {
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pahana_edu", "root", "");

	            String sql = "DELETE FROM items WHERE id = ?";
	            PreparedStatement stmt = conn.prepareStatement(sql);
	            stmt.setString(1, id);

	            int rowsDeleted = stmt.executeUpdate();

	            if (rowsDeleted > 0) {
	                response.sendRedirect("items.jsp?success=3");
	            } else {
	                response.sendRedirect("items.jsp?success=0");
	            }

	            conn.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	            response.sendRedirect("items.jsp?success=0");

	        }
	    }

}
