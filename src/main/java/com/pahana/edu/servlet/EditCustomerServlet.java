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


@WebServlet(name = "EditCustomerServlet", urlPatterns = { "/EditCustomerServlet" })
public class EditCustomerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {

	        String id = request.getParameter("id");
	        String name = request.getParameter("name");
	        String email = request.getParameter("email");
	        String phone = request.getParameter("phone");
	        String address = request.getParameter("address");

	        try {
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pahana_edu", "root", "");

	            String sql = "UPDATE customers SET name=?, email=?, phone=?, address=? WHERE id=?";
	            PreparedStatement stmt = conn.prepareStatement(sql);
	            stmt.setString(1, name);
	            stmt.setString(2, email);
	            stmt.setString(3, phone);
	            stmt.setString(4, address);
	            stmt.setString(5, id);

	            int rowsUpdated = stmt.executeUpdate();

	            if (rowsUpdated > 0) {
	                response.sendRedirect("customer.jsp?success=2");
	            } else {
	                response.sendRedirect("customer.jsp?success=0");
	            }

	            conn.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	            response.sendRedirect("customer.jsp?success=0");
	        }
	    }
}
