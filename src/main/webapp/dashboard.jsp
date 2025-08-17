<%@ page session="true" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String username = (String) session.getAttribute("username");
    if (username == null) {
        response.sendRedirect("login.jsp");
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <title>Dashboard</title>
        <link rel="stylesheet" type="text/css" href="css/style.css">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet">
        <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>

    </head>
    <body>
        <jsp:include page="sidebar.jsp" />

        <div class="main-content">
            <h1>Welcome, <%= username%>!</h1>

            <div class="cards">
                <div class="card">
                    <h2 id="billCount">0</h2>
                    <p style="color:white;">Total Bills</p>
                </div>
                <div class="card">
                    <h2 id="customerCount">0</h2>
                    <p style="color:white;">Total Customers</p>
                </div>
                <div class="card">
                    <h2 id="itemCount">0</h2>
                    <p style="color:white;">Total Items</p>
                </div>
            </div>

            <div class="description">
                <h3>About Our Shop</h3>
                <p>
                    Pahana Edu is a trusted educational and stationery shop that caters to the diverse needs of students, teachers, and schools. With a strong commitment to quality and affordability, the shop offers a wide range of educational materials, textbooks, stationery items, and classroom supplies all under one roof.
                </p>
                <p>
                    Known for its reliable customer service and well-organized stock, Pahana Edu has become a go-to destination for parents and institutions seeking essential academic resources. Whether it's school book lists, exam materials, or everyday stationery, Pahana Edu ensures that customers receive the right products at the right time, contributing positively to the learning journey of thousands.
                        </p>
            </div>
        </div>

        
        <script>
            $(document).ready(function () {
                fetch('DashboardCountsServlet')
                        .then(res => res.json())
                        .then(data => {
                            $("#billCount").text(data.bills);
                            $("#customerCount").text(data.customers);
                            $("#itemCount").text(data.items);
                        })
                        .catch(err => console.error("Error loading dashboard counts:", err));
            });
        </script>

    </body>
</html>
