function startScanner(contextPath) {

    const result = document.getElementById("scan-result");

    const html5QrCode = new Html5Qrcode("reader");

    function onScanSuccess(decodedText) {

        html5QrCode.stop();

        const data = new URLSearchParams();

        data.append("qr", decodedText);

        fetch(contextPath + "/coordinator/mark-attendance", {

            method: "POST",

            headers: {
                "Content-Type":
                "application/x-www-form-urlencoded"
            },

            body: data.toString()

        })

        .then(response => response.json())

        .then(json => {

            if (json.success) {

                result.className = "alert success";

                result.innerHTML =
                    "Attendance marked successfully for <b>"
                    + json.student +
                    "</b>";

            } else {

                result.className = "alert error";

                result.innerHTML = json.message;

                // Restart scanner
                startScanner(contextPath);
            }

        })

        .catch(() => {

            result.className = "alert error";

            result.innerHTML = "Unable to mark attendance.";

        });

    }

    html5QrCode.start(

        { facingMode: "environment" },

        {
            fps: 10,
            qrbox: 250
        },

        onScanSuccess

    ).catch(err => {

        result.className = "alert error";

        result.innerHTML = err;

    });

}