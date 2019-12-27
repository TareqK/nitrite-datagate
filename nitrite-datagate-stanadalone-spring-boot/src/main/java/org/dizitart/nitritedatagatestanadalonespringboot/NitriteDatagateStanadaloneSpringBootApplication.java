package org.dizitart.nitritedatagatestanadalonespringboot;

import org.dizitart.nitrite.datagate.spring.boot.configuration.DatagateHandlerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(DatagateHandlerConfiguration.class)
public class NitriteDatagateStanadaloneSpringBootApplication {

  public static void main(String[] args) {
    SpringApplication.run(NitriteDatagateStanadaloneSpringBootApplication.class, args);
  }

}
