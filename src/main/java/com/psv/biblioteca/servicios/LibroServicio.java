package com.psv.biblioteca.servicios;

import com.psv.biblioteca.entidades.Autor;
import com.psv.biblioteca.entidades.Editorial;
import com.psv.biblioteca.entidades.Libro;
import com.psv.biblioteca.errores.ErrorServicio;
import com.psv.biblioteca.repositorios.LibroRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LibroServicio {

    @Autowired
    private AutorServicio autorServicio;

    @Autowired
    private EditorialServicio editorialServicio;

    @Autowired
    private LibroRepositorio libroRepositorio;

    @Transactional
    public void crearLibro(Long isbn, String titulo, Integer anio, Integer ejemplares, String idAutor, String idEditorial) throws ErrorServicio {
        validar(isbn, titulo, anio, ejemplares, idEditorial, idAutor);

        Autor autor = autorServicio.buscarAutorPorId(idAutor);

        Editorial editorial = editorialServicio.buscarEditorialPorId(idEditorial);

        Libro libro = new Libro();

        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setAnio(anio);
        libro.setEjemplares(ejemplares);
        libro.setEjemplaresPrestados(0);
        libro.setEjemplaresRestantes(ejemplares);
        libro.setAlta(true);
        libro.setAutor(autor);
        libro.setEditorial(editorial);

        libroRepositorio.save(libro);
    }

    @Transactional(readOnly = true)
    public List<Libro> leerLibros() {
        return libroRepositorio.findAll(Sort.by(Sort.Direction.ASC, "autor.nombre"));
    }

    @Transactional(readOnly = true)
    public Libro buscarLibroPorId(String id) throws ErrorServicio {
        Optional<Libro> respuesta = libroRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();

            return libro;
        } else {
            throw new ErrorServicio("No se encontró el libro solicitado.");
        }
    }

    @Transactional
    public void darAltaBajaLibro(String id) throws ErrorServicio {
        Libro libro = buscarLibroPorId(id);

        if (libro.getAlta() == true) {
            libro.setAlta(false);
        } else {
            libro.setAlta(true);
        }

        libroRepositorio.save(libro);
    }

    @Transactional
    public void borrarLibro(String id) throws ErrorServicio {
        Libro libro = buscarLibroPorId(id);

        libroRepositorio.delete(libro);
    }

    @Transactional
    public void actualizarLibro(String id, Long isbn, String titulo, Integer anio, Integer ejemplares, String idAutor, String idEditorial) throws ErrorServicio {
        validar(isbn, titulo, anio, ejemplares, idEditorial, idAutor);

        Libro libro = buscarLibroPorId(id);

        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setAnio(anio);
        libro.setEjemplares(ejemplares);
        libro.setEjemplaresRestantes(ejemplares - libro.getEjemplaresPrestados());
        libro.setAlta(true);

        Autor autor = autorServicio.buscarAutorPorId(idAutor);
        libro.setAutor(autor);

        Editorial editorial = editorialServicio.buscarEditorialPorId(idEditorial);
        libro.setEditorial(editorial);

        libroRepositorio.save(libro);
    }

    public void validar(Long isbn, String titulo, Integer anio, Integer ejemplares, String idEditorial, String idAutor) throws ErrorServicio {
        if (isbn == null) {
            throw new ErrorServicio("Ingresar ISBN del libro.");
        }

        if (isbn < 0) {
            throw new ErrorServicio("ISBN incorrecto.");
        }

        for (Libro libro : leerLibros()) {
            if (libro.getIsbn().equals(isbn)) {
                throw new ErrorServicio("ISBN de libro ya ingresado.");
            }
        }

        if (titulo == null || titulo.isEmpty()) {
            throw new ErrorServicio("Ingresar título del libro.");
        }

        if (anio == null) {
            throw new ErrorServicio("Ingresar año del libro.");
        }

        if (anio < 0) {
            throw new ErrorServicio("Año incorrecto.");
        }

        if (ejemplares == null) {
            throw new ErrorServicio("Ingresar ejemplares del libro.");
        }

        if (ejemplares < 0) {
            throw new ErrorServicio("Ejemplares incorrectos.");
        }

        if (idAutor == null || idAutor.isEmpty()) {
            throw new ErrorServicio("Ingresar el autor del libro.");
        }
        
        if (idEditorial == null || idEditorial.isEmpty()) {
            throw new ErrorServicio("Ingresar la editorial del libro.");
        }
    }
}
