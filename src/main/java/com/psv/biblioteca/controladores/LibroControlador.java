
package com.psv.biblioteca.controladores;

import com.psv.biblioteca.entidades.Libro;
import com.psv.biblioteca.errores.ErrorServicio;
import com.psv.biblioteca.servicios.AutorServicio;
import com.psv.biblioteca.servicios.EditorialServicio;
import com.psv.biblioteca.servicios.LibroServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/libro")
public class LibroControlador {
    
    @Autowired
    private AutorServicio autorServicio;
    
    @Autowired
    private EditorialServicio editorialServicio;
    
    @Autowired
    private LibroServicio libroServicio;
    
    @GetMapping("/libros")
    public String mostrarLibros(ModelMap model){
        model.put("libros", libroServicio.leerLibros());
        
        return "libros.html";
    }
    
    @GetMapping("/form-agregar-libro")
    public String mostrarFormAgregarLibro(ModelMap modelo){
        modelo.put("autores", autorServicio.leerAutores());
        modelo.put("editoriales", editorialServicio.leerEditoriales());
        
        return "form-agregar-libro.html";
    }
    
    @PostMapping("/form-agregar-libro")
    public String crearLibro(@RequestParam(required = false) Long isbn, @RequestParam String titulo, @RequestParam (required = false) Integer anio, 
            @RequestParam (required = false) Integer ejemplares , @RequestParam String idAutor, @RequestParam String idEditorial, ModelMap modelo){
        
        try {
            libroServicio.crearLibro(isbn, titulo, anio, ejemplares, idAutor, idEditorial);
            
            return "redirect:/libro/libros";
        } catch (ErrorServicio e) {
            modelo.put("error", e.getMessage());
            modelo.put("autores", autorServicio.leerAutores());
            modelo.put("editoriales", editorialServicio.leerEditoriales());
            
            return "form-agregar-libro.html";
        }
    }
    
    @GetMapping("/alta-baja-libro")
    public String darAltaBajaLibro(String id, ModelMap modelo){
        try{
            libroServicio.darAltaBajaLibro(id);
            
            return "redirect:/libro/libros";
        }
        catch(ErrorServicio e){
            modelo.put("error", e.getMessage());
            
            return "redirect:/libro/libros";
        }
    }
}
