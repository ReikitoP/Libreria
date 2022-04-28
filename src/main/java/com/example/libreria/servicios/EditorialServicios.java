package com.example.libreria.servicios;

import com.example.libreria.entidades.Editorial;
import com.example.libreria.errores.ErroresServicios;
import com.example.libreria.repositorio.EditorialRepositorio;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EditorialServicios {
    @Autowired
    private EditorialRepositorio editorialRepositorio;

    @Transactional
    public void cargarEditorial(String nombre) throws ErroresServicios {
        validar(nombre);
        Editorial editorial = new Editorial();
        editorial.setNombre(nombre);
        editorialRepositorio.save(editorial);
    }

    private void validar(String nombre) throws ErroresServicios {
        if (nombre == null || nombre.isEmpty()) {
            throw new ErroresServicios("El nombre no puede estar vacio");
        }
    }

    @Transactional
    public void modificarEditorial(String nombre, String id) throws ErroresServicios {
        Editorial editorial = editorialRepositorio.buscarPorId(id);
        validar(nombre);
        editorial.setNombre(nombre);
        editorialRepositorio.save(editorial);
    }

    public List<Editorial> listarEditoriales() {
        List<Editorial> editoriales = editorialRepositorio.ListarEditoriales();
        return editoriales;
    }

    public Editorial listarEditorial(String id) {
        Editorial editorial = editorialRepositorio.buscarPorId(id);
        return editorial;
    }

    @Transactional
    public void baja(String id){
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if(respuesta.isPresent()){
        Editorial editorial =respuesta.get();
        editorial.setAlta(false);
        
        editorialRepositorio.save(editorial);
        }
    }
@Transactional
    public void alta(String id) throws ErroresServicios {
        Optional<Editorial> resp = editorialRepositorio.findById(id);
        if(resp.isPresent()){
        Editorial editorial =resp.get();
        editorial.setAlta(true);
        
        editorialRepositorio.save(editorial);
        }else{
            throw new ErroresServicios("no se encontro la editorial que busca");
        }
    }
}
