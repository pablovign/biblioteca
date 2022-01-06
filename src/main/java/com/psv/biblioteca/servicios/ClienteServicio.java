package com.psv.biblioteca.servicios;

import com.psv.biblioteca.entidades.Cliente;
import com.psv.biblioteca.entidades.Foto;
import com.psv.biblioteca.errores.ErrorServicio;
import com.psv.biblioteca.repositorios.ClienteRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ClienteServicio {

    @Autowired
    private ClienteRepositorio clienteRepositorio;
    
    @Autowired
    private FotoServicio fotoServicio;

    @Transactional(readOnly = true)
    public Cliente buscarClienteId(String id) throws ErrorServicio {
        Optional<Cliente> respuesta = clienteRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Cliente cliente = respuesta.get();

            return cliente;
        } else {
            throw new ErrorServicio("No se encontró el cliente solicitado.");
        }
    }

    @Transactional(readOnly = true)
    public List<Cliente> buscarClientes() {
        return clienteRepositorio.findAll(Sort.by(Sort.Direction.ASC, "documento"));
    }

    @Transactional(readOnly = true)
    public List<Cliente> buscarClientesAlta() {
        return clienteRepositorio.buscarClientesAlta();
    }

    @Transactional
    public void guardarCliente(MultipartFile archivo, Long documento, String nombre, String apellido, String telefono) throws ErrorServicio {
        validar(documento, nombre, apellido);

        Cliente cliente = new Cliente();

        cliente.setDocumento(documento);
        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setTelefono(telefono);
        cliente.setAlta(true);
        
        Foto foto = fotoServicio.guardarFoto(archivo);
        cliente.setFoto(foto);

        clienteRepositorio.save(cliente);
    }

    public void validar(Long documento, String nombre, String apellido) throws ErrorServicio {
        if (documento == null) {
            throw new ErrorServicio("Ingresar documento del cliente.");
        }

        if (documento < 0) {
            throw new ErrorServicio("Documento incorrecto.");
        }

        for (Cliente cliente : buscarClientes()) {
            if (cliente.getDocumento().equals(documento)) {
                throw new ErrorServicio("Documento de cliente ya ingresado.");
            }
        }

        if (nombre.isEmpty() || nombre == null) {
            throw new ErrorServicio("Ingresar nombre del cliente.");
        }

        if (apellido.isEmpty() || apellido == null) {
            throw new ErrorServicio("Ingresar apellido del cliente.");
        }
    }
}
