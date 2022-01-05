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
import org.springframework.web.bind.annotation.PathVariable;
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
    public String mostrarLibros(ModelMap model) {
        model.put("libros", libroServicio.leerLibros());

        return "libros.html";
    }

    @GetMapping("/form-agregar-libro")
    public String mostrarFormAgregarLibro(ModelMap modelo) {
        modelo.put("autores", autorServicio.buscarAutoresAlta());
        modelo.put("editoriales", editorialServicio.buscarEditorialesAlta());

        return "form-agregar-libro.html";
    }

    @PostMapping("/form-agregar-libro")
    public String crearLibro(@RequestParam(required = false) Long isbn, @RequestParam String titulo, @RequestParam(required = false) Integer anio,
            @RequestParam(required = false) Integer ejemplares, @RequestParam(required = false) String idAutor, @RequestParam(required = false) String idEditorial, ModelMap modelo) {

        try {
            libroServicio.crearLibro(isbn, titulo, anio, ejemplares, idAutor, idEditorial);

            return "redirect:/libro/libros";
        } catch (ErrorServicio e) {
            modelo.put("error", e.getMessage());
            modelo.put("autores", autorServicio.buscarAutoresAlta());
            modelo.put("editoriales", editorialServicio.buscarEditorialesAlta());

            return "form-agregar-libro.html";
        }
    }

    @GetMapping("/alta-baja-libro")
    public String darAltaBajaLibro(String id, ModelMap modelo) {
        try {
            libroServicio.darAltaBajaLibro(id);

            return "redirect:/libro/libros";
        } catch (ErrorServicio e) {
            modelo.put("error", e.getMessage());

            return "redirect:/libro/libros";
        }
    }

    @GetMapping("/borrar-libro")
    public String borrarLibro(String id, ModelMap modelo) {
        try {
            libroServicio.borrarLibro(id);

            return "redirect:/libro/libros";
        } catch (ErrorServicio e) {
            modelo.put("error", e);

            return "redirect:/libro/libros";
        }
    }

    @GetMapping("/form-actualizar-libro")
    public String mostrarFormActualizarLibro(String id, ModelMap modelo) throws ErrorServicio {
        try {
            modelo.put("libro", libroServicio.buscarLibroPorId(id));
            modelo.put("autores", autorServicio.buscarAutoresAlta());
            modelo.put("editoriales", editorialServicio.buscarEditorialesAlta());

            return "form-actualizar-libro";
        } catch (ErrorServicio e) {
            Libro libro = libroServicio.buscarLibroPorId(id);

            modelo.put("error", e.getMessage());

            return "form-actualizar-libro.html";
        }
    }

    @PostMapping("/form-actualizar-libro/{id}")
    public String actualizarLibro(@PathVariable String id, @RequestParam(required = false) Long isbn, @RequestParam String titulo,
            @RequestParam(required = false) Integer anio, @RequestParam(required = false) Integer ejemplares, @RequestParam(required = false) String idAutor,
            @RequestParam(required = false) String idEditorial, ModelMap modelo) throws ErrorServicio {
        try {
            libroServicio.actualizarLibro(id, isbn, titulo, anio, ejemplares, idAutor, idEditorial);

            return "redirect:/libro/libros";
        } catch (ErrorServicio e) {
            Libro libro = libroServicio.buscarLibroPorId(id);

            modelo.put("libro", libro);
            modelo.put("autores", autorServicio.buscarAutoresAlta());
            modelo.put("editoriales", editorialServicio.buscarEditorialesAlta());
            modelo.put("error", e.getMessage());

            return "form-actualizar-libro.html";
        }
    }
}
