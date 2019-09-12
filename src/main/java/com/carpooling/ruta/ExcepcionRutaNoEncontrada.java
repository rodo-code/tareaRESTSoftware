package com.carpooling.ruta;

public class ExcepcionRutaNoEncontrada extends RuntimeException {
    ExcepcionRutaNoEncontrada(Long id){
        super("No se pudo encontrar la ruta "+ id);
    }
}
