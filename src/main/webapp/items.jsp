<%@ page import="java.sql.*, java.util.*" %>

<%
    // Fetch categories from DB
    List<String> categories = new ArrayList<>();
    // Fetch items from DB
    List<Map<String, String>> itemList = new ArrayList<>();

    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pahana_edu", "root", "");

        // Categories
        stmt = conn.prepareStatement("SELECT DISTINCT category FROM items ORDER BY category");
        rs = stmt.executeQuery();
        while (rs.next()) {
            categories.add(rs.getString("category"));
        }
        rs.close();
        stmt.close();

        // Items
        stmt = conn.prepareStatement("SELECT * FROM items ORDER BY id DESC");
        rs = stmt.executeQuery();
        while (rs.next()) {
            Map<String, String> item = new HashMap<>();
            item.put("id", rs.getString("id"));
            item.put("name", rs.getString("name"));
            item.put("category", rs.getString("category"));
            item.put("price", rs.getString("price"));
            item.put("quantity", rs.getString("quantity"));
            itemList.add(item);
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
        <title>Item Management - Pahana Edu</title>
        <link rel="stylesheet" href="css/style.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
        <style>
            .page-header {
                display: flex;
                justify-content: space-between;
                align-items: center;
                margin-bottom: 30px;
            }
            .page-header h2 {
                font-size: 28px;
            }
            .btn-add {
                background: #007bff;
                color: #fff;
                padding: 10px 20px;
                border-radius: 8px;
                text-decoration: none;
                font-weight: bold;
            }
            .filters {
                display: flex;
                gap: 15px;
                margin-bottom: 20px;
            }
            .filters input, .filters select {
                padding: 8px;
                border: 1px solid #ccc;
                border-radius: 6px;
            }
            .styled-table {
                width: 100%;
                border-collapse: collapse;
                font-size: 16px;
                border-radius: 10px;
                overflow: hidden;
                background: white;
            }
            .styled-table thead {
                background-color: #007bff;
                color: white;
            }
            .styled-table th, .styled-table td {
                padding: 12px 15px;
            }
            .styled-table tr:nth-child(even) { background: #f2f2f2; }
            .styled-table tr:hover { background: #e6f2ff; }
            .edit-btn { color: #007bff; margin-right: 10px; }
            .delete-btn { color: #dc3545; }
            .edit-row { display: none; background: #f0f8ff; }
            .edit-row.visible { display: table-row; }
            .edit-form { display: flex; gap: 10px; padding: 10px 0; flex-wrap: wrap; }
            .edit-form input, .edit-form select {
                padding: 8px;
                border: 1px solid #ccc;
                border-radius: 6px;
                flex: 1;
            }
            .btn-update {
                background: #28a745;
                color: white;
                border: none;
                padding: 8px 16px;
                border-radius: 6px;
                cursor: pointer;
            }
            .message {
                padding: 12px 20px;
                border-radius: 6px;
                margin-bottom: 20px;
            }
            .message.success { background: #d4edda; color: #155724; border-left: 5px solid #28a745; }
            .message.error { background: #f8d7da; color: #721c24; border-left: 5px solid #dc3545; }
        </style>
    </head>
    <body>
        <jsp:include page="sidebar.jsp" />

        <div class="main-content">
            <div class="page-header">
                <h2>Item Management</h2>
                <a href="add-item.jsp" class="btn-add">+ Add Item</a>
            </div>

            <%
                String msg = request.getParameter("success");
                String messageText = "";
                String messageClass = "";
                if ("1".equals(msg)) {
                    messageText = "Item added successfully!";
                    messageClass = "success";
                } else if ("2".equals(msg)) {
                    messageText = "Item updated successfully!";
                    messageClass = "success";
                } else if ("3".equals(msg)) {
                    messageText = "Item deleted successfully!";
                    messageClass = "success";
                } else if ("0".equals(msg)) {
                    messageText = "Something went wrong!";
                    messageClass = "error";
                }
            %>
            <% if (!messageText.isEmpty()) {%>
            <div class="message <%= messageClass%>" id="autoDisappear"><%= messageText%></div>
            <% } %>

            <div class="filters">
                <input type="text" id="searchBox" placeholder="Search item name...">
                <select id="categoryFilter">
                    <option value="">All Categories</option>
                    <% for (String cat : categories) {%>
                    <option value="<%= cat%>"><%= cat%></option>
                    <% } %>
                </select>
            </div>

            <table class="styled-table" id="itemsTable">
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Category</th>
                        <th>Price</th>
                        <th>Quantity</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <% for (Map<String, String> i : itemList) {%>
                    <tr>
                        <td><%= i.get("name")%></td>
                        <td><%= i.get("category")%></td>
                        <td><%= i.get("price")%></td>
                        <td><%= i.get("quantity")%></td>
                        <td>
                            <a href="#" class="edit-btn" onclick="toggleEdit('<%= i.get("id")%>')"><i class="fas fa-edit"></i></a>
                            <form action="ItemController" method="post" style="display:inline;">
    <input type="hidden" name="action" value="delete">
    <input type="hidden" name="id" value="<%= i.get("id") %>">
    <button type="submit" class="delete-btn" onclick="return confirm('Are you sure you want to delete the item?')">
        <i class="fas fa-trash"></i>
    </button>
</form>

                        </td>
                    </tr>
                    <tr id="editRow-<%= i.get("id")%>" class="edit-row">
                        <td colspan="5">
                            <form action="ItemController" method="post">
    <input type="hidden" name="action" value="update">
    <input type="hidden" name="id" value="<%= i.get("id") %>">
    <div class="edit-form">
        <input type="text" name="name" value="<%= i.get("name") %>" required>
        <select name="category" required>
            <% for (String cat : categories) { %>
            <option value="<%= cat %>" <%= cat.equals(i.get("category")) ? "selected" : "" %>><%= cat %></option>
            <% } %>
        </select>
        <input type="number" name="price" value="<%= i.get("price") %>" required>
        <input type="number" name="quantity" value="<%= i.get("quantity") %>" required>
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
            function toggleEdit(id) {
                document.getElementById("editRow-" + id).classList.toggle("visible");
            }
            document.getElementById("searchBox").addEventListener("keyup", function () {
                let filter = this.value.toLowerCase();
                let rows = document.querySelectorAll("#itemsTable tbody tr");
                rows.forEach((row, index) => {
                    if (index % 2 === 0) { // Only main rows, skip edit rows
                        let itemName = row.cells[0].innerText.toLowerCase();
                        row.style.display = itemName.includes(filter) ? "" : "none";
                        if (row.nextElementSibling && row.nextElementSibling.classList.contains("edit-row")) {
                            row.nextElementSibling.style.display = row.style.display;
                        }
                    }
                });
            });
            document.getElementById("categoryFilter").addEventListener("change", function () {
                let category = this.value.toLowerCase();
                let rows = document.querySelectorAll("#itemsTable tbody tr");
                rows.forEach((row, index) => {
                    if (index % 2 === 0) {
                        let itemCategory = row.cells[1].innerText.toLowerCase();
                        row.style.display = category === "" || itemCategory === category ? "" : "none";
                        if (row.nextElementSibling && row.nextElementSibling.classList.contains("edit-row")) {
                            row.nextElementSibling.style.display = row.style.display;
                        }
                    }
                });
            });
            setTimeout(() => {
                const el = document.getElementById("autoDisappear");
                if (el)
                    el.style.display = "none";
            }, 3000);
        </script>

    </body>
</html>
