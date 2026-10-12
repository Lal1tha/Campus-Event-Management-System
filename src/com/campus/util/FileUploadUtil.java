package com.campus.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import jakarta.servlet.http.Part;

public final class FileUploadUtil {
    private FileUploadUtil() {
    }

    public static String savePart(Part part, String directory) throws IOException {
        if (part == null || part.getSize() == 0) {
            return "";
        }
        Files.createDirectories(Path.of(directory));
        String fileName = Path.of(part.getSubmittedFileName()).getFileName().toString();
        Path target = Path.of(directory, System.currentTimeMillis() + "-" + fileName);
        part.write(target.toString());
        return target.getFileName().toString();
    }
}
