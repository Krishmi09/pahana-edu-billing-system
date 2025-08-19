package com.pahana.edu.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.pahana.edu.model.*;

public class BillDAO {
	 private Connection getConnection() throws Exception {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        return DriverManager.getConnection("jdbc:mysql://localhost:3306/pahana_edu", "root", "");
	    }

	    public int saveBill(Bill bill) throws Exception {
	        int billId = 0;
	        Connection conn = getConnection();
	        conn.setAutoCommit(false);
	        try {
	            PreparedStatement ps = conn.prepareStatement(
	                "INSERT INTO bills (customer_name, customer_contact, payment_method, total_amount, paid_amount, balance) VALUES (?, ?, ?, ?, ?, ?)",
	                Statement.RETURN_GENERATED_KEYS
	            );
	            ps.setString(1, bill.getCustomerName());
	            ps.setString(2, bill.getCustomerContact());
	            ps.setString(3, bill.getPaymentMethod());
	            ps.setDouble(4, bill.getTotalAmount());
	            ps.setDouble(5, bill.getPaidAmount());
	            ps.setDouble(6, bill.getBalance());
	            ps.executeUpdate();
	            ResultSet keys = ps.getGeneratedKeys();
	            if (keys.next()) billId = keys.getInt(1);
	            PreparedStatement itemStmt = conn.prepareStatement(
	                "INSERT INTO bill_items (bill_id, item_name, quantity, price, total) VALUES (?, ?, ?, ?, ?)"
	            );
	            for (BillItem item : bill.getItems()) {
	                itemStmt.setInt(1, billId);
	                itemStmt.setString(2, item.getName());
	                itemStmt.setInt(3, item.getQuantity());
	                itemStmt.setDouble(4, item.getPrice());
	                itemStmt.setDouble(5, item.getTotal());
	                itemStmt.addBatch();
	            }
	            itemStmt.executeBatch();
	            conn.commit();
	        } catch (Exception e) {
	            conn.rollback();
	            throw e;
	        } finally {
	            conn.close();
	        }
	        return billId;
	    }

	    public Bill getBill(int billId) throws Exception {
	        Bill bill = new Bill();
	        Connection conn = getConnection();
	        PreparedStatement ps = conn.prepareStatement("SELECT * FROM bills WHERE id = ?");
	        ps.setInt(1, billId);
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            bill.setId(rs.getInt("id"));
	            bill.setCustomerName(rs.getString("customer_name"));
	            bill.setCustomerContact(rs.getString("customer_contact"));
	            bill.setPaymentMethod(rs.getString("payment_method"));
	            bill.setTotalAmount(rs.getDouble("total_amount"));
	            bill.setPaidAmount(rs.getDouble("paid_amount"));
	            bill.setBalance(rs.getDouble("balance"));
	            bill.setBillDate(rs.getString("bill_date"));
	        }
	        rs.close(); ps.close();
	        ps = conn.prepareStatement("SELECT * FROM bill_items WHERE bill_id = ?");
	        ps.setInt(1, billId);
	        rs = ps.executeQuery();
	        List<BillItem> items = new ArrayList<>();
	        while (rs.next()) {
	            BillItem item = new BillItem();
	            item.setName(rs.getString("item_name"));
	            item.setPrice(rs.getDouble("price"));
	            item.setQuantity(rs.getInt("quantity"));
	            item.setTotal(rs.getDouble("total"));
	            items.add(item);
	        }
	        bill.setItems(items);
	        rs.close(); ps.close(); conn.close();
	        return bill;
	    }
}
