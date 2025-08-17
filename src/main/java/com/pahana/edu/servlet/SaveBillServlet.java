package com.pahana.edu.servlet;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import org.json.*;

@WebServlet("/SaveBillServlet")
public class SaveBillServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        StringBuilder jsonBuffer = new StringBuilder();
	        BufferedReader reader = request.getReader();
	        String line;
	        while ((line = reader.readLine()) != null) {
	            jsonBuffer.append(line);
	        }

	        try {
	            JSONObject data = new JSONObject(jsonBuffer.toString());
	            String customerName = data.getString("customerName");
	            String customerContact = data.getString("customerContact");
	            String paymentMethod = data.getString("paymentMethod");
	            double totalAmount = data.getDouble("totalAmount");
	            double paidAmount = data.getDouble("paidAmount");
	            double balance = data.getDouble("balance");
	            JSONArray items = data.getJSONArray("cartItems");

	            Class.forName("com.mysql.cj.jdbc.Driver");
	            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pahana_edu", "root", "");

	            conn.setAutoCommit(false); // transaction start

	            PreparedStatement billStmt = conn.prepareStatement(
	                "INSERT INTO bills (customer_name, customer_contact, payment_method, total_amount, paid_amount, balance) VALUES (?, ?, ?, ?, ?, ?)",
	                Statement.RETURN_GENERATED_KEYS
	            );

	            billStmt.setString(1, customerName);
	            billStmt.setString(2, customerContact);
	            billStmt.setString(3, paymentMethod);
	            billStmt.setDouble(4, totalAmount);
	            billStmt.setDouble(5, paidAmount);
	            billStmt.setDouble(6, balance);

	            billStmt.executeUpdate();

	            ResultSet rs = billStmt.getGeneratedKeys();
	            int billId = -1;
	            if (rs.next()) {
	                billId = rs.getInt(1);
	            }

	            PreparedStatement itemStmt = conn.prepareStatement(
	                "INSERT INTO bill_items (bill_id, item_name, item_price, quantity) VALUES (?, ?, ?, ?)"
	            );

	            for (int i = 0; i < items.length(); i++) {
	                JSONObject item = items.getJSONObject(i);
	                itemStmt.setInt(1, billId);
	                itemStmt.setString(2, item.getString("name"));
	                itemStmt.setDouble(3, item.getDouble("price"));
	                itemStmt.setInt(4, item.getInt("qty"));
	                itemStmt.addBatch();
	            }

	            itemStmt.executeBatch();
	            conn.commit();
	            conn.close();

	            response.setContentType("text/plain");
	            response.getWriter().write("Bill saved successfully!");

	        } catch (Exception e) {
	            e.printStackTrace();
	            response.setStatus(500);
	            response.getWriter().write("Error: " + e.getMessage());
	        }
	    }

}
