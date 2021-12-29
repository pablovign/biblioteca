
package com.psv.biblioteca.controladores;

import com.psv.biblioteca.errores.ErrorServicio;
import com.psv.biblioteca.servicios.EditorialServicio;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/editorial")
public class EditorialControlador {
    
    @Autowired
    private EditorialServicio editorialServicio;
    
    @GetMapping("/editoriales")
    public String mostrarEditoriales(ModelMap modelo){
        modelo.put("editoriales", editorialServicio.leerEditoriales());
        
        return "editoriales.html";
    }
    
    @GetMapping("/form-editoriales")
    public String mostrarFormularioEditoriales(){
        return "form-editoriales.html";
    }
    
    @PostMapping("/form-editoriales")
    public String crearEditorial(@RequestParam String nombre, ModelMap modelo){
        try {
            editorialServicio.crearEditorial(nombre);
            
            return "redirect:/editorial/editoriales";
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
            
            return "form-editoriales.html";
        }
    }
    
    @GetMapping("/alta-baja-editorial")
    public String darAltaBajaEditorial(@RequestParam String id, ModelMap modelo){
        try {
            editorialServicio.darAltaBajaEditorial(id);
            
            return "redirect:/editorial/editoriales";
        } catch (ErrorServicio ex) {
            modelo.put("error", ex.getMessage());
            
            return "editoriales.html";
        }
    }
}
