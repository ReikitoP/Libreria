package com.example.libreria.repositorio;

import com.example.libreria.entidades.Libro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepositorio extends JpaRepository<Libro, String>{
    @Query("SELECT c FROM Libro c")
    public List<Libro> listarLibros();
    
    @Query("SELECT c FROM Libro c WHERE c.id = :id ")
    public Libro buscarLibroPorId(@Param("id") String id);
}
