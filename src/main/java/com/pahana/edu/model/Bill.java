package com.pahana.edu.model;

import java.util.List;

public class Bill {
    private int id;
    private String customerName;
    private String customerContact;
    private String paymentMethod;
    private double totalAmount;
    private double paidAmount;
    private double balance;
    private String billDate;
    private List<BillItem> items;

    public int getId() { 
    	return id; 
    	}
    public void setId(int id) { 
    	this.id = id; 
    	}
    public String getCustomerName() {
    	return customerName; 
    	}
    public void setCustomerName(String customerName) { 
    	this.customerName = customerName; 
    	}
    public String getCustomerContact() { 
    	return customerContact; 
    	}
    public void setCustomerContact(String customerContact) { 
    	this.customerContact = customerContact; 
    	}
    public String getPaymentMethod() { 
    	return paymentMethod; 
    	}
    public void setPaymentMethod(String paymentMethod) { 
    	this.paymentMethod = paymentMethod; 
    	}
    public double getTotalAmount() { 
    	return totalAmount; 
    	}
    public void setTotalAmount(double totalAmount) { 
    	this.totalAmount = totalAmount; 
    	}
    public double getPaidAmount() { 
    	return paidAmount; 
    	}
    public void setPaidAmount(double paidAmount) { 
    	this.paidAmount = paidAmount; 
    	}
    public double getBalance() {
    	return balance; 
    	}
    public void setBalance(double balance) { 
    	this.balance = balance; 
    	}
    public String getBillDate() { 
    	return billDate; 
    	}
    public void setBillDate(String billDate) { 
    	this.billDate = billDate;
    	}
    public List<BillItem> getItems() { 
    	return items; 
    	}
    public void setItems(List<BillItem> items) {
    	this.items = items; 
    	}
}
