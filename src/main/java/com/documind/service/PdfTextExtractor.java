
package com.documind.service;

import com.documind.dto.PageText;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PdfTextExtractor {

    public List<PageText> extractPages(MultipartFile file)
            throws IOException {

        List<PageText> pages = new ArrayList<>();

        try (PDDocument document = Loader.loadPDF(file.getBytes())) {

            PDFTextStripper stripper = new PDFTextStripper();

            int totalPages = document.getNumberOfPages();

            for (int i = 1; i <= totalPages; i++) {

                stripper.setStartPage(i);
                stripper.setEndPage(i);

                String pageText = stripper.getText(document).trim();

                pages.add(new PageText(i, pageText));
            }
        }

        return pages;
    }
}
