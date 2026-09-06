package com.fabianospdev.volunteer.services.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class FirebaseInitializationService {

    private static final Logger log = LoggerFactory.getLogger(FirebaseInitializationService.class);

    private final ResourceLoader resourceLoader;
    private final boolean enabled;
    private final String credentialsLocation;

    public FirebaseInitializationService(
            ResourceLoader resourceLoader,
            @Value("${firebase.enabled:false}") boolean enabled,
            @Value("${firebase.credentials:classpath:firebase-adminsdk.json}") String credentialsLocation
    ) {
        this.resourceLoader = resourceLoader;
        this.enabled = enabled;
        this.credentialsLocation = credentialsLocation;
    }

    @PostConstruct
    public void initializeFirebase() {
        if (!enabled) {
            log.info("Firebase initialization is disabled");
            return;
        }

        try {
            Resource resource = resourceLoader.getResource(credentialsLocation);
            if (!resource.exists()) {
                log.warn("Firebase credentials not found at {}. Skipping initialization.", credentialsLocation);
                return;
            }

            try (InputStream serviceAccount = resource.getInputStream()) {
                FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .build();

                if (FirebaseApp.getApps().stream().noneMatch(app -> "android".equals(app.getName()))) {
                    FirebaseApp.initializeApp(options, "android");
                    log.info("Firebase initialized");
                }
            }
        } catch (Exception ex) {
            log.warn("Could not initialize Firebase: {}", ex.getMessage());
        }
    }
}
