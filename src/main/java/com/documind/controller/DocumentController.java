
package com.documind.controller;

import com.documind.dto.DocumentUploadResponse;
import com.documind.dto.PageText;
import com.documind.exception.InvalidPdfException;
import com.documind.service.PdfTextExtractor;
import com.documind.service.PdfValidator;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.io.IOException;
import java.util.List;

@RestController
public class DocumentController {

    private final PdfValidator pdfValidator;
    private final PdfTextExtractor pdfTextExtractor;

    public DocumentController(
            PdfValidator pdfValidator,
            PdfTextExtractor pdfTextExtractor) {
        this.pdfValidator = pdfValidator;
        this.pdfTextExtractor = pdfTextExtractor;
    }

    @PostMapping("/api/documents")
    public ResponseEntity<DocumentUploadResponse> uploadDocument(
            @RequestParam("file") MultipartFile file)
            throws IOException {

        pdfValidator.validate(file);

        List<PageText> pages = pdfTextExtractor.extractPages(file);

        DocumentUploadResponse response = new DocumentUploadResponse(
                file.getOriginalFilename(),
                file.getSize(),
                pages.size(),
                pages
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @ExceptionHandler(InvalidPdfException.class)
    public ResponseEntity<String> handleInvalidPdf(
            InvalidPdfException exception) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<String> handleFileTooLarge(
            MaxUploadSizeExceededException exception) {

        return ResponseEntity
                .status(HttpStatus.PAYLOAD_TOO_LARGE)
                .body("File is too large. Maximum allowed size is 20MB.");
    }
}
