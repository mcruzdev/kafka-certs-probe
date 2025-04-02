package dev.matheuscruz.kafka.certs.autoconfigurations;

import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@AutoConfiguration
public class KafkaCertAutoConfiguration {

   private static final Logger logger = LoggerFactory.getLogger(KafkaCertAutoConfiguration.class);
   private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
   private final ExecutorService asyncExecutor = Executors.newFixedThreadPool(2);

   public KafkaCertAutoConfiguration(ApplicationContext context) {
   }

   @Bean
   @ConditionalOnProperty(name = "kafka.cert.mock", havingValue = "false", matchIfMissing = true)
   public InitializingBean generateRealCertificate() {
      return () -> {
         logger.info("generating real certificate");
         scheduler.scheduleAtFixedRate(this::runAsync, 0, 1, TimeUnit.SECONDS);
      };
   }

   @Bean
   @ConditionalOnProperty(name = "kafka.cert.mock", havingValue = "true")
   public InitializingBean generateCertificate() {
      return () -> {
         logger.info("generating mock certificate");
      };
   }

   private void runAsync() {
      CompletableFuture.runAsync(() -> {
         logger.info("checking certificate");
      }, asyncExecutor).exceptionally(throwable -> {
         logger.error("error checking certificate", throwable);
         return null;
      }).thenApply(unused -> {
         logger.info("certificate checked");
         return null;
      });
   }

   @PreDestroy
   void destroy() {
      scheduler.shutdown();
   }
}
