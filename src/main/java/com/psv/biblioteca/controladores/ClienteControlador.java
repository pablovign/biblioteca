
package com.psv.biblioteca.controladores;

import com.psv.biblioteca.errores.ErrorServicio;
import com.psv.biblioteca.servicios.ClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/cliente")
public class ClienteControlador {
    
    @Autowired
    private ClienteServicio clienteServicio;
    
    @GetMapping("/clientes")
    public String mostrarPagClientes(ModelMap modelo){
        modelo.put("clientes", clienteServicio.buscarClientes());
        
        return "clientes.html";
    }
    
    @GetMapping("/form-cliente")
    public String mostrarFromCliente(){
        return "form-cliente.html";
    }
    
    @PostMapping("/form-cliente")
    public String crearCliente(MultipartFile archivo, @RequestParam(required = false) Long documento, @RequestParam String nombre, 
            @RequestParam String apellido, @RequestParam(required = false) String telefono, ModelMap modelo){
        try{
            clienteServicio.guardarCliente(archivo, documento, nombre, apellido, telefono);
            
            return "redirect:/cliente/clientes";
        }
        catch(ErrorServicio e){
            modelo.put("error", e.getMessage());
            
            return "form-cliente.html";
        }
    }
}
