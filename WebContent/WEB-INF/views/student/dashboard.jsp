<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.campus.model.Event" %>
<%@ page import="com.campus.model.EventRegistration" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Student Dashboard</title>

    <link rel="stylesheet"
          href="<%=request.getContextPath()%>/assets/css/style.css">
</head>

<body>

<%@ include file="../common/header.jspf" %>

<div class="layout">

    <%@ include file="../common/sidebar.jspf" %>

    <main class="content">

        <h1>Student Dashboard</h1>

        <%@ include file="../common/messages.jspf" %>

        <h2>Upcoming Events</h2>

        <div class="grid cards">

            <%
                List<Event> events =
                        (List<Event>) request.getAttribute("events");

                if (events != null) {
                    for (Event event : events) {
            %>

            <article class="card">

                <h3><%= event.getTitle() %></h3>

                <p><%= event.getDescription() %></p>

                <p>
                    <%= event.getEventDate() %> |
                    <%= event.getVenue() %>
                </p>

                <form method="post"
                      action="<%=request.getContextPath()%>/student/events/register">

                    <input
                            type="hidden"
                            name="eventId"
                            value="<%= event.getId() %>">

                    <button type="submit">
                        Register
                    </button>

                </form>

            </article>

            <%
                    }
                }
            %>

        </div>

        <h2>My Registrations</h2>

        <div class="table-wrap">

            <table>

                <tr>
                    <th>ID</th>
                    <th>Event</th>
                    <th>Status</th>
                    <th>Actions</th>
                </tr>

                <%
                    List<EventRegistration> registrations =
                            (List<EventRegistration>) request.getAttribute("registrations");

                    if (registrations != null) {
                        for (EventRegistration registration : registrations) {
                %>

                <tr>

                    <td><%= registration.getId() %></td>

                    <td><%= registration.getEventTitle() %></td>

                    <td><%= registration.getStatus() %></td>

                    <td class="actions">

                        <a class="button secondary"
                           href="receipt?id=<%= registration.getId() %>">
                            Receipt
                        </a>

                        <a class="button secondary"
                           href="qr-download?id=<%= registration.getId() %>">
                            QR
                        </a>

                        <a class="button secondary"
                           href="certificate?registrationId=<%= registration.getId() %>">
                            Certificate
                        </a>

                        <form method="post"
                              action="registrations/cancel">

                            <input
                                    type="hidden"
                                    name="id"
                                    value="<%= registration.getId() %>">

                            <button
                                    type="submit"
                                    class="button danger">
                                Cancel
                            </button>

                        </form>

                    </td>

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