package by.afinny.infoservice.repository;

import by.afinny.infoservice.entity.ErrorMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ErrorMessageRepository extends JpaRepository<ErrorMessage, UUID> {
}
