
package com.psv.biblioteca.controladores;

import com.psv.biblioteca.entidades.Autor;
import com.psv.biblioteca.errores.ErrorServicio;
import com.psv.biblioteca.servicios.AutorServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/autor")
public class AutorControlador {
    
    @Autowired
    private AutorServicio autorServicio;
    
    @GetMapping("/autores")
    public String mostrarAutores(ModelMap modelo){
        modelo.put("autores", autorServicio.leerAutores());
        
        return "autores.html";
    }
    
    @GetMapping("/form-autor")
    public String mostrarFormAutor(){
       
        return "form-autor.html";
    }
    
    @PostMapping("/form-autor")
    public String formAutor(@RequestParam String nombre, ModelMap modelo){
        try {
            autorServicio.crearAutor(nombre);
            
            return "redirect:/autor/autores";
        } catch (ErrorServicio e) {
            modelo.put("error", e.getMessage());
            
            return "form-autor.html";
        }
    }
    
    @GetMapping("/alta-baja-autor/{id}")
    public String darAltaBajaAutor(@PathVariable String id, ModelMap modelo){
        try {
            autorServicio.darAltaBajaAutor(id);
            
            return "redirect:/autor/autores";
        } catch (ErrorServicio e) {
            modelo.put("error", e.getMessage());
            
            return "redirect:/autor/autores";
        }
    }
    
    @GetMapping("/form-actualizar-autor/{id}")
    public String mostrarFormActualizarAutor(@PathVariable String id, ModelMap modelo){
        try {
            Autor autor = autorServicio.buscarAutorPorId(id);
            
            modelo.put("autor", autor);
            
            return "form-actualizar-autor.html";
        } catch (ErrorServicio e) {
            modelo.put("error", e.getMessage());
            
            return "redirect:/autor/autores";
        }
    }
    
    @PostMapping("/form-actualizar-autor/{id}")
    public String actualizarAutor(@PathVariable String id, @RequestParam String nombre, ModelMap modelo){
        try {
            autorServicio.actualizarAutor(id, nombre);
            
            return "redirect:/autor/autores";
        } catch (ErrorServicio e) {
            modelo.put("error", e.getMessage());
            
            return "redirect:/autor/autores";
        }
    }
    
    @GetMapping("/borrar-autor/{id}")
    public String borrarAutor(@PathVariable String id, ModelMap modelo){
        try {
            autorServicio.borrarAutor(id);
            
            return "redirect:/autor/autores";
        } catch (ErrorServicio e) {
            modelo.put("error", e.getMessage());
            
            return "redirect:/autor/autores";
        }
    }
}
