package com.example.libreria.repositorio;

import com.example.libreria.entidades.Cliente;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepositorio extends JpaRepository<Cliente, String>{
    @Query("SELECT c FROM Libro c")
    public List<Cliente> listarClientes();
    
    @Query("SELECT c FROM Libro c WHERE c.id = :id ")
    public Cliente buscarClientePorId(@Param("id") String id);
}
