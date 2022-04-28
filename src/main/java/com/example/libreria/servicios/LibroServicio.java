package com.example.libreria.servicios;

import com.example.libreria.entidades.Autor;
import com.example.libreria.entidades.Editorial;
import com.example.libreria.entidades.Libro;
import com.example.libreria.errores.ErroresServicios;
import com.example.libreria.repositorio.AutorRepositorio;
import com.example.libreria.repositorio.EditorialRepositorio;
import com.example.libreria.repositorio.LibroRepositorio;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibroServicio {
    //llamamos al repositorio, lo vamos a crear como atributo de la clase
    //marcamos el atributo de private LibroRepositor libroRepositorio como autowired
    //Le estamos diciendo al servidor de aplicaciones que este varible la inicialice, entonces no hace falta inicializar esa variable
    //la variable la inicializa el servior de aplicaciones

    @Autowired
    private LibroRepositorio libroRepositorio;
    @Autowired
    private AutorRepositorio autorRepositorio;

    @Autowired
    private EditorialRepositorio editorialRepositorio;

    //todos estos parametros , todos estos datos los recibimos de un formulario web
    //o de una aplicacion movil
    //los datos se van a transformar en una entidad de tipo libro
    //una vez que creamos esa clase o ese objeto del tipo libro, le vamos a decir al repsotorio que lo guarde o lo almacene en la base de datos
    //el respositorio es quien se va a encargar de transformar el objeto en una o mas tablas de la base de datos
    //no siempre el usuario nos carga la informacion de la manera ideal, puede que alguno de las cosas q tenga que cargar este vacio
    //o que el mail sea vacio o nulo, o q no nos cargue el apellido o el nombre, que son escenciales
    //entonces nostros en lugar de persistir lo q deberiamos hacer es validar
    @Transactional
    public void crearLibro(Long isbn, String titulo, Integer anio, Integer ejemplares,
            Integer ejemplaresPrestados, Autor nombreAutor, Editorial nombreEditorial)
            throws ErroresServicios {

        validacion(isbn, titulo, anio, ejemplares, ejemplaresPrestados);

        Libro libro = new Libro();

        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setAnio(anio);
        libro.setEjemplares(ejemplares);
        libro.setEjemplaresPrestados(ejemplaresPrestados);
        libro.setEditorial(nombreEditorial);
        libro.setAutor(nombreAutor);

        //llmamos a la clase de libro repositorio
        //llamamos al metodo save y le pasamos como parametro a libro(objeto creado de la clase libro)
        libroRepositorio.save(libro);
        autorRepositorio.save(nombreAutor);
        editorialRepositorio.save(nombreEditorial);
        System.out.println(libro);

        //El repositorio transforma un obj en una o mas tablas en la base d datos
    }

    @Transactional
    public void modificarLibro(String id, Long isbn, String titulo, Integer anio, Integer ejemplares,
            Integer ejemplaresPrestados) throws ErroresServicios {

        validacion(isbn, titulo, anio, ejemplares, ejemplaresPrestados);

        Libro libro = libroRepositorio.buscarLibroPorId(id);

        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setAnio(anio);
        libro.setEjemplares(ejemplares);
        libro.setEjemplaresPrestados(ejemplaresPrestados);

        libroRepositorio.save(libro);
    }

    @Transactional
    public void baja(String id) {
        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();
            libro.setAlta(false);

            libroRepositorio.save(libro);
        }
    }

    @Transactional
    public void alta(String id) throws ErroresServicios {
        Optional<Libro> resp = libroRepositorio.findById(id);
        if (resp.isPresent()) {
            Libro libro = resp.get();
            libro.setAlta(true);

            libroRepositorio.save(libro);
        } else {
            throw new ErroresServicios("no se encontro el libro que busca");
        }
    }

    public List<Libro> listaLibro() {
        List<Libro> libros = libroRepositorio.listarLibros();
        return libros;
    }

    public Libro listarLibros(String id) {
        Libro libro = libroRepositorio.buscarLibroPorId(id);
        return libro;
    }

    private void validacion(Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados) throws ErroresServicios {

        if (isbn == null) {
            throw new ErroresServicios("El ISBN no puede ser nulo.");
        }
        if (titulo == null || titulo.isEmpty()) {
            throw new ErroresServicios("El Titulo no puede estar vacio.");
        }
        if (anio == null) {
            throw new ErroresServicios("El año no puede estar vacio.");
        }
        if (ejemplares == null) {
            throw new ErroresServicios("El n° de ejemplares no puede ser nulo.");
        }
        if (ejemplaresPrestados == null) {
            throw new ErroresServicios("El n° de ejemplares prestados no puede ser nulo.");
        }

    }
}
