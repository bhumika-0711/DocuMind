
package com.documind.service;

import com.documind.exception.InvalidPdfException;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class PdfValidator {

    public void validate(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new InvalidPdfException("No file was uploaded.");
        }

        try (PDDocument document = Loader.loadPDF(file.getBytes())) {
            if (document.getNumberOfPages() == 0) {
                throw new InvalidPdfException("The PDF has no pages.");
            }

            String text = new PDFTextStripper().getText(document);

            if (text == null || text.isBlank()) {
                throw new InvalidPdfException(
                        "No extractable text found. Scanned/image-only " +
                                "PDFs aren't supported yet.");
            }

        } catch (IOException e) {
            throw new InvalidPdfException(
                    "The file isn't a valid, readable PDF.");
        }
    }
}