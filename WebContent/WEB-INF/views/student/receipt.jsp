<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.campus.model.EventRegistration" %>

<%
EventRegistration registration =
        (EventRegistration) request.getAttribute("registration");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Registration Receipt</title>

    <link rel="stylesheet"
          href="<%=request.getContextPath()%>/assets/css/style.css">
</head>

<body>

<%@ include file="../common/header.jspf" %>

<div class="layout">

    <%@ include file="../common/sidebar.jspf" %>

    <main class="content">

        <section class="card">

            <h1>Registration Receipt</h1>

            <%@ include file="../common/messages.jspf" %>

            <% if (registration != null) { %>

                <p><strong>Registration ID:</strong> <%= registration.getId() %></p>

                <p>
                    <strong>Student:</strong>
                    <%= registration.getStudentName() %>
                    (<%= registration.getUsn() %>)
                </p>

                <p>
                    <strong>Event:</strong>
                    <%= registration.getEventTitle() %>
                </p>

                <p>
                    <strong>Status:</strong>
                    <%= registration.getStatus() %>
                </p>

                <p>
                    <strong>Registered At:</strong>
                    <%= registration.getRegisteredAt() %>
                </p>

                <img
                    src="<%=request.getContextPath()%>/<%=registration.getQrImagePath()%>"
                    alt="QR Code"
                    style="max-width:220px;">

                <br><br>

                <button onclick="window.print()">
                    Download Receipt
                </button>

                <a class="button secondary"
                   href="qr-download?id=<%=registration.getId()%>">
                    Download QR
                </a>

            <% } else { %>

                <p>No registration found.</p>

            <% } %>

        </section>

    </main>

</div>

</body>
</html>