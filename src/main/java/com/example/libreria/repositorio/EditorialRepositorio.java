package com.example.libreria.repositorio;

import com.example.libreria.entidades.Editorial;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EditorialRepositorio extends JpaRepository<Editorial, String> {
    @Query("SELECT c FROM Editorial c")
    public List<Editorial> ListarEditoriales();

    @Query("SELECT c FROM Editorial c WHERE c.id = :id")
    public Editorial buscarPorId(@Param("id") String id);
}
