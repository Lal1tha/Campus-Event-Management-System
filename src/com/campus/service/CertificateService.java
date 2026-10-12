package com.campus.service;

import java.io.File;

import com.campus.dao.CertificateDAO;
import com.campus.dao.EventDAO;
import com.campus.dao.RegistrationDAO;
import com.campus.dao.StudentDAO;
import com.campus.dao.AttendanceDAO;
import com.campus.model.Certificate;
import com.campus.model.Event;
import com.campus.model.EventRegistration;
import com.campus.model.Student;
import com.campus.util.PDFUtil;

public class CertificateService {
    private final CertificateDAO certificateDAO = new CertificateDAO();
    private final RegistrationDAO registrationDAO = new RegistrationDAO();
    private final StudentDAO studentDAO = new StudentDAO();
    private final EventDAO eventDAO = new EventDAO();
    private final AttendanceDAO attendanceDAO = new AttendanceDAO();

    public Certificate generateIfMissing(int registrationId, String appRoot) throws Exception {
        if (!attendanceDAO.exists(registrationId)) {
            throw new IllegalArgumentException("Certificate is available only after attendance is marked.");
        }
        Certificate existing = certificateDAO.findByRegistration(registrationId);
        if (existing != null) {
            return existing;
        }
        EventRegistration registration = registrationDAO.findById(registrationId);
        Student student = studentDAO.findById(registration.getStudentId());
        Event event = eventDAO.findById(registration.getEventId());
        String verificationCode = java.util.UUID.randomUUID().toString();
        String pdfPath = PDFUtil.generateCertificate(student, event, verificationCode, appRoot + File.separator + "certificates");
        certificateDAO.create(registrationId, verificationCode, pdfPath);
        return certificateDAO.findByRegistration(registrationId);
    }
}
