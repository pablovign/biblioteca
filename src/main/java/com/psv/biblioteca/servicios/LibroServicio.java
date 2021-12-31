
package com.psv.biblioteca.servicios;

import com.psv.biblioteca.entidades.Autor;
import com.psv.biblioteca.entidades.Editorial;
import com.psv.biblioteca.entidades.Libro;
import com.psv.biblioteca.errores.ErrorServicio;
import com.psv.biblioteca.repositorios.LibroRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void crearLibro(Long isbn, String titulo, Integer anio, Integer ejemplares, String idAutor, String idEditorial) throws ErrorServicio{
        validar(isbn, titulo, anio, ejemplares);
        
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
    
    public void validar(Long isbn, String titulo, Integer anio, Integer ejemplares) throws ErrorServicio{
        if(isbn == null){
            throw new ErrorServicio("Ingresar ISBN del libro.");
        }
        
        if(isbn < 0){
            throw new ErrorServicio("Ingresar ISBN positivo del libro.");
        }
        
        if(titulo == null || titulo.isEmpty()){
            throw new ErrorServicio("Ingresar título del libro.");
        }
        
        if(anio == null){
            throw new ErrorServicio("Ingresar año del libro.");
        }
        
        if(anio < 0){
            throw new ErrorServicio("Ingresar año positivo del libro.");
        }
        
        if(ejemplares == null){
            throw new ErrorServicio("Ingresar ejemplares del libro.");
        }
        
        if(ejemplares < 0){
            throw new ErrorServicio("Ingresar ejemplares positivos del libro.");
        }
    }
}
