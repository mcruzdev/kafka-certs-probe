package dev.matheuscruz.kafka.certs.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("kafka.cert")
public class KafkaCertProperties {

   /**
    * Whether mock is enabled or not.
    */
   private boolean mock;

   public boolean isMock() {
      return mock;
   }
}
