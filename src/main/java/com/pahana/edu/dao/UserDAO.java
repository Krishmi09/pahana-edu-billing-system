package com.pahana.edu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.pahana.edu.util.ConnectionFactory;

public class UserDAO {
	   public boolean validate(String username, String password) {
	        boolean isValid = false;

	        try {
	            Connection conn = ConnectionFactory.getConnection();
	            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
	            PreparedStatement stmt = conn.prepareStatement(sql);
	            stmt.setString(1, username);
	            stmt.setString(2, password);

	            ResultSet rs = stmt.executeQuery();
	            if (rs.next()) {
	                isValid = true; 
	            }

	            conn.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        return isValid;
	    }
}
