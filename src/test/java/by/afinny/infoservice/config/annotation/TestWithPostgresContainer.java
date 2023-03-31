package by.afinny.infoservice.config.annotation;

import by.afinny.infoservice.config.initializer.PostgresContainerInitializer;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(
    webEnvironment = WebEnvironment.RANDOM_PORT
)
@Testcontainers
@ActiveProfiles("integration")
@AutoConfigureMockMvc
@ContextConfiguration(initializers = PostgresContainerInitializer.class)
public @interface TestWithPostgresContainer {
}
