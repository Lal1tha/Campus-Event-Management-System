package com.campus.service;

import com.campus.dao.AttendanceDAO;
import com.campus.model.EventRegistration;

public class AttendanceService {
    private final AttendanceDAO attendanceDAO = new AttendanceDAO();
    private final RegistrationService registrationService = new RegistrationService();

    public EventRegistration markByQr(String qrPayload, int coordinatorId) throws Exception {
        EventRegistration registration = registrationService.validateQr(qrPayload);
        if (registration == null) {
            throw new IllegalArgumentException("Invalid or cancelled registration QR code.");
        }
        if (attendanceDAO.exists(registration.getId())) {
            throw new IllegalArgumentException("Attendance is already marked for this registration.");
        }
        attendanceDAO.mark(registration.getId(), registration.getStudentId(), registration.getEventId(), coordinatorId);
        return registration;
    }
}
