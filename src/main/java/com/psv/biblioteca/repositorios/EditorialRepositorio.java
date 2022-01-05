
package com.psv.biblioteca.repositorios;

import com.psv.biblioteca.entidades.Editorial;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EditorialRepositorio extends JpaRepository<Editorial, String>{
    
    @Query("SELECT e FROM Editorial e WHERE e.alta = true ORDER BY e.nombre")
    public List<Editorial> buscarEditorialesAlta();
}
