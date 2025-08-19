<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Login - Pahana Edu</title>
        <style>
            body {
                margin: 0;
                font-family: Arial, sans-serif;
                display: flex;
                height: 100vh;
            }

            .left {
                flex: 1;
                background-color: #f6f6f6;
                padding: 260px 30px;
            }

            .left h1 {
                font-size: 48px;
                margin-bottom: 10px;
            }

            .left p {
                font-size: 18px;
                color: #444;

            }

            .right {
                flex: 1;
                background-color: #1e1e1e;
                color: white;
                display: flex;
                align-items: center;
                justify-content: center;
            }

            .login-card {
                background-color: #2c2c2c;
                padding: 40px;
                border-radius: 15px;
                width: 350px;
                box-shadow: 0 0 30px rgba(0, 0, 0, 0.5);
            }

            .login-card h2 {
                text-align: center;
                margin-bottom: 30px;
            }

            input[type="text"], input[type="password"] {
                width: 100%;
                padding: 12px;
                margin: 10px 0;
                border: none;
                border-radius: 8px;
            }

            .btn-login {
                width: 100%;
                padding: 12px;
                background-color: #6674f4;
                color: white;
                border: none;
                border-radius: 8px;
                cursor: pointer;
                font-weight: bold;
                margin-top: 10px;
            }

            .btn-login:hover {
                background-color: #5560e6;
            }

            .remember-me {
                margin: 10px 0;
            }

            .footer {
                text-align: center;
                margin-top: 20px;
                font-size: 12px;
            }

            .error-msg {
                text-align: center;
                color: #E97451;
                margin-top: 10px;
            }

            .message.success {
                background-color: #d4edda;
                color: #155724;
                border-left: 5px solid #28a745;
                padding: 12px 20px;
                border-radius: 6px;
                margin-bottom: 20px;
                font-weight: 500;
                animation: fadeIn 0.5s ease-in-out;
            }



        </style>
    </head>
    <body>

        <div class="left">
            <%
                String logoutMsg = request.getParameter("logout");
                if ("1".equals(logoutMsg)) {
            %>
            <div class="message success">You have successfully logged out!</div>
            <%
                }
            %>


            <h1>Welcome!!</h1>
            <p>Login to access your personalized dashboard and manage customers, items, billing, and reports with ease.<br> Stay organized, serve customers faster, and keep your business running smoothly.</p>

        </div>

        <div class="right">
            <div class="login-card">
                <h2>Sign In</h2>

                <!-- Success/Error messages -->
        <%
            String msg = request.getParameter("success");
            if ("0".equals(msg)) {
        %>
            <div class="message error" id="autoDisappear">Invalid username or password!</div>
        <%
            } else if ("1".equals(msg)) {
        %>
            <div class="message success" id="autoDisappear">Login successful!</div>
        <%
            }
        %>

                <form method="post" action="LoginServlet">
                    <label>Email</label>
                    <input type="text" name="username" placeholder="you@example.com" required>

                    <label>Password</label>
                    <input type="password" name="password" placeholder="••••••••" required>

                    <div class="remember-me">
                        <input type="checkbox" name="remember"> Remember me
                    </div>

                    <input type="submit" class="btn-login" value="Login">


                </form>
                <div class="footer">
                    Need access? Contact your Admin.
                </div>
            </div>
        </div>

    </body>
</html>
