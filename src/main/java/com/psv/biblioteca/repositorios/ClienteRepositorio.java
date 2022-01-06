
package com.psv.biblioteca.repositorios;

import com.psv.biblioteca.entidades.Cliente;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepositorio extends JpaRepository<Cliente, String>{
    
    @Query("SELECT c FROM Cliente c WHERE c.alta = true ORDER BY c.documento")
    public List<Cliente> buscarClientesAlta();
}
