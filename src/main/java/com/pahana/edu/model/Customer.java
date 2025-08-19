package com.pahana.edu.model;

public class Customer {
	 private int id;
	    private String name;
	    private String email;
	    private String phone;
	    private String address;

	    // Constructor for adding new customer
	    public Customer(String name, String email, String phone, String address) {
	        this.name = name;
	        this.email = email;
	        this.phone = phone;
	        this.address = address;
	    }

	    // Constructor for updating
	    public Customer(int id, String name, String email, String phone, String address) {
	        this.id = id;
	        this.name = name;
	        this.email = email;
	        this.phone = phone;
	        this.address = address;
	    }

	    // Getters & Setters
	    public int getId() { return id; }
	    public void setId(int id) { this.id = id; }

	    public String getName() { return name; }
	    public void setName(String name) { this.name = name; }

	    public String getEmail() { return email; }
	    public void setEmail(String email) { this.email = email; }

	    public String getPhone() { return phone; }
	    public void setPhone(String phone) { this.phone = phone; }

	    public String getAddress() { return address; }
	    public void setAddress(String address) { this.address = address; }
	}
