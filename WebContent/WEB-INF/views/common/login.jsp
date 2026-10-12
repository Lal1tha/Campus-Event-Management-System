<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
String selectedRole = request.getParameter("role");

if(selectedRole == null){
    selectedRole = "STUDENT";
}
%>

<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="UTF-8">

<title>Campus Event Login</title>

<link rel="stylesheet"
      href="<%=request.getContextPath()%>/assets/css/style.css">

<script defer
        src="<%=request.getContextPath()%>/assets/js/validation.js"></script>

</head>

<body class="center">

<form class="auth-card"
      method="post"
      data-validate>

    <h1>Campus Event Login</h1>

    <%@ include file="messages.jspf" %>

    <label>

        Email

        <input
                type="email"
                name="email"
                placeholder="Enter your email"
                required>

    </label>

    <label>

        Password

        <input
                type="password"
                name="password"
                placeholder="Enter your password"
                required>

    </label>

    <label>

        Login As

        <select name="role">

            <option value="STUDENT"
                <%= "STUDENT".equals(selectedRole) ? "selected" : "" %>>
                Student
            </option>

            <option value="COORDINATOR"
                <%= "COORDINATOR".equals(selectedRole) ? "selected" : "" %>>
                Coordinator
            </option>

            <option value="ADMIN"
                <%= "ADMIN".equals(selectedRole) ? "selected" : "" %>>
                Admin
            </option>

        </select>

    </label>

    <button
            class="button"
            type="submit">

        Login

    </button>

    <br><br>

    <p>

        <a href="<%=request.getContextPath()%>/forgot-password">

            Forgot Password?

        </a>

        <br><br>

        <a href="<%=request.getContextPath()%>/student/register">

            New Student? Register Here

        </a>

    </p>

</form>

</body>

</html>