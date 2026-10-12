package com.campus.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.campus.model.Certificate;
import com.campus.util.DateUtil;

public class CertificateDAO extends BaseDAO {
    public Certificate findByRegistration(int registrationId) throws SQLException {
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement("SELECT * FROM certificates WHERE registration_id=?")) {
            ps.setInt(1, registrationId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Certificate certificate = new Certificate();
                    certificate.setId(rs.getInt("id"));
                    certificate.setRegistrationId(rs.getInt("registration_id"));
                    certificate.setVerificationCode(rs.getString("verification_code"));
                    certificate.setPdfPath(rs.getString("pdf_path"));
                    certificate.setGeneratedAt(DateUtil.toLocalDateTime(rs.getTimestamp("generated_at")));
                    return certificate;
                }
            }
        }
        return null;
    }

    public String create(int registrationId, String verificationCode, String pdfPath) throws SQLException {
        String sql = "INSERT INTO certificates(registration_id, verification_code, pdf_path) VALUES(?,?,?)";
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, registrationId);
            ps.setString(2, verificationCode);
            ps.setString(3, pdfPath);
            ps.executeUpdate();
        }
        return verificationCode;
    }
}
