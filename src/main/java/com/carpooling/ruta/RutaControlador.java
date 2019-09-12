package com.carpooling.ruta;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
public class RutaControlador {

    private final RepositorioRuta repositorioRuta;
    private final EnsambladorResourceRuta ensambladorResourceRuta;
    RutaControlador(RepositorioRuta repositorioRuta, EnsambladorResourceRuta ensambladorResourceRuta){
        this.repositorioRuta = repositorioRuta;
        this.ensambladorResourceRuta = ensambladorResourceRuta;
    }

    @GetMapping("/rutas")
    Resources<Resource<Ruta>> todo(){
        List<Resource<Ruta>> rutas = repositorioRuta.findAll().stream()
                .map(ensambladorResourceRuta::toResource)
                .collect(Collectors.toList());
        return  new Resources<>(rutas,
                linkTo(methodOn(RutaControlador.class).todo()).withSelfRel());
    }

    @PostMapping("/rutas")
    Ruta nuevaRuta(@RequestBody Ruta nuevo){
        return repositorioRuta.save(nuevo);
    }

    @GetMapping("/rutas/{id}")
    Resource<Ruta> uno(@PathVariable Long id){
        Ruta ruta = repositorioRuta.findById(id).orElseThrow(()-> new ExcepcionRutaNoEncontrada(id));
        return ensambladorResourceRuta.toResource(ruta);
    }

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
