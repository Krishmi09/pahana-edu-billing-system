package com.pahana.edu.dao;
import com.pahana.edu.model.Item;
import com.pahana.edu.util.ConnectionFactory;
import java.sql.*;
import java.util.*;


public class ItemDAO {
	public boolean addItem(Item item) {
        String sql = "INSERT INTO items(name, category, price, quantity) VALUES (?, ?, ?, ?)";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, item.getName());
            ps.setString(2, item.getCategory());
            ps.setDouble(3, item.getPrice());
            ps.setInt(4, item.getQuantity());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateItem(Item item) {
        String sql = "UPDATE items SET name=?, category=?, price=?, quantity=? WHERE id=?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, item.getName());
            ps.setString(2, item.getCategory());
            ps.setDouble(3, item.getPrice());
            ps.setInt(4, item.getQuantity());
            ps.setInt(5, item.getId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteItem(int id) {
        String sql = "DELETE FROM items WHERE id=?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Item> getAllItems() {
        List<Item> list = new ArrayList<>();
        String sql = "SELECT * FROM items ORDER BY id DESC";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Item i = new Item(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("category"),
                        rs.getDouble("price"),
                        rs.getInt("quantity"));
                list.add(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
