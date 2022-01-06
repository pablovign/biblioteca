
package com.psv.biblioteca.servicios;

import com.psv.biblioteca.entidades.Foto;
import com.psv.biblioteca.repositorios.FotoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FotoServicio {
    
    @Autowired
    private FotoRepositorio fotoRepositorio;
    
    public Foto guardarFoto(MultipartFile archivo){
        if(archivo != null && !archivo.isEmpty()){
            try {
                Foto foto = new Foto();
                
                foto.setMime(archivo.getContentType());
                foto.setNombre(archivo.getName());
                foto.setContenido(archivo.getBytes());
                
                return fotoRepositorio.save(foto);
            } 
            catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return null;
    }
}
