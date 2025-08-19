package com.pahana.edu.service;
import com.pahana.edu.dao.ItemDAO;
import com.pahana.edu.model.Item;
import java.util.List;

public class ItemService {
	 private ItemDAO itemDAO = new ItemDAO();

	    public boolean addItem(Item item) {
	        return itemDAO.addItem(item);
	    }

	    public boolean updateItem(Item item) {
	        return itemDAO.updateItem(item);
	    }

	    public boolean deleteItem(int id) {
	        return itemDAO.deleteItem(id);
	    }

	    public List<Item> getAllItems() {
	        return itemDAO.getAllItems();
	    }

}
