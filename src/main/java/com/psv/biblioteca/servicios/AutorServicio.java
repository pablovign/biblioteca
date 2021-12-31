
package com.psv.biblioteca.servicios;

import com.psv.biblioteca.entidades.Autor;
import com.psv.biblioteca.errores.ErrorServicio;
import com.psv.biblioteca.repositorios.AutorRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AutorServicio {
    
    @Autowired
    private AutorRepositorio autorRepositorio;
    
    @Transactional
    public void crearAutor(String nombre) throws ErrorServicio{
        validar(nombre);
        
        Autor autor = new Autor();
        
        autor.setNombre(nombre);
        autor.setAlta(true);
        
        autorRepositorio.save(autor);
    }
    
    @Transactional(readOnly = true)
    public List<Autor> leerAutores(){
        return autorRepositorio.findAll(Sort.by(Sort.Direction.ASC, "nombre"));
    }
    
    @Transactional
    public void darAltaBajaAutor(String id) throws ErrorServicio{
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        
        if(respuesta.isPresent()){
            Autor autor = respuesta.get();
            
            if(autor.getAlta() == true){
                autor.setAlta(false);
            }
            else{
                autor.setAlta(true);
            }
            
            autorRepositorio.save(autor);
        }
        else{
            throw new ErrorServicio("No se encontró el autor solicitado.");
        }
    }
    
    @Transactional
    public void actualizarAutor(String id, String nombre) throws ErrorServicio{
        validar(nombre);
        
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        
        if(respuesta.isPresent()){
            Autor autor = respuesta.get();
            
            autor.setNombre(nombre);
            
            autorRepositorio.save(autor);
        }
        else{
            throw new ErrorServicio("No se encontró el autor solicitado.");
        }
    }
    
    @Transactional(readOnly = true)
    public Autor buscarAutorPorId(String id) throws ErrorServicio{
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        
        if(respuesta.isPresent()){
            Autor autor = respuesta.get();
            
            return autor;
        }
        else{
            throw new ErrorServicio("No se encontró el autor solicitado.");
        }
    }
    
    @Transactional
    public void borrarAutor(String id) throws ErrorServicio{
        Autor autor = buscarAutorPorId(id);
        
        autorRepositorio.deleteById(id);
    }
    
    public void validar(String nombre) throws ErrorServicio{
        if(nombre == null || nombre.isEmpty()){
            throw new ErrorServicio("Ingresar nombre del autor.");
        }
    }
}
