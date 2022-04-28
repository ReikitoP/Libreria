package com.example.libreria.servicios;

import com.example.libreria.entidades.Autor;
import com.example.libreria.errores.ErroresServicios;
import com.example.libreria.repositorio.AutorRepositorio;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutorServicios {

    @Autowired
    private AutorRepositorio autorRepositorio;

    @Transactional
    public void cargarAutor(String nombre) throws ErroresServicios {
        validar(nombre);
        Autor autor = new Autor();
        autor.setNombre(nombre);
        autorRepositorio.save(autor);

    }

    private void validar(String nombre) throws ErroresServicios {
        if (nombre == null || nombre.isEmpty()) {
            throw new ErroresServicios("El nombre no puede estar vacio");
        }
    }

    @Transactional
    public void modificarAutor(String nombre, String id) throws ErroresServicios {
        Autor autor = autorRepositorio.buscarPorId(id);
        validar(nombre);
        autor.setNombre(nombre);
        autorRepositorio.save(autor);

    }

    public List<Autor> listarAutores() {
        List<Autor> autores = autorRepositorio.ListarAutores();
        return autores;
    }

    public Autor listarAutor(String id) {
        Autor autor = autorRepositorio.buscarPorId(id);
        return autor;
    }

    @Transactional
    public void baja(String id) {
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Autor autor = respuesta.get();
            autor.setAlta(false);

            autorRepositorio.save(autor);
        }
    }

    @Transactional
    public void alta(String id) throws ErroresServicios {
        Optional<Autor> resp = autorRepositorio.findById(id);
        if (resp.isPresent()) {
            Autor autor = resp.get();
            autor.setAlta(true);

            autorRepositorio.save(autor);
        } else {
            throw new ErroresServicios("no se encontro el autor que busca");
        }
    }
}
