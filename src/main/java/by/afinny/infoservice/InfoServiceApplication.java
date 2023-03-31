package by.afinny.infoservice;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class InfoServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(InfoServiceApplication.class, args);
    }
}
