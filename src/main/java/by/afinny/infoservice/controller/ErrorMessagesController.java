package by.afinny.infoservice.controller;

import by.afinny.infoservice.service.impl.ErrorMessagesServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.UUID;

import static by.afinny.infoservice.controller.ErrorMessagesController.URL_ERROR_MESSAGE;

@RestController
@RequestMapping(URL_ERROR_MESSAGE)
@RequiredArgsConstructor
public class ErrorMessagesController {

    public static final String URL_ERROR_MESSAGE = "/auth/error-message";
    private final ErrorMessagesServiceImpl errorMessagesService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadErrorMessageReport(@RequestPart("file") MultipartFile file,
                                         @RequestParam @NotNull String userEmail,
                                         @RequestParam @NotNull String message,
                                         @RequestParam UUID clientId) {
        errorMessagesService.uploadErrorMessagesReport(file, userEmail, message, clientId);
    }
}
