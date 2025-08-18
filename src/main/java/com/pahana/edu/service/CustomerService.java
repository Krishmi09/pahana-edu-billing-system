package com.pahana.edu.service;

import com.pahana.edu.dao.CustomerDAO;
import com.pahana.edu.model.Customer;
import java.util.List;


public class CustomerService {
	 private CustomerDAO dao = new CustomerDAO();

	    public void addCustomer(Customer c) {
	        dao.insert(c);
	    }

	    public void updateCustomer(Customer c) {
	        dao.update(c);
	    }

	    public void deleteCustomer(int id) {
	        dao.delete(id);
	    }

	    public List<Customer> getAllCustomers() {
	        return dao.getAll();
	    }

}
