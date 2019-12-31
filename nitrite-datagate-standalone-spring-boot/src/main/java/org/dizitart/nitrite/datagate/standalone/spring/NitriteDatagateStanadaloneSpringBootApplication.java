package org.dizitart.nitrite.datagate.standalone.spring;

import org.dizitart.nitrite.datagate.config.Datagate;
import org.dizitart.nitrite.datagate.impl.config.DatagateConfigurationImpl;
import org.dizitart.nitrite.datagate.spring.boot.configuration.DatagateHandlerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(DatagateHandlerConfiguration.class)
public class NitriteDatagateStanadaloneSpringBootApplication {

  public static void main(String[] args) {
    Datagate.setConfiguration(new DatagateConfigurationImpl());
    SpringApplication.run(NitriteDatagateStanadaloneSpringBootApplication.class, args);
  }

}
