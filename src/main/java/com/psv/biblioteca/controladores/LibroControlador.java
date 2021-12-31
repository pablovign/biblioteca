
package com.psv.biblioteca.controladores;

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
    public String mostrarLibros(){
        return "libros.html";
    }
    
    @GetMapping("/form-agregar-libro")
    public String mostrarFormAgregarLibro(ModelMap modelo){
        modelo.put("autores", autorServicio.leerAutores());
        modelo.put("editoriales", editorialServicio.leerEditoriales());
        
        return "form-agregar-libro.html";
    }
    
    @PostMapping("/form-agregar-libro")
    public String crearLibro(@RequestParam Long isbn, @RequestParam String titulo, @RequestParam Integer anio, @RequestParam Integer ejemplares,
            @RequestParam String idAutor, @RequestParam String idEditorial){
        
        try {
            libroServicio.crearLibro(isbn, titulo, anio, ejemplares, idAutor, idEditorial);
            
            return "redirect:/libro/libros";
        } catch (Exception e) {
            return null;
        }
    }
}
