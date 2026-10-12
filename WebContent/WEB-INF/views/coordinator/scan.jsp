<!DOCTYPE html>
<html>

<head>

    <title>Scan Attendance QR</title>

    <link rel="stylesheet"
          href="<%=request.getContextPath()%>/assets/css/style.css">

    <!-- QR Scanner Library -->
    <script src="https://unpkg.com/html5-qrcode"></script>

    <!-- Your Scanner -->
    <script src="<%=request.getContextPath()%>/assets/js/qr-scanner.js"></script>

</head>

<body onload="startScanner('<%=request.getContextPath()%>')">

    <%@ include file="../common/header.jspf" %>

    <div class="layout">

        <%@ include file="../common/sidebar.jspf" %>

        <main class="content">

            <h1>Scan Attendance QR</h1>

            <section class="scanner">

                <div id="reader"
                     style="width:500px;max-width:100%;">
                </div>

                <br>

                <div id="scan-result" class="alert">
                    Point the webcam at a student's registration QR code.
                </div>

            </section>

        </main>

    </div>

</body>

</html>