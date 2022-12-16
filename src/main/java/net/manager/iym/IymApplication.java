package net.manager.iym;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class IymApplication {

    public static void main(String[] args) {
        SpringApplication.run(IymApplication.class, args);
    }

}
