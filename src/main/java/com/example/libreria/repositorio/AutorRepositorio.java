package com.example.libreria.repositorio;

import com.example.libreria.entidades.Autor;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AutorRepositorio extends JpaRepository<Autor, String>{
    @Query("SELECT c FROM Autor c")
    public List<Autor> ListarAutores();
    
        
    @Query("SELECT c FROM Autor c WHERE c.id = :id")
    public Autor buscarPorId(@Param("id")String id);
}
