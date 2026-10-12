package com.campus.model;

import java.time.LocalDateTime;

public class Certificate {
    private int id;
    private int registrationId;
    private String verificationCode;
    private String pdfPath;
    private LocalDateTime generatedAt;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getRegistrationId() { return registrationId; }
    public void setRegistrationId(int registrationId) { this.registrationId = registrationId; }
    public String getVerificationCode() { return verificationCode; }
    public void setVerificationCode(String verificationCode) { this.verificationCode = verificationCode; }
    public String getPdfPath() { return pdfPath; }
    public void setPdfPath(String pdfPath) { this.pdfPath = pdfPath; }
    public LocalDateTime getGeneratedAt() { return generatedAt; }
    public void setGeneratedAt(LocalDateTime generatedAt) { this.generatedAt = generatedAt; }
}
