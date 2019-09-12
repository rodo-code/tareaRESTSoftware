package com.carpooling.ruta;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Ruta {
    private @Id @GeneratedValue Long id;
    private String zonaInicio;
    private String zonaFinal;
    private float distancia;

    Ruta(){}

    Ruta(String zonaInicio,String zonaFinal){
        this.zonaInicio = zonaInicio;
        this.zonaFinal = zonaFinal;
    }
    Ruta(String zonaInicio,String zonaFinal,float distancia){
        this.zonaInicio = zonaInicio;
        this.zonaFinal = zonaFinal;
        this.distancia = distancia;
    }
}
