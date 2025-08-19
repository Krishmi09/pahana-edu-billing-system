<%@ page import="java.sql.*" %>
<%
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
%>

<!DOCTYPE html>
<html>
    <head>
        <title>Add Item - Pahana Edu</title>
        <link rel="stylesheet" href="css/style.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
        <style>
            .form-container {
                margin-left: 280px;
                padding: 40px;
            }

            .form-box {
                background-color: white;
                padding: 30px;
                max-width: 600px;
                margin: 0 auto;
                box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
                border-radius: 10px;
            }

            .form-box h2 {
                text-align: center;
                margin-bottom: 25px;
                font-size: 24px;
                color: #1E3A8A;
            }

            .form-box label {
                display: block;
                margin-bottom: 8px;
                font-weight: bold;
            }

            .form-box input, .form-box select, .form-box textarea {
                width: 100%;
                padding: 10px;
                margin-bottom: 18px;
                border: 1px solid #ccc;
                border-radius: 6px;
                font-size: 15px;
            }

            .form-box textarea {
                resize: vertical;
            }

            .btn-submit {
                background-color: #1E3A8A;
                color: white;
                border: none;
                padding: 12px 20px;
                width: 100%;
                font-weight: bold;
                border-radius: 6px;
                cursor: pointer;
                transition: background-color 0.3s ease;
            }

            .btn-submit:hover {
                background-color: #2c51a6;
            }
        </style>
    </head>
    <body>
        <jsp:include page="sidebar.jsp" />
        <div class="form-container">
            <div class="form-box">
                <h2>Add New Item</h2>
   <form action="ItemController" method="post">
    <input type="hidden" name="action" value="add">

    <label for="name">Item Name</label>
    <input type="text" id="name" name="name" required>

    <label for="category">Category</label>
    <select id="category" name="category" required>
        <option value="">-- Select Category --</option>
        <% 
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pahana_edu", "root", "");
                stmt = conn.prepareStatement("SELECT DISTINCT category FROM items");
                rs = stmt.executeQuery();
                while (rs.next()) {
        %>
        <option value="<%= rs.getString("category") %>"><%= rs.getString("category") %></option>
        <% 
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            }
        %>
    </select>

    <label for="price">Price</label>
    <input type="number" id="price" name="price" step="0.01" required>

    <label for="quantity">Quantity</label>
    <input type="number" id="quantity" name="quantity" required>

    <label for="description">Description</label>
    <textarea id="description" name="description" rows="4"></textarea>

    <button type="submit" class="btn-submit">Add Item</button>
</form>



                <a href="items.jsp" class="btn-back"><i class="fas fa-arrow-left"></i> Back to Item List</a>
            </div>
            </div>
    </body>
</html>