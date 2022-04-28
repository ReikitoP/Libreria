package com.example.libreria.repositorio;

import com.example.libreria.entidades.Prestamos;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PrestamosRepositorio extends JpaRepository<Prestamos, String>{
    @Query("SELECT c FROM Prestamos c")
    public List<Prestamos> listarPrestamos();
    
    @Query("SELECT c FROM Prestamos c WHERE c.id = :id ")
    public Prestamos buscarPrestamosPorId(@Param("id") String id);
}
