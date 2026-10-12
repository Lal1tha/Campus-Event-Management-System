<!DOCTYPE html>
<html><head><title>Forgot Password</title><link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/style.css"><script defer src="<%=request.getContextPath()%>/assets/js/validation.js"></script></head>
<body class="center">
<form class="auth-card" method="post" data-validate>
    <h1>Reset Student Password</h1>
    <%@ include file="messages.jspf" %>
    <label>Email<input type="email" name="email" required></label>
    <label>New Password<input type="password" name="password" minlength="8" required></label>
    <button class="button" type="submit">Update Password</button>
    <a href="<%=request.getContextPath()%>/login">Back to login</a>
</form>
</body></html>
