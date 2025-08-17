package com.pahana.edu.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;



@WebServlet(name = "AddItemServle", urlPatterns = { "/AddItemServle" })
public class AddItemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String name = req.getParameter("name");
        String category = req.getParameter("category");
        String price = req.getParameter("price");
        String quantity = req.getParameter("quantity");
        String description = req.getParameter("description");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pahana_edu", "root", "");
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO items (name, category, price, quantity,description) VALUES (?, ?, ?, ?, ?)");
            stmt.setString(1, name);
            stmt.setString(2, category);
            stmt.setDouble(3, Double.parseDouble(price));
            stmt.setInt(4, Integer.parseInt(quantity));
            stmt.setString(5, description);
            stmt.executeUpdate();
            conn.close();
            res.sendRedirect("items.jsp?success=1");
        } catch (Exception e) {
            e.printStackTrace();
            res.sendRedirect("items.jsp?success=0");
        }
    }


}
