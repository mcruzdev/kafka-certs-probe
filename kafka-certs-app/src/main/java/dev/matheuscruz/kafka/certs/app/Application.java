package dev.matheuscruz.kafka.certs.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@SpringBootApplication
@RestController
public class Application {

   private static final Logger logger = LoggerFactory.getLogger(Application.class);

   public static void main(String[] args) {
      SpringApplication.run(Application.class, args);
   }

   @GetMapping("/id")
   public String id() {
      String id = UUID.randomUUID().toString();
      logger.info("generated {}", id);
      return id;
   }

}
