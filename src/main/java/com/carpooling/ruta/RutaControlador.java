package com.carpooling.ruta;

import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
public class RutaControlador {

    private final RepositorioRuta repositorioRuta;
    RutaControlador(RepositorioRuta repositorioRuta){
        this.repositorioRuta = repositorioRuta;
    }

    @GetMapping("/rutas")
    List<Ruta> todo(){
        return repositorioRuta.findAll();
    }

    @PostMapping("/rutas")
    Ruta nuevaRuta(@RequestBody Ruta nuevo){
        return repositorioRuta.save(nuevo);
    }

    @GetMapping("/rutas/{id}")
    Resource<Ruta> uno(@PathVariable Long id){
        Ruta ruta = repositorioRuta.findById(id).orElseThrow(()-> new ExcepcionRutaNoEncontrada(id));
        return new Resource<>(ruta,
                linkTo(methodOn(RutaControlador.class).uno(id)).withSelfRel(),
                linkTo(methodOn(RutaControlador.class).todo()).withRel("rutas"));
    }
    /*Ruta una(@PathVariable Long id){
        return repositorioRuta.findById(id).orElseThrow(()->new ExcepcionRutaNoEncontrada(id));
    }*/

    @PutMapping("/rutas/{id}")
    Ruta reemplazarRuta(@RequestBody Ruta nuevaruta,@PathVariable Long id){
        return repositorioRuta.findById(id)
                .map(ruta -> {
                    ruta.setZonaInicio(nuevaruta.getZonaInicio());
                    ruta.setZonaFinal(nuevaruta.getZonaFinal());
                    return repositorioRuta.save(ruta);
                        })
                .orElseGet(()->{
                   nuevaruta.setId(id);
                   return repositorioRuta.save(nuevaruta);
                });
    }

    @DeleteMapping("/rutas/{id}")
    void borrarRuta(@PathVariable Long id){
        repositorioRuta.deleteById(id);
    }
}
