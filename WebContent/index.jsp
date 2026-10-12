<%@ page import="java.util.*,com.campus.dao.EventDAO,com.campus.model.Event,com.campus.service.DashboardService" %>

<%
List<Event> events = new ArrayList<>();
Map<String,Integer> stats = new LinkedHashMap<>();

try{
    events = new EventDAO().search("", "", true);
    stats = new DashboardService().statistics();
}catch(Exception e){
    e.printStackTrace();
}
%>

<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>Campus Event Management System</title>

<link rel="stylesheet"
href="<%=request.getContextPath()%>/assets/css/style.css">

</head>

<body>

<header class="hero">

    <section class="hero-content">

        <h1>Campus Event Management System</h1>

        <p>
            One centralized platform to register for events,
            manage registrations, verify attendance using QR codes,
            and download certificates.
        </p>

        <div class="hero-actions">

            <a class="button"
               href="<%=request.getContextPath()%>/login?role=STUDENT">
                Student Login
            </a>

            <a class="button secondary"
               href="<%=request.getContextPath()%>/login?role=COORDINATOR">
                Coordinator Login
            </a>

            <a class="button secondary"
               href="<%=request.getContextPath()%>/login?role=ADMIN">
                Admin Login
            </a>

            <a class="button"
               href="<%=request.getContextPath()%>/student/register">
                Student Registration
            </a>

        </div>

    </section>

</header>

<main class="page">

    <!-- Statistics -->

    <section class="grid stats">

        <% for(Map.Entry<String,Integer> entry : stats.entrySet()){ %>

            <article class="card">

                <small><%= entry.getKey() %></small>

                <strong><%= entry.getValue() %></strong>

            </article>

        <% } %>

    </section>

    <!-- Upcoming Events -->

    <section id="events">

        <h2>Upcoming Events</h2>

        <div class="grid cards">

            <% for(Event event : events){ %>

            <article class="card">

                <h3><%= event.getTitle() %></h3>

                <p><%= event.getDescription() %></p>

                <p>

                    <strong><%= event.getEventDate() %></strong>

                    <br>

                    Venue : <%= event.getVenue() %>

                </p>

            </article>

            <% } %>

        </div>

    </section>

    <!-- About -->

    <section id="about" class="band">

        <h2>About College</h2>

        <p>

            ABC College of Engineering encourages innovation,
            leadership and technical excellence by organizing
            workshops, hackathons, seminars, cultural festivals
            and various extracurricular activities throughout
            the academic year.

        </p>

    </section>

    <!-- Contact -->

    <section id="contact">

        <h2>Contact</h2>

        <p>

            📧 events@abc-college.edu

            <br><br>

            📞 +91 9876543210

        </p>

    </section>

</main>

<footer>

    <p>

        © 2026 Campus Event Management System

    </p>

</footer>

</body>

</html>