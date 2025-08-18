package com.pahana.edu.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;


@WebServlet(name = "EditItemServlet", urlPatterns = { "/EditItemServlet" })
public class EditItemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String category = request.getParameter("category");
        String price = request.getParameter("price");
        String quantity = request.getParameter("quantity");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pahana_edu", "root", "");

            String sql = "UPDATE items SET name=?, category=?, price=?, quantity=? WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, category);
            stmt.setString(3, price);
            stmt.setString(4, quantity);
            stmt.setString(5, id);

            int updated = stmt.executeUpdate();
            if (updated > 0) {
                response.sendRedirect("items.jsp?success=2");
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
