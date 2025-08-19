package com.pahana.edu.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.*;
import com.google.gson.Gson;

@WebServlet("/ReportController")
public class ReportController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String month = request.getParameter("month");

        if (action == null) action = "view"; // default

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pahana_edu", "root", "");

            if (action.equals("download")) {
                response.setContentType("application/octet-stream");
                response.setHeader("Content-Disposition", "attachment; filename=invoice_report_" + month + ".txt");

                PreparedStatement ps = conn.prepareStatement("SELECT * FROM bills WHERE DATE_FORMAT(bill_date,'%Y-%m')=?");
                ps.setString(1, month);
                ResultSet rs = ps.executeQuery();
                PrintWriter out = response.getWriter();

                out.println("Invoice Report for " + month);
                out.println("----------------------------------------------------");
                while(rs.next()){
                    out.println("Invoice ID: " + rs.getInt("id"));
                    out.println("Customer: " + rs.getString("customer_name"));
                    out.println("Contact: " + rs.getString("customer_contact"));
                    out.println("Payment: " + rs.getString("payment_method"));
                    out.println("Total: LKR " + rs.getDouble("total_amount"));
                    out.println("Paid: LKR " + rs.getDouble("paid_amount"));
                    out.println("Balance: LKR " + rs.getDouble("balance"));
                    out.println("Date: " + rs.getString("bill_date"));
                    out.println("----------------------------------------------------");
                }
                rs.close(); ps.close(); conn.close();
                out.flush();
                return;

            } else if (action.equals("data")) {
                // Return JSON for charts
                Map<String, Object> result = new HashMap<>();
                List<String> categoryLabels = new ArrayList<>();
                List<Double> categorySales = new ArrayList<>();
                double totalSales = 0, totalPaid = 0, totalBalance = 0;

                String[] categories = {"Books","Stationery","Electronics","Art Supplies"};
                for (String cat : categories) {
                    PreparedStatement ps = conn.prepareStatement(
                        "SELECT SUM(bi.total) as cat_total FROM bill_items bi JOIN bills b ON bi.bill_id=b.id " +
                        "JOIN items i ON i.name=bi.item_name WHERE i.category=? " +
                        (month!=null ? "AND DATE_FORMAT(b.bill_date,'%Y-%m')=?" : "")
                    );
                    ps.setString(1, cat);
                    if(month!=null) ps.setString(2, month);
                    ResultSet rs = ps.executeQuery();
                    if(rs.next()){
                        categoryLabels.add(cat);
                        categorySales.add(rs.getDouble("cat_total"));
                    }
                    rs.close(); ps.close();
                }

                PreparedStatement totalStmt = conn.prepareStatement(
                    "SELECT SUM(total_amount) as total, SUM(paid_amount) as paid, SUM(balance) as balance FROM bills " +
                    (month!=null ? "WHERE DATE_FORMAT(bill_date,'%Y-%m')=?" : "")
                );
                if(month!=null) totalStmt.setString(1, month);
                ResultSet rs2 = totalStmt.executeQuery();
                if(rs2.next()){
                    totalSales = rs2.getDouble("total");
                    totalPaid = rs2.getDouble("paid");
                    totalBalance = rs2.getDouble("balance");
                }
                rs2.close(); totalStmt.close(); conn.close();

                result.put("categoryLabels", categoryLabels);
                result.put("categorySales", categorySales);
                result.put("totalSales", totalSales);
                result.put("totalPaid", totalPaid);
                result.put("totalBalance", totalBalance);

                response.setContentType("application/json");
                response.getWriter().print(new Gson().toJson(result));

            } else {
                // Default view - forward to JSP
                request.setAttribute("month", month);
                request.getRequestDispatcher("reports.jsp").forward(request, response);
            }

        } catch(Exception e){
            e.printStackTrace();
            response.setContentType("application/json");
            response.setStatus(500);
            response.getWriter().print("{\"error\":\""+e.getMessage()+"\"}");
        }
    }
}
