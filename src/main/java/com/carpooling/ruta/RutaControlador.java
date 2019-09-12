package com.carpooling.ruta;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.net.URI;
import java.net.URISyntaxException;
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
    ResponseEntity<?> nuevaRuta(@RequestBody Ruta nuevo) throws URISyntaxException {
        Resource<Ruta> resource = ensambladorResourceRuta.toResource(repositorioRuta.save(nuevo));
        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @GetMapping("/rutas/{id}")
    Resource<Ruta> uno(@PathVariable Long id){
        Ruta ruta = repositorioRuta.findById(id).orElseThrow(()-> new ExcepcionRutaNoEncontrada(id));
        return ensambladorResourceRuta.toResource(ruta);
    }

    @PutMapping("/rutas/{id}")
    ResponseEntity<?> reemplazarRuta(@RequestBody Ruta nuevaruta, @PathVariable Long id) throws URISyntaxException{
        Ruta rutaActualizada = repositorioRuta.findById(id)
                .map(ruta -> {
                    ruta.setZonaInicio(nuevaruta.getZonaInicio());
                    ruta.setZonaFinal(nuevaruta.getZonaFinal());
                    ruta.setDistancia(nuevaruta.getDistancia());
                    return repositorioRuta.save(ruta);
                        })
                .orElseGet(()->{
                   nuevaruta.setId(id);
                   return repositorioRuta.save(nuevaruta);
                });
        Resource<Ruta> resource = ensambladorResourceRuta.toResource(rutaActualizada);
        return ResponseEntity.created(new URI(resource.getId().expand().getHref())).body(resource);
    }

    @DeleteMapping("/rutas/{id}")
    ResponseEntity<?> borrarRuta(@PathVariable Long id){
        repositorioRuta.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
