package by.afinny.infoservice.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface ErrorMessagesService {
    void uploadErrorMessagesReport(MultipartFile file,
                                   String userEmail, String message, UUID clientId);
}
