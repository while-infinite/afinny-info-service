package by.afinny.infoservice.service.impl;

import by.afinny.infoservice.document.ErrorMessageDocument;
import by.afinny.infoservice.entity.ErrorMessage;
import by.afinny.infoservice.exception.BytesFromFileException;
import by.afinny.infoservice.mapper.ErrorMessageMapperImpl;
import by.afinny.infoservice.repository.ErrorDocumentRepository;
import by.afinny.infoservice.repository.ErrorMessageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@ActiveProfiles("test")
class ErrorMessagesServiceImplTest {

    @InjectMocks
    private ErrorMessagesServiceImpl errorMessagesService;

    @Spy
    private ErrorMessageMapperImpl errorMessageMapper = new ErrorMessageMapperImpl();

    @Mock
    private ErrorMessageRepository errorMessageRepository;

    @Mock
    private ErrorDocumentRepository errorDocumentRepository;

    @Captor
    private ArgumentCaptor<ErrorMessageDocument> documentArgumentCaptor;

    @Captor
    private ArgumentCaptor<ErrorMessage> errorMessageArgumentCaptor;

    private ErrorMessageDocument errorMessageDocument;
    private ErrorMessage errorMessage;
    private MockMultipartFile file;
    private final UUID CLIENT_ID = UUID.fromString("9b81ee52-2c0d-4bda-90b4-0b12e9d6f467");
    private final String USER_EMAIL = "test@errormesssage.com";
    private final String MESSAGE = "error message text";

    @BeforeEach
    public void setUp(){
        file = new MockMultipartFile("file",
                "document.tst",
                "text/plain",
                "Error Message".getBytes());
        errorMessageDocument = ErrorMessageDocument.builder()
                .clientId(CLIENT_ID)
                .file(getBytesFromFIle(file))
                .build();
        errorMessage = ErrorMessage.builder()
                .userId(CLIENT_ID)
                .message(MESSAGE)
                .userEmail(USER_EMAIL)
                .build();
    }

    @Test
    @DisplayName("Verifying that the documents have been saved")
    void uploadErrorMessagesReport_shouldSaveTheDocument() {
        //ACT
        errorMessagesService.uploadErrorMessagesReport(file, USER_EMAIL, MESSAGE, CLIENT_ID);

        //VERIFY
        verify(errorDocumentRepository, times(1))
                .save(documentArgumentCaptor.capture());

        verify(errorMessageRepository, times(1))
                .save(errorMessageArgumentCaptor.capture());

        verifyDocumentParameters(documentArgumentCaptor.getValue());
        verifyErrorMessageParameters(errorMessageArgumentCaptor.getValue());
    }

    @Test
    @DisplayName("If failed to save then return INTERNAL SERVER ERROR")
    void uploadErrorMessagesReport_ifNotSave_thenShouldReturnInternalServerError(){
        //ARRANGE
        doThrow(InternalError.class).when(errorMessageRepository).save(any(ErrorMessage.class));

        //ACT&VERIFY
        assertThatThrownBy(() -> errorMessagesService.uploadErrorMessagesReport(file, USER_EMAIL, MESSAGE, CLIENT_ID))
                .isInstanceOf(InternalError.class);
    }

    private void verifyDocumentParameters(ErrorMessageDocument capturedDocument) {
        assertSoftly(softAssertion -> {
            softAssertion.assertThat(capturedDocument.getClientId()).isEqualTo(errorMessageDocument.getClientId());
            softAssertion.assertThat(capturedDocument.getFile()).isEqualTo(errorMessageDocument.getFile());
        });
    }


    private void verifyErrorMessageParameters(ErrorMessage capturedErrorMessage) {
        assertSoftly(softAssertions -> {
            softAssertions.assertThat(capturedErrorMessage.getUserId()).isEqualTo(errorMessage.getUserId());
            softAssertions.assertThat(capturedErrorMessage.getMessage()).isEqualTo(errorMessage.getMessage());
            softAssertions.assertThat(capturedErrorMessage.getUserEmail()).isEqualTo(errorMessage.getUserEmail());
        });

    }

    private byte[] getBytesFromFIle(MultipartFile file){
        try {
            return file.getBytes();
        } catch (IOException e) {
            throw new BytesFromFileException("Can't get bytes from file");
        }
    }
}