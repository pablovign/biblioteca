
package com.psv.biblioteca.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cliente")
public class ClienteControlador {
    
    @GetMapping("/clientes")
    public String mostrarPagClientes(){
        return "clientes.html";
    }
}
