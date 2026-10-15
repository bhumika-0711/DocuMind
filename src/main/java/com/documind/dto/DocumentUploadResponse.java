
package com.documind.dto;

import java.util.List;

public record DocumentUploadResponse(
        String filename,
        long sizeBytes,
        int pageCount,
        List<PageText> pages) {
}
