package com.pahana.edu.model;

public class BillItem {
	 private int id;
	    private int billId;
	    private String name;
	    private double price;
	    private int quantity;
	    private double total;

	    public int getId() { return id; }
	    public void setId(int id) { this.id = id; }
	    public int getBillId() { return billId; }
	    public void setBillId(int billId) { this.billId = billId; }
	    public String getName() { return name; }
	    public void setName(String name) { this.name = name; }
	    public double getPrice() { return price; }
	    public void setPrice(double price) { this.price = price; }
	    public int getQuantity() { return quantity; }
	    public void setQuantity(int quantity) { this.quantity = quantity; }
	    public double getTotal() { return total; }
	    public void setTotal(double total) { this.total = total; }
}
