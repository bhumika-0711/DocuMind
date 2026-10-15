
package com.documind.config;

import com.documind.entity.Document;
import com.documind.repository.DocumentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StartupCheck {

    @Bean
    CommandLineRunner checkDatabase(DocumentRepository repository) {
        return args -> {
            Document document = new Document("startup-check.pdf", 1);
            repository.save(document);

            System.out.println("Document saved with ID: " + document.getId());
            System.out.println("Total documents: " + repository.count());
        };
    }
}
