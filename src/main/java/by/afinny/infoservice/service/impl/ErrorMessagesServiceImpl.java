package by.afinny.infoservice.service.impl;

import by.afinny.infoservice.document.ErrorMessageDocument;
import by.afinny.infoservice.entity.ErrorMessage;
import by.afinny.infoservice.mapper.ErrorMessageMapper;
import by.afinny.infoservice.repository.ErrorDocumentRepository;
import by.afinny.infoservice.repository.ErrorMessageRepository;
import by.afinny.infoservice.service.ErrorMessagesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ErrorMessagesServiceImpl implements ErrorMessagesService {

    private final ErrorMessageMapper errorMessageMapper;
    private final ErrorMessageRepository messageRepository;
    private final ErrorDocumentRepository documentRepository;

    @Override
    @Transactional
    public void uploadErrorMessagesReport(MultipartFile file,
                                          String userEmail, String message, UUID clientId) {
        log.info("invoke uploadErrorMessagesReport() method");
        ErrorMessageDocument messageDocument = errorMessageMapper.toErrorMessageDocument(
                file, clientId, UUID.randomUUID());
        ErrorMessage errorMessage = errorMessageMapper.toErrorMessage(message, clientId, userEmail, Instant.now());
        messageRepository.save(errorMessage);
        documentRepository.save(messageDocument);
    }
}
