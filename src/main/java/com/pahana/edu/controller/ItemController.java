package com.pahana.edu.controller;
import com.pahana.edu.model.Item;
import com.pahana.edu.service.ItemService;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/ItemController")
public class ItemController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ItemService itemService = new ItemService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Item> items = itemService.getAllItems();
        req.setAttribute("items", items);
        req.getRequestDispatcher("items.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        boolean result = false;

        try {
            if ("add".equals(action)) {
                String name = req.getParameter("name");
                String category = req.getParameter("category");
                double price = Double.parseDouble(req.getParameter("price"));
                int quantity = Integer.parseInt(req.getParameter("quantity"));

                Item i = new Item(name, category, price, quantity);
                result = itemService.addItem(i);
                resp.sendRedirect("ItemController?success=" + (result ? "1" : "0"));

            } else if ("update".equals(action)) {
                int id = Integer.parseInt(req.getParameter("id"));
                String name = req.getParameter("name");
                String category = req.getParameter("category");
                double price = Double.parseDouble(req.getParameter("price"));
                int quantity = Integer.parseInt(req.getParameter("quantity"));

                Item i = new Item(id, name, category, price, quantity);
                result = itemService.updateItem(i);
                resp.sendRedirect("ItemController?success=" + (result ? "2" : "0"));

            } else if ("delete".equals(action)) {
                int id = Integer.parseInt(req.getParameter("id"));
                result = itemService.deleteItem(id);
                resp.sendRedirect("ItemController?success=" + (result ? "3" : "0"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("ItemController?success=0");
        }
    }
}
