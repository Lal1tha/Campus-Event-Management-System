package com.campus.service;

import java.io.File;

import com.campus.dao.RegistrationDAO;
import com.campus.dao.StudentDAO;
import com.campus.model.EventRegistration;
//import com.campus.model.Student;
//import com.campus.util.EmailUtil;
import com.campus.util.QRCodeUtil;

public class RegistrationService {

    private final RegistrationDAO registrationDAO = new RegistrationDAO();
    private final StudentDAO studentDAO = new StudentDAO();

    public int register(int studentId, int eventId, String appRoot) throws Exception {

        if (registrationDAO.hasActiveRegistration(studentId, eventId)) {
            throw new IllegalArgumentException("You are already registered for this event.");
        }

        // Create registration
        int registrationId = registrationDAO.create(studentId, eventId);

        // Generate QR code
        String payload = QRCodeUtil.buildPayload(registrationId, studentId, eventId);

        String qrPath = QRCodeUtil.createQrImage(
                payload,
                appRoot + File.separator + "qr",
                registrationId);

        // Save QR details in database
        registrationDAO.saveQr(registrationId, payload, qrPath);

        /*
         * ===========================
         * EMAIL SECTION DISABLED
         * ===========================
         * Uncomment this block after
         * configuring Jakarta Mail
         * and Jakarta Activation.
         */

        /*
        Student student = studentDAO.findById(studentId);

        File qrFile = new File(appRoot, qrPath);

        try {
            EmailUtil.sendRegistrationEmail(
                    student.getEmail(),
                    "Campus Event Registration Confirmed",
                    "<h2>Registration Confirmed</h2>"
                    + "<p>Your registration ID is "
                    + registrationId
                    + ".</p>",
                    qrFile);
        } catch (Exception ignored) {
            // Ignore email failures
        }
        */

        return registrationId;
    }

    public EventRegistration validateQr(String payload) throws Exception {
        return registrationDAO.findByQrPayload(payload);
    }
}