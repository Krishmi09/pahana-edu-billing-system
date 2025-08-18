package com.pahana.edu.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import com.google.gson.Gson;
import java.io.PrintWriter;

@WebServlet(name = "ReportsServlet", urlPatterns = { "/ReportsServlet" })
public class ReportsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        String action = request.getParameter("action");
	        response.setContentType("application/json");
	        PrintWriter out = response.getWriter();

	        try {
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pahana_edu", "root", "");

	            if ("salesData".equals(action)) {
	                Map<String, Integer> salesData = new LinkedHashMap<>();

	                String sql = "SELECT item_name, SUM(quantity) as total_sold FROM bill_items GROUP BY item_name ORDER BY total_sold DESC";
	                PreparedStatement ps = conn.prepareStatement(sql);
	                ResultSet rs = ps.executeQuery();

	                while (rs.next()) {
	                    salesData.put(rs.getString("item_name"), rs.getInt("total_sold"));
	                }

	                out.print(new Gson().toJson(salesData));
	                rs.close();
	                ps.close();
	            } else if ("monthlyInvoices".equals(action)) {
	                String month = request.getParameter("month"); // format: yyyy-MM
	                List<Map<String, String>> invoices = new ArrayList<>();

	                String sql = "SELECT * FROM bills WHERE DATE_FORMAT(created_at, '%Y-%m') = ? ORDER BY created_at DESC";
	                PreparedStatement ps = conn.prepareStatement(sql);
	                ps.setString(1, month);
	                ResultSet rs = ps.executeQuery();

	                while (rs.next()) {
	                    Map<String, String> invoice = new HashMap<>();
	                    invoice.put("id", rs.getString("id"));
	                    invoice.put("customer", rs.getString("customer_name"));
	                    invoice.put("contact", rs.getString("customer_contact"));
	                    invoice.put("method", rs.getString("payment_method"));
	                    invoice.put("total", rs.getString("total_amount"));
	                    invoice.put("paid", rs.getString("paid_amount"));
	                    invoice.put("balance", rs.getString("balance"));
	                    invoice.put("date", rs.getString("created_at"));
	                    invoices.add(invoice);
	                }

	                out.print(new Gson().toJson(invoices));
	                rs.close();
	                ps.close();
	            }

	            conn.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	            response.setStatus(500);
	            out.print("{\"error\":\"Internal Server Error\"}");
	        } finally {
	            out.flush();
	            out.close();

	        }

	    }

}
