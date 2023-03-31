package by.afinny.infoservice.mapper;

import by.afinny.infoservice.document.ErrorMessageDocument;
import by.afinny.infoservice.entity.ErrorMessage;
import by.afinny.infoservice.exception.BytesFromFileException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class ErrorMessageMapperTest {

    @InjectMocks
    private ErrorMessageMapperImpl errorMessageMapper;

    private ErrorMessageDocument actualErrorMessageDocument;
    private ErrorMessage actualErrorMessage;
    private ErrorMessageDocument expectedErrorMessageDocument;
    private ErrorMessage expectedErrorMessage;
    private MockMultipartFile file;
    private final UUID ID = UUID.fromString("9b81ee52-2c0d-4bda-90b4-0b12e9d6f467");
    private final UUID CLIENT_ID = UUID.fromString("9b81ee52-2c0d-4bda-90b4-0b12e9d6f467");
    private final String USER_EMAIL = "test@errormesssage.com";
    private final String MESSAGE = "error message text";
    private final Instant MAILING_DATE = Instant.now();


    @BeforeEach
    public void setUp(){
        file = new MockMultipartFile("file",
                "document.tst",
                "text/plain",
                "Error Message".getBytes());
        expectedErrorMessageDocument = ErrorMessageDocument.builder()
                .clientId(CLIENT_ID)
                .file(getBytesFromFIle(file))
                .build();
        expectedErrorMessage = ErrorMessage.builder()
                .userId(CLIENT_ID)
                .message(MESSAGE)
                .userEmail(USER_EMAIL)
                .mailingDate(MAILING_DATE)
                .build();
        actualErrorMessageDocument = errorMessageMapper.toErrorMessageDocument(file, CLIENT_ID, ID);
        actualErrorMessage = errorMessageMapper.toErrorMessage(MESSAGE, CLIENT_ID, USER_EMAIL, MAILING_DATE);
    }

    @Test
    @DisplayName("Check fields after mapping")
    void toErrorMessage_CheckFields() {
        verifyErrorMessageFields(expectedErrorMessage, actualErrorMessage);
    }

    @Test
    @DisplayName("Check fields after mapping")
    void toErrorMessageDocument_CheckFields() {
        verifyErrorMessageDocumentFields(expectedErrorMessageDocument, actualErrorMessageDocument);
    }

    private void verifyErrorMessageFields(ErrorMessage expectedErrorMessage, ErrorMessage actualErrorMessage) {
        assertSoftly(softAssertion -> {
            softAssertion.assertThat(expectedErrorMessage.getUserId()).isEqualTo(actualErrorMessage.getUserId());
            softAssertion.assertThat(expectedErrorMessage.getMessage()).isEqualTo(actualErrorMessage.getMessage());
            softAssertion.assertThat(expectedErrorMessage.getUserEmail()).isEqualTo(actualErrorMessage.getUserEmail());
            softAssertion.assertThat(expectedErrorMessage.getMailingDate()).isEqualTo(actualErrorMessage.getMailingDate());
        });
    }

    private void verifyErrorMessageDocumentFields(ErrorMessageDocument expectedErrorMessageDocument, ErrorMessageDocument actualErrorMessageDocument) {
        assertSoftly(softAssertion -> {
            softAssertion.assertThat(expectedErrorMessageDocument.getClientId()).isEqualTo(actualErrorMessageDocument.getClientId());
            softAssertion.assertThat(expectedErrorMessageDocument.getFile()).isEqualTo(actualErrorMessageDocument.getFile());
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