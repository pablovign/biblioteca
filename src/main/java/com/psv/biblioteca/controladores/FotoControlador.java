
package com.psv.biblioteca.controladores;

import com.psv.biblioteca.entidades.Cliente;
import com.psv.biblioteca.errores.ErrorServicio;
import com.psv.biblioteca.servicios.ClienteServicio;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/foto")
public class FotoControlador {
    
    @Autowired
    private ClienteServicio clienteServicio;
    
    @GetMapping("/mostrar-foto/{id}")
    public ResponseEntity<byte[]> mostrarFoto(@PathVariable String id){      
        try {
            Cliente cliente = clienteServicio.buscarClienteId(id);
            
            if(cliente.getFoto() == null){
                throw new ErrorServicio("El usuario no tiene foto.");
            }
            
            byte[] foto = cliente.getFoto().getContenido();
            
            HttpHeaders headers = new HttpHeaders();
            
            headers.setContentType(MediaType.IMAGE_JPEG);
            
            return new ResponseEntity<>(foto, headers, HttpStatus.OK);   
        } catch (ErrorServicio ex) {
            Logger.getLogger(FotoControlador.class.getName()).log(Level.SEVERE, null, ex);
            
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }       
    }
}
