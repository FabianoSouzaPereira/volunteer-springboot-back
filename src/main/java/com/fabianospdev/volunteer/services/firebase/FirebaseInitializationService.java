package com.fabianospdev.volunteer.services.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;

@Service
public class FirebaseInitializationService {

    @PostConstruct
    public void initializeFirebase() {
        try {
            FileInputStream serviceAccount = new FileInputStream("src/main/resources/volunteers-3a94d-firebase-adminsdk-e0xt7-a424fa9e51.json");

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();
            FirebaseApp.initializeApp(options, "android");
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}