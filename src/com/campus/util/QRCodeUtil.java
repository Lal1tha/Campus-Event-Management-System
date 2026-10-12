package com.campus.util;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public final class QRCodeUtil {

    private QRCodeUtil() {
    }

    public static String buildPayload(int registrationId, int studentId, int eventId) {
        return "REG:" + registrationId +
               "|STU:" + studentId +
               "|EVT:" + eventId +
               "|UUID:" + UUID.randomUUID();
    }

    public static String createQrImage(String payload,
                                       String qrDirectory,
                                       int registrationId) throws Exception {

        System.out.println("========== QR DEBUG ==========");
        System.out.println("QR Directory : " + qrDirectory);

        Files.createDirectories(Path.of(qrDirectory));

        String fileName = "registration-" + registrationId + ".png";

        Path target = Path.of(qrDirectory, fileName);

        System.out.println("Saving To : " + target.toAbsolutePath());

        QRCodeWriter writer = new QRCodeWriter();

        BitMatrix matrix = writer.encode(
                payload,
                BarcodeFormat.QR_CODE,
                350,
                350);

        MatrixToImageWriter.writeToPath(matrix, "PNG", target);

        System.out.println("QR CREATED SUCCESSFULLY");
        System.out.println("==============================");

        return "qr/" + fileName;
    }
}