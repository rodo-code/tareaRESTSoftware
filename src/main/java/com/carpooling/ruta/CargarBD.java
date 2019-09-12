package com.carpooling.ruta;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class CargarBD {

    @Bean
    CommandLineRunner iniciarDB(RepositorioRuta repositorioRuta){
        return args -> {
          log.info("Precargando " + repositorioRuta.save(new Ruta("Miraflores","Sopocachi")));
          log.info("Precargando " + repositorioRuta.save(new Ruta("Los Pinos","San Miguel")));
        };
    }
}
