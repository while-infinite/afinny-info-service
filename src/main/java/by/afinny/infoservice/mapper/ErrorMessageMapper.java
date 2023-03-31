package by.afinny.infoservice.mapper;

import by.afinny.infoservice.document.ErrorMessageDocument;
import by.afinny.infoservice.entity.ErrorMessage;
import by.afinny.infoservice.exception.BytesFromFileException;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.util.UUID;

@Mapper
public interface ErrorMessageMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "clientId", target = "clientId")
    @Mapping(qualifiedByName = "getBytesFromFIle", target = "file")
    ErrorMessageDocument toErrorMessageDocument(MultipartFile file, UUID clientId, UUID id);

    @Mapping(source = "message", target = "message")
    @Mapping(source = "userId", target = "userId")
    @Mapping(source = "userEmail", target = "userEmail")
    @Mapping(source = "mailingDate", target = "mailingDate")
    ErrorMessage toErrorMessage(String message, UUID userId, String userEmail, Instant mailingDate);

    @Named("getBytesFromFIle")
    default byte[] getBytesFromFIle(MultipartFile file){
        try {
            return file.getBytes();
        } catch (IOException e) {
            throw new BytesFromFileException("Can't get bytes from file");
        }
    }

    @Named("generateUUID")
    default UUID generateUUID(){
        return UUID.randomUUID();
    }
}
