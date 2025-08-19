<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Add Customer - Pahana Edu</title>
        <link rel="stylesheet" href="css/add-customer.css">
        <style>
            /*Add customer*/
            body {
                margin: 0;
                font-family: 'Segoe UI', sans-serif;
                background: linear-gradient(135deg, #e0eafc, #cfdef3);
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
            }
            .form-container {
                background: white;
                padding: 40px 30px;
                border-radius: 12px;
                box-shadow: 0 8px 25px rgba(0, 0, 0, 0.15);
                width: 420px;
                animation: slideIn 0.6s ease forwards;
                opacity: 0;
                transform: translateY(20px);
            }

            @keyframes slideIn {
                to {
                    opacity: 1;
                    transform: translateY(0);
                }
            }

            h2 {
                margin-bottom: 25px;
                text-align: center;
                color: #1E3A8A;
            }

            form input,
            form textarea {
                width: 100%;
                padding: 12px 14px;
                margin-bottom: 18px;
                border: 1px solid #ccc;
                border-radius: 8px;
                font-size: 15px;
                outline: none;
                transition: 0.3s ease;
            }

            form input:focus,
            form textarea:focus {
                border-color: #1E3A8A;
                box-shadow: 0 0 5px rgba(30, 58, 138, 0.4);
            }

            form textarea {
                height: 90px;
                resize: none;
            }

            .btn-group {
                display: flex;
                justify-content: space-between;
                align-items: center;
            }

            .submit-btn {
                background-color: #1E3A8A;
                color: white;
                border: none;
                padding: 10px 22px;
                border-radius: 8px;
                cursor: pointer;
                font-size: 15px;
                font-weight: bold;
                transition: 0.3s ease;
            }

            .submit-btn:hover {
                background-color: #2c51a6;
            }

            .cancel-btn {
                background-color: #ccc;
                padding: 10px 22px;
                border-radius: 8px;
                color: black;
                text-decoration: none;
                font-size: 15px;
                font-weight: bold;
                transition: background 0.3s ease;
            }

            .cancel-btn:hover {
                background-color: #999;
            }

        </style>
    </head>
    <body>
        <jsp:include page="sidebar.jsp" />

            <div class="form-container">
            <h2>Add New Customer</h2>
            <form action="CustomerController" method="post" onsubmit="return validateForm()">
                <input type="hidden" name="action" value="add">
                
                <input type="text" name="name" placeholder="Full Name" required />
                <input type="email" name="email" placeholder="Email Address" required />
                <input type="text" name="phone" placeholder="Phone Number" maxlength="10" required />
                <textarea name="address" placeholder="Address" required></textarea>

                <div class="btn-group">
                    <button type="submit" class="submit-btn">Add Customer</button>
                    <a href="customer.jsp" class="cancel-btn">Cancel</a>
                </div>
            </form>
        </div>

        <script>
            function validateForm() {
                const phone = document.querySelector('input[name="phone"]').value;
                const email = document.querySelector('input[name="email"]').value;
                const emailPattern = /^[^@\s]+@[^@\s]+\.[^@\s]+$/;

                if (!/^\d{10}$/.test(phone)) {
                    alert("Phone number must be exactly 10 digits.");
                    return false;
                }

                if (!emailPattern.test(email)) {
                    alert("Please enter a valid email address.");
                    return false;
                }

                return true;
            }
        </script>
    </body>
</html>
