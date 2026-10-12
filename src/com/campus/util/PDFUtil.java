package com.campus.util;

import java.awt.Color;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;

import com.campus.model.Event;
import com.campus.model.Student;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;

public final class PDFUtil {
    private PDFUtil() {
    }

    public static String generateCertificate(Student student, Event event, String verificationCode, String outputDirectory) throws Exception {
        Files.createDirectories(Path.of(outputDirectory));
        String fileName = "certificate-" + student.getId() + "-" + event.getId() + ".pdf";
        Path target = Path.of(outputDirectory, fileName);
        Document document = new Document(PageSize.A4.rotate(), 48, 48, 42, 42);
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(target.toFile()));
        document.open();

        PdfContentByte canvas = writer.getDirectContent();
        Rectangle page = document.getPageSize();
        canvas.setColorStroke(new Color(25, 75, 150));
        canvas.setLineWidth(4);
        canvas.rectangle(24, 24, page.getWidth() - 48, page.getHeight() - 48);
        canvas.stroke();

        Font title = new Font(Font.HELVETICA, 30, Font.BOLD, new Color(20, 55, 110));
        Font h2 = new Font(Font.HELVETICA, 22, Font.BOLD);
        Font body = new Font(Font.HELVETICA, 15);
        Font small = new Font(Font.HELVETICA, 11);

        addCentered(document, "ABC College of Engineering", title, 18);
        addCentered(document, "[ College Logo ]", small, 20);
        addCentered(document, "Certificate of Participation", h2, 26);
        addCentered(document, "This is proudly presented to", body, 14);
        addCentered(document, student.getName() + " (" + student.getUsn() + ")", h2, 18);
        addCentered(document, "for successfully attending " + event.getTitle(), body, 14);
        String date = event.getEventDate() == null ? "" : event.getEventDate().format(DateTimeFormatter.ofPattern("dd MMM yyyy"));
        addCentered(document, "held on " + date + " at " + event.getVenue(), body, 34);
        addCentered(document, "Verification Code: " + verificationCode, small, 48);
        addCentered(document, "Coordinator Signature                                      Admin Signature", body, 8);
        document.close();
        return "certificates/" + fileName;
    }

    private static void addCentered(Document document, String text, Font font, int spacingAfter) throws Exception {
        Paragraph paragraph = new Paragraph(text, font);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.setSpacingAfter(spacingAfter);
        document.add(paragraph);
    }
}
