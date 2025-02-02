package com.fabianospdev.volunteer.services.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class FirebaseInitializationService {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ResourceLoader resourceLoader;

    @PostConstruct
    public void initializeFirebase() {
        try {
            Resource resource = resourceLoader.getResource("classpath:volunteers-3a94d-firebase-adminsdk-e0xt7-a424fa9e51.json");

            if (!resource.exists()) {
                throw new IOException("Arquivo de configuração do Firebase não encontrado!");
            }

            InputStream serviceAccount = resource.getInputStream();

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            FirebaseApp.initializeApp(options, "android");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}