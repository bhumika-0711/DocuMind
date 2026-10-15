
package com.documind.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "documents")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filename;
    private int pageCount;
    private LocalDateTime uploadedAt;

    public Document() {
    }

    public Document(String filename, int pageCount) {
        this.filename = filename;
        this.pageCount = pageCount;
        this.uploadedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getFilename() {
        return filename;
    }

    public int getPageCount() {
        return pageCount;
    }

    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }
}
