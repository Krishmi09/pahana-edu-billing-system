package com.pahana.edu.dao;

import com.pahana.edu.model.Customer;
import com.pahana.edu.util.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CustomerDAO {
	  public void insert(Customer c) {
	        String sql = "INSERT INTO customers(name, email, phone, address) VALUES (?, ?, ?, ?)";
	        try (Connection conn = ConnectionFactory.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)) {
	            ps.setString(1, c.getName());
	            ps.setString(2, c.getEmail());
	            ps.setString(3, c.getPhone());
	            ps.setString(4, c.getAddress());
	            ps.executeUpdate();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    public void update(Customer c) {
	        String sql = "UPDATE customers SET name=?, email=?, phone=?, address=? WHERE id=?";
	        try (Connection conn = ConnectionFactory.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)) {
	            ps.setString(1, c.getName());
	            ps.setString(2, c.getEmail());
	            ps.setString(3, c.getPhone());
	            ps.setString(4, c.getAddress());
	            ps.setInt(5, c.getId());
	            ps.executeUpdate();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    public void delete(int id) {
	        String sql = "DELETE FROM customers WHERE id=?";
	        try (Connection conn = ConnectionFactory.getConnection();
	             PreparedStatement ps = conn.prepareStatement(sql)) {
	            ps.setInt(1, id);
	            ps.executeUpdate();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    public List<Customer> getAll() {
	        List<Customer> list = new ArrayList<>();
	        String sql = "SELECT * FROM customers ORDER BY id DESC";
	        try (Connection conn = ConnectionFactory.getConnection();
	             Statement st = conn.createStatement();
	             ResultSet rs = st.executeQuery(sql)) {
	            while (rs.next()) {
	                Customer c = new Customer(
	                        rs.getInt("id"),
	                        rs.getString("name"),
	                        rs.getString("email"),
	                        rs.getString("phone"),
	                        rs.getString("address")
	                );
	                list.add(c);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return list;
	    }

}
