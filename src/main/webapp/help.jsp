<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Help & User Guide | Pahana Edu</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <style>
        body {
            margin: 0;
            font-family: 'Segoe UI', sans-serif;
            background: #f4f6f9;
            color: #333;
        }
        .container {
            max-width: 1000px;
            margin: 70px auto;
            padding: 5px;
            
        }
        .main-content { margin-left: 280px; }
        h1 {
            text-align: center;
            margin-bottom: 30px;
            color: #007bff;
        }
        .help-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
            gap: 20px;
        }
        .help-card {
            background: #fff;
            padding: 20px;
            border-radius: 12px;
            box-shadow: 0 5px 15px rgba(0,0,0,0.05);
            transition: transform 0.2s ease, box-shadow 0.2s ease;
        }
        .help-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 8px 20px rgba(0,0,0,0.1);
        }
        .help-card i {
            font-size: 35px;
            color: #ffaa00;
            margin-bottom: 15px;
        }
        .help-card h3 {
            margin: 0 0 10px;
            font-size: 20px;
            color: #333;
        }
        .help-card p {
            font-size: 15px;
            line-height: 1.5;
            color: #555;
        }
        .footer-note {
            margin-top: 40px;
            text-align: center;
            font-size: 14px;
            color: #777;
        }
        .footer-note strong {
            color: #007bff;
        }
    </style>
</head>
<body>

<jsp:include page="sidebar.jsp" />

<div class="main-content">
<div class="container">
    <h1><i class="fas fa-circle-question"></i> Help & User Guide</h1>
    <div class="help-grid">
        
        <div class="help-card">
            <i class="fas fa-user-plus"></i>
            <h3>Add / Edit Customers</h3>
            <p>Use the <strong>Customers</strong> section to add new customers, edit details, or remove outdated records. Click the "Add Customer" button to open the form. Ensure all fields are filled before saving.</p>
        </div>

        <div class="help-card">
            <i class="fas fa-box"></i>
            <h3>Manage Items</h3>
            <p>In the <strong>Items</strong> section, add new products, update prices, change quantities, or delete items no longer sold. Categories are loaded dynamically for easy organization.</p>
        </div>

        <div class="help-card">
            <i class="fas fa-receipt"></i>
            <h3>Generate Bills</h3>
            <p>Click on an item to add it to the cart on the right panel. Adjust quantities, remove items, and enter customer details. The total and balance are calculated automatically before generating an invoice.</p>
        </div>

        <div class="help-card">
            <i class="fas fa-chart-bar"></i>
            <h3>View Reports</h3>
            <p>The <strong>Reports</strong> section provides visual sales analytics. See the highest sold items in a bar chart and filter monthly invoices. Download reports as PDF for record keeping.</p>
        </div>

        <div class="help-card">
            <i class="fas fa-right-from-bracket"></i>
            <h3>Logout</h3>
            <p>Click the <strong>Logout</strong> button in the navigation menu. Confirm your action to securely sign out of the system. A message will confirm that you have successfully logged out.</p>
        </div>

        <div class="help-card">
            <i class="fas fa-circle-info"></i>
            <h3>Support</h3>
            <p>If you encounter any issues or need assistance, contact the system administrator. You can also check the FAQ section for common troubleshooting tips.</p>
        </div>

    </div>

    <div class="footer-note">
        <p>For technical support, contact <strong>IT Department</strong> | Pahana Edu</p>
    </div>
</div>
</div>
</body>
</html>
