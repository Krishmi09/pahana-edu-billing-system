package com.pahana.edu.servlet;

import java.io.IOException;

import com.google.gson.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.io.PrintWriter;


@WebServlet("/DashboardCountsServlet")
public class DashboardCountsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        JsonObject json = new JsonObject();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pahana_edu", "root", "");

            // Total Bills
            PreparedStatement ps1 = conn.prepareStatement("SELECT COUNT(*) AS count FROM bills");
            ResultSet rs1 = ps1.executeQuery();
            if (rs1.next()) json.addProperty("bills", rs1.getInt("count"));
            rs1.close();
            ps1.close();

            // Total Customers
            PreparedStatement ps2 = conn.prepareStatement("SELECT COUNT(*) AS count FROM customers");
            ResultSet rs2 = ps2.executeQuery();
            if (rs2.next()) json.addProperty("customers", rs2.getInt("count"));
            rs2.close();
            ps2.close();

            // Total Items
            PreparedStatement ps3 = conn.prepareStatement("SELECT COUNT(*) AS count FROM items");
            ResultSet rs3 = ps3.executeQuery();
            if (rs3.next()) json.addProperty("items", rs3.getInt("count"));
            rs3.close();
            ps3.close();

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        out.print(json.toString());
        out.flush();
    }

}
