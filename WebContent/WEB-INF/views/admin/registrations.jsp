<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.campus.model.EventRegistration" %>

<!DOCTYPE html>
<html>

<head>

    <meta charset="UTF-8">

    <title>Registrations</title>

    <link rel="stylesheet"
          href="<%=request.getContextPath()%>/assets/css/style.css">

</head>

<body>

<%@ include file="../common/header.jspf" %>

<div class="layout">

    <%@ include file="../common/sidebar.jspf" %>

    <main class="content">

        <h1>Registrations</h1>

        <form class="actions" method="get">

            <input
                    type="text"
                    name="q"
                    placeholder="Search registrations">

            <button type="submit">
                Search
            </button>

        </form>

        <div class="table-wrap">

            <table>

                <tr>

                    <th>ID</th>
                    <th>Student</th>
                    <th>USN</th>
                    <th>Event</th>
                    <th>Status</th>
                    <th>Registered</th>

                </tr>

                <%
                    List<EventRegistration> registrations =
                            (List<EventRegistration>) request.getAttribute("registrations");

                    if (registrations != null) {

                        for (EventRegistration registration : registrations) {
                %>

                <tr>

                    <td><%= registration.getId() %></td>

                    <td><%= registration.getStudentName() %></td>

                    <td><%= registration.getUsn() %></td>

                    <td><%= registration.getEventTitle() %></td>

                    <td><%= registration.getStatus() %></td>

                    <td><%= registration.getRegisteredAt() %></td>

                </tr>

                <%
                        }
                    }
                %>

            </table>

        </div>

    </main>

</div>

</body>

</html>