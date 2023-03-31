package by.afinny.infoservice.controller;

import by.afinny.infoservice.service.impl.ErrorMessagesServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

import static by.afinny.infoservice.controller.ErrorMessagesController.URL_ERROR_MESSAGE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ErrorMessagesController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class ErrorMessagesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ErrorMessagesServiceImpl errorMessagesService;

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
    }

    @Test
    @DisplayName("If successfully save document should return 200")
    void uploadErrorMessageReport_shouldReturnStatusOk() throws Exception {
        //ACT&VERIFY
        mockMvc.perform(
                multipart(URL_ERROR_MESSAGE)
                        .file("file", file.getBytes())
                        .param("clientId", CLIENT_ID.toString())
                        .param("userEmail", USER_EMAIL)
                        .param("message", MESSAGE)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("If save not successful then return INTERNAL SERVER ERROR")
    void uploadErrorMessageReport_ifNotSave_thenReturnInternalServerError() throws Exception {
        //ARRANGE
        doThrow(RuntimeException.class).when(errorMessagesService).uploadErrorMessagesReport(any(MultipartFile.class),
                any(String.class), any(String.class), any(UUID.class));

        //ACT&VERIFY
        mockMvc.perform(
                        multipart(URL_ERROR_MESSAGE)
                                .file("file", file.getBytes())
                                .param("clientId", CLIENT_ID.toString())
                                .param("userEmail", USER_EMAIL)
                                .param("message", MESSAGE)
                                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isInternalServerError());
    }
}