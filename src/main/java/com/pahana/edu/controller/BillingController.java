package com.pahana.edu.controller;
import com.pahana.edu.model.Bill;
import com.pahana.edu.model.BillItem;
import com.pahana.edu.dao.BillDAO;
import org.json.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/billing")
public class BillingController extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) sb.append(line);
        try {
            JSONObject data = new JSONObject(sb.toString());
            Bill bill = new Bill();
            bill.setCustomerName(data.getString("customerName"));
            bill.setCustomerContact(data.getString("customerContact"));
            bill.setPaymentMethod(data.getString("paymentMethod"));
            bill.setTotalAmount(data.getDouble("totalAmount"));
            bill.setPaidAmount(data.getDouble("paidAmount"));
            bill.setBalance(data.getDouble("balance"));
            List<BillItem> items = new ArrayList<>();
            JSONArray cart = data.getJSONArray("cartItems");
            for (int i = 0; i < cart.length(); i++) {
                JSONObject obj = cart.getJSONObject(i);
                BillItem item = new BillItem();
                item.setName(obj.getString("name"));
                item.setPrice(obj.getDouble("price"));
                item.setQuantity(obj.getInt("qty"));
                item.setTotal(obj.getDouble("price") * obj.getInt("qty"));
                items.add(item);
            }
            bill.setItems(items);
            BillDAO dao = new BillDAO();
            int billId = dao.saveBill(bill);
            response.setContentType("application/json");
            response.getWriter().print("{\"status\":\"success\",\"redirect\":\"invoice_preview.jsp?bill_id=" + billId + "\"}");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(500);
            response.getWriter().print("{\"status\":\"error\",\"message\":\"" + e.getMessage() + "\"}");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int billId = Integer.parseInt(request.getParameter("bill_id"));
        try {
            BillDAO dao = new BillDAO();
            Bill bill = dao.getBill(billId);
            request.setAttribute("bill", bill);
            request.getRequestDispatcher("invoice_preview.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}
