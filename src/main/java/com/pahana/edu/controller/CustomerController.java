package com.pahana.edu.controller;

import com.pahana.edu.model.Customer;
import com.pahana.edu.service.CustomerService;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/CustomerController")
public class CustomerController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CustomerService customerService = new CustomerService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Load all customers and forward to JSP
        List<Customer> customers = customerService.getAllCustomers();
        req.setAttribute("customers", customers);
        req.getRequestDispatcher("customer.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        boolean result = false;

        try {
            if ("add".equals(action)) {
                String name = req.getParameter("name");
                String email = req.getParameter("email");
                String phone = req.getParameter("phone");
                String address = req.getParameter("address");

                Customer c = new Customer(name, email, phone, address);
                result = customerService.addCustomer(c);
                resp.sendRedirect("CustomerController?success=" + (result ? "1" : "0"));

            } else if ("update".equals(action)) {
                int id = Integer.parseInt(req.getParameter("id"));
                String name = req.getParameter("name");
                String email = req.getParameter("email");
                String phone = req.getParameter("phone");
                String address = req.getParameter("address");

                Customer c = new Customer(id, name, email, phone, address);
                result = customerService.updateCustomer(c);
                resp.sendRedirect("CustomerController?success=" + (result ? "2" : "0"));

            } else if ("delete".equals(action)) {
                int id = Integer.parseInt(req.getParameter("id"));
                result = customerService.deleteCustomer(id);
                resp.sendRedirect("CustomerController?success=" + (result ? "3" : "0"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("CustomerController?success=0");
        }
    }
}
