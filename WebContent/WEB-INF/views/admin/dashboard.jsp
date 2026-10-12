<%@ page import="java.util.*,com.campus.model.Event" %>

<!DOCTYPE html>
<html>

<head>
    <title>Admin Dashboard</title>

    <link rel="stylesheet"
          href="<%=request.getContextPath()%>/assets/css/style.css">
</head>

<body>

    <%@ include file="../common/header.jspf" %>

    <div class="layout">

        <%@ include file="../common/sidebar.jspf" %>

        <main class="content">

            <%@ include file="../common/messages.jspf" %>

            <h1 style="margin-bottom:25px;">Admin Dashboard</h1>

            <!-- Statistics -->
            <section class="grid stats">

                <%
                    Map<String, Integer> stats =
                        (Map<String, Integer>) request.getAttribute("stats");

                    if (stats != null) {
                        for (Map.Entry<String, Integer> entry : stats.entrySet()) {
                %>

                    <article class="card">
                        <small><%= entry.getKey() %></small>
                        <strong><%= entry.getValue() %></strong>
                    </article>

                <%
                        }
                    }
                %>

            </section>

            <h2>Events</h2>

            <div class="table-wrap">

                <table>

                    <tr>
                        <th>Title</th>
                        <th>Date</th>
                        <th>Venue</th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>

                    <%
                        List<Event> events =
                            (List<Event>) request.getAttribute("events");

                        if (events != null) {
                            for (Event event : events) {
                    %>

                    <tr>

                        <td><%= event.getTitle() %></td>
                        <td><%= event.getEventDate() %></td>
                        <td><%= event.getVenue() %></td>
                        <td><%= event.getStatus() %></td>

                        <td class="actions">

                            <a class="button secondary"
                               href="events/update?id=<%= event.getId() %>">
                                Edit
                            </a>

                            <form method="post" action="events/approve">
                                <input type="hidden"
                                       name="id"
                                       value="<%= event.getId() %>">
                                <button type="submit">
                                    Approve
                                </button>
                            </form>

                            <form method="post" action="events/delete">
                                <input type="hidden"
                                       name="id"
                                       value="<%= event.getId() %>">
                                <button type="submit"
                                        class="button danger">
                                    Delete
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