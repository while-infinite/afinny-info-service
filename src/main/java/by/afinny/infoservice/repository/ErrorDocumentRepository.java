package by.afinny.infoservice.repository;

import by.afinny.infoservice.document.ErrorMessageDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface ErrorDocumentRepository extends MongoRepository<ErrorMessageDocument, UUID> {
}
