package com.pahana.edu.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;

@WebServlet(name = "AddCustomerServle", urlPatterns = { "/AddCustomerServle" })
public class AddCustomerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        // Get parameters from form
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        String address = req.getParameter("address");

        // Basic server-side validation
        if (name == null || email == null || phone == null || address == null
                || name.trim().isEmpty() || email.trim().isEmpty() || phone.trim().isEmpty()) {
            res.sendRedirect("customer.jsp?success=0");
            return;
        }

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // DB connection (change username/password as needed)
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/pahana_edu", "root", "");

            // SQL insert
            String sql = "INSERT INTO customers (name, email, phone, address) VALUES (?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, phone);
            stmt.setString(4, address);

            // Execute
            stmt.executeUpdate();

            // Redirect with success flag
            res.sendRedirect("customer.jsp?success=1");

        } catch (Exception e) {
            e.printStackTrace();
            res.sendRedirect("customer.jsp?success=0");
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
