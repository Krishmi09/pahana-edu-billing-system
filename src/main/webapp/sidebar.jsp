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

        <link rel="stylesheet" type="text/css" href="css/style.css">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet">
        <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
        <!-- Bootstrap 5 CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

        <!-- Bootstrap 5 JS (for modal) -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

        <style>
            /* Logout Modal Overlay */
            .logout-modal-overlay {
                display: none;
                position: fixed;
                z-index: 999;
                left: 0; top: 0;
                width: 100vw;
                height: 100vh;
                background-color: rgba(0, 0, 0, 0.4);
                justify-content: center;
                align-items: center;
                animation: fadeIn 0.3s ease-in-out;
            }

            /* Modal Box */
            .logout-modal-content {
                background-color: #fff;
                padding: 30px 35px;
                border-radius: 12px;
                box-shadow: 0 10px 30px rgba(0,0,0,0.2);
                text-align: center;
                width: 400px;
                animation: slideUp 0.3s ease-in-out;
            }

            /* Heading and Message */
            .logout-modal-content h2 {
                margin-top: 0;
                font-size: 24px;
                color: #333;
            }

            .logout-modal-content p {
                margin: 15px 0 25px;
                color: #555;
            }

            /* Buttons */
            .logout-buttons {
                display: flex;
                justify-content: center;
                gap: 15px;
            }

            .logout-confirm {
                background-color: #d9534f;
                color: white;
                border: none;
                padding: 10px 20px;
                border-radius: 8px;
                cursor: pointer;
                font-weight: bold;
                transition: background 0.3s;
            }

            .logout-confirm:hover {
                background-color: #c9302c;
            }

            .logout-cancel {
                background-color: #6c757d;
                color: white;
                border: none;
                padding: 10px 20px;
                border-radius: 8px;
                cursor: pointer;
                font-weight: bold;
                transition: background 0.3s;
            }

            .logout-cancel:hover {
                background-color: #5a6268;
            }

            /* Animations */
            @keyframes fadeIn {
                from { opacity: 0; }
                to { opacity: 1; }
            }

            @keyframes slideUp {
                from { transform: translateY(20px); opacity: 0; }
                to { transform: translateY(0); opacity: 1; }
            }

        </style>

    </head>
    <body>
        <div class="sidebar">
            <div class="logo">
                <img src="images/logo.jpg" alt="Logo">
            </div>
            <ul class="menu">
                <li><a href="dashboard.jsp"><i class="fas fa-home"></i> Dashboard</a></li>
                <li>
                    <a href="customer.jsp"><i class="fas fa-user"></i> Customers </a>
                </li>
                <li>
                    <a href="items.jsp"><i class="fas fa-box"></i> Items </a>
                </li>
                <li><a href="billing.jsp"><i class="fas fa-file-invoice"></i> Billing</a></li>
                <li><a href="reports.jsp"><i class="fas fa-clipboard"></i> Report</a></li>
                <li><a href="help.jsp"><i class="fas fa-chart-bar"></i> Help</a></li>
                <li><a href="#" class="logout-btn" onclick="showLogoutModal()"><i class="fas fa-sign-out-alt"></i> Logout</a></li>
            </ul>
        </div>

        <!-- Logout Confirmation Modal -->
        <div id="logoutModal" class="logout-modal-overlay">
            <div class="logout-modal-content">
                <h2>Confirm Logout</h2>
                <p>Are you sure you want to log out?</p>
                <div class="logout-buttons">
                    <button onclick="confirmLogout()" class="logout-confirm">Yes, Logout</button>
                    <button onclick="closeLogoutModal()" class="logout-cancel">Cancel</button>
                </div>
            </div>
        </div>


        <script>
            function showLogoutModal() {
                document.getElementById("logoutModal").style.display = "flex";
            }

            function closeLogoutModal() {
                document.getElementById("logoutModal").style.display = "none";
            }

            function confirmLogout() {
                window.location.href = "login.jsp?logout=1";
            }
        </script>


    </body>
</html>