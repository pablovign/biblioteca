
package com.psv.biblioteca.servicios;

import com.psv.biblioteca.entidades.Editorial;
import com.psv.biblioteca.errores.ErrorServicio;
import com.psv.biblioteca.repositorios.EditorialRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EditorialServicio {
    
    @Autowired
    private EditorialRepositorio editorialRepositorio;
    
    @Transactional
    public void crearEditorial(String nombre) throws ErrorServicio{
        validar(nombre);
        
        Editorial editorial = new Editorial();
        
        editorial.setNombre(nombre);
        editorial.setAlta(true);
        
        editorialRepositorio.save(editorial);
    }
    
    @Transactional(readOnly = true)
    public List<Editorial> leerEditoriales(){
        return editorialRepositorio.findAll();
    }
    
    @Transactional
    public void darAltaBajaEditorial(String id) throws ErrorServicio{
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        
        if(respuesta.isPresent()){
            Editorial editorial = respuesta.get();
            
            if(editorial.getAlta()){
                editorial.setAlta(false);
            }
            else{
                editorial.setAlta(true);
            }
            
            editorialRepositorio.save(editorial);
        }
        else{
            throw new ErrorServicio("No se encontró la editorial solicitada.");
        }
    }
    
    public void validar(String nombre) throws ErrorServicio{
        if(nombre == null || nombre.isEmpty()){
            throw new ErrorServicio("Ingresar nombre de la editorial.");
        }
    }
}
