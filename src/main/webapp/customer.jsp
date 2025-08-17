<%@ page import="java.sql.*, java.util.*" %>

<%
    List<Map<String, String>> customerList = new ArrayList<>();
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pahana_edu", "root", "");
        stmt = conn.prepareStatement("SELECT * FROM customers ORDER BY id DESC");
        rs = stmt.executeQuery();

        while (rs.next()) {
            Map<String, String> customer = new HashMap<>();
            customer.put("id", rs.getString("id"));
            customer.put("name", rs.getString("name"));
            customer.put("email", rs.getString("email"));
            customer.put("phone", rs.getString("phone"));
            customer.put("address", rs.getString("address"));
            customerList.add(customer);
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        if (rs != null) {
            rs.close();
        }
        if (stmt != null) {
            stmt.close();
        }
        if (conn != null) {
            conn.close();
        }
    }
%>

<!DOCTYPE html>
<html>
    <head>
        <title>Customer Management - Pahana Edu</title>
        <link rel="stylesheet" href="css/style.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
        <style>
            /*Customer*/
            /* Page Header */
            .page-header {
                display: flex;
                justify-content: space-between;
                align-items: center;
                margin-bottom: 30px;
            }

            .page-header h2 {
                font-size: 28px;
                color: #1e1e2f;
            }

            .btn-add {
                background: #007bff;
                color: #fff;
                border: none;
                padding: 10px 20px;
                font-weight: bold;
                border-radius: 8px;
                cursor: pointer;
                text-decoration: none;
                transition: background 0.3s ease;
            }

            .btn-add:hover {
                background: #0056b3;
            }

            /* Success/Error Message */
            .message {
                padding: 12px 20px;
                border-radius: 6px;
                margin-bottom: 20px;
                font-weight: 500;
                animation: fadeIn 0.5s ease-in-out;
            }

            .message.success {
                background-color: #d4edda;
                color: #155724;
                border-left: 5px solid #28a745;
            }

            .message.error {
                background-color: #f8d7da;
                color: #721c24;
                border-left: 5px solid #dc3545;
            }

            @keyframes fadeIn {
                from { opacity: 0; transform: translateY(-10px); }
                to { opacity: 1; transform: translateY(0); }
            }

            /* Table */
            .styled-table {
                width: 100%;
                border-collapse: collapse;
                font-size: 16px;
                box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
                overflow: hidden;
                border-radius: 10px;
                background: white;
            }

            .styled-table thead {
                background-color: #007bff;
                color: white;
            }

            .styled-table th, .styled-table td {
                padding: 15px 20px;
                text-align: left;
            }

            .styled-table tr:nth-child(even) {
                background-color: #f2f2f2;
            }

            .styled-table tr:hover {
                background-color: #e6f2ff;
            }

            .edit-btn, .delete-btn {
                color: #007bff;
                text-decoration: none;
                font-size: 18px;
                margin-right: 10px;
            }

            .delete-btn {
                color: #dc3545;
            }

            .edit-btn:hover, .delete-btn:hover {
                opacity: 0.8;
            }

            /* Edit Row Form */
            .edit-row {
                display: none;
                background-color: #f0f8ff;
                transition: all 0.4s ease;
            }

            .edit-row.visible {
                display: table-row;
                animation: slideDown 0.3s ease-in-out;
            }

            @keyframes slideDown {
                from { opacity: 0; transform: translateY(-10px); }
                to { opacity: 1; transform: translateY(0); }
            }

            .edit-form {
                display: flex;
                gap: 10px;
                flex-wrap: wrap;
                padding: 15px 0;
            }

            .edit-form input {
                padding: 8px 12px;
                border-radius: 6px;
                border: 1px solid #ccc;
                flex: 1 1 22%;
                min-width: 150px;
            }

            .btn-update {
                background-color: #28a745;
                color: white;
                border: none;
                padding: 8px 16px;
                font-weight: bold;
                border-radius: 6px;
                cursor: pointer;
                transition: background 0.3s ease;
            }

            .btn-update:hover {
                background-color: #218838;
            }
        </style>
    </head>
    <body>
        <jsp:include page="sidebar.jsp" />
        <div class="main-content">
            <div class="page-header">
                <h2>Customer Management</h2>
                <a href="add-customer.jsp" class="btn-add">+ Add Customer</a>
            </div>

            <!-- Success/Error message -->
            <%
                String msg = request.getParameter("success");
                String messageText = "";
                String messageClass = "";

                if ("1".equals(msg)) {
                    messageText = "Customer added successfully!";
                    messageClass = "success";
                } else if ("2".equals(msg)) {
                    messageText = "Customer updated successfully!";
                    messageClass = "success";
                } else if ("3".equals(msg)) {
                    messageText = "Customer deleted successfully!";
                    messageClass = "success";
                } else if ("0".equals(msg)) {
                    messageText = "Something went wrong!";
                    messageClass = "error";
                }
            %>

            <% if (!messageText.isEmpty()) {%>
            <div class="message <%= messageClass%>" id="autoDisappear">
                <%= messageText%>
            </div>
            <% } %>




            <!-- Customer Table -->
            <table class="styled-table">
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Email</th>
                        <th>Phone</th>
                        <th>Address</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <%-- Dynamic customer list from DB --%>
                    <% for (Map<String, String> c : customerList) {%>
                    <tr>
                        <td><%= c.get("name")%></td>
                        <td><%= c.get("email")%></td>
                        <td><%= c.get("phone")%></td>
                        <td><%= c.get("address")%></td>
                        <td>
                            <a href="#" class="edit-btn" onclick="toggleEdit('<%= c.get("id")%>')"><i class="fas fa-edit"></i></a>
                            <a href="DeleteCustomerServlet?id=<%= c.get("id")%>" class="delete-btn" onclick="return confirm('Are you sure you want to delete this customer?')"><i class="fas fa-trash"></i></a>
                        </td>
                    </tr>
                    <tr id="editRow-<%= c.get("id")%>" class="edit-row">
                        <td colspan="5">
                            <form action="EditCustomerServlet" method="post">
                                <input type="hidden" name="id" value="<%= c.get("id")%>">
                                <div class="edit-form">
                                    <input type="text" name="name" value="<%= c.get("name")%>" required>
                                    <input type="email" name="email" value="<%= c.get("email")%>" required>
                                    <input type="text" name="phone" value="<%= c.get("phone")%>" maxlength="10" required>
                                    <input type="text" name="address" value="<%= c.get("address")%>" required>
                                    <button type="submit" class="btn-update">Update</button>
                                </div>
                            </form>
                        </td>
                    </tr>
                    <% }%>
                </tbody>
            </table>
        </div>

        <script>
            setTimeout(() => {
                const el = document.getElementById("autoDisappear");
                if (el)
                    el.style.display = "none";
            }, 3000);

            // Toggle the visibility of the edit row
            function toggleEdit(id) {
                const row = document.getElementById("editRow-" + id);
                if (row) {
                    row.classList.toggle("visible");
                }
                }


        </script>

    </body>
</html>
