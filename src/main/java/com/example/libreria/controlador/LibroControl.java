package com.example.libreria.controlador;

import com.example.libreria.entidades.Autor;
import com.example.libreria.entidades.Editorial;
import com.example.libreria.entidades.Libro;
import com.example.libreria.errores.ErroresServicios;
import com.example.libreria.servicios.AutorServicios;
import com.example.libreria.servicios.EditorialServicios;
import com.example.libreria.servicios.LibroServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/libro")
public class LibroControl {

    @Autowired
    private LibroServicio libroServicio;
    @Autowired
    private AutorServicios autorServicio;

    @Autowired
    private EditorialServicios editorialServicio;

    @GetMapping("/registro")
    public String formulario(ModelMap modelo) {
        List<Autor> autores = autorServicio.listarAutores();
        modelo.put("autores", autores);
        List<Editorial> editoriales = editorialServicio.listarEditoriales();
        modelo.put("editoriales", editoriales);
        return "formularioLibro";
    }

    @PostMapping("/registro")
    public String guardar(ModelMap modelo, @RequestParam Long isbn,
            @RequestParam String titulo, @RequestParam Integer anio,
            @RequestParam Integer ejemplares, @RequestParam Integer ejemplaresPrestados,
            @RequestParam Autor nombreAutor, @RequestParam Editorial nombreEditorial) throws ErroresServicios {
        try {
            libroServicio.crearLibro(isbn, titulo, anio, ejemplares, ejemplaresPrestados,
                    nombreAutor, nombreEditorial);
            modelo.put("exito", "registro exitoso");
            return "formularioLibro";
        } catch (ErroresServicios e) {
            modelo.put("error", "Falto algun dato");
            return "formularioLibro";
        }

    }

    @GetMapping("/modificar/{id}")
    public String modificar(ModelMap modelo, @PathVariable String id) {
        modelo.addAttribute("libro", libroServicio.listarLibros(id));
        return "modificarLibro";

    }

    @PostMapping("/modificar/{id}")
    public String confirmarModificacion(RedirectAttributes attribute, @RequestParam String id,
            @RequestParam Long isbn, @RequestParam String titulo, @RequestParam Integer anio,
            @RequestParam Integer ejemplares,
            @RequestParam Integer ejemplaresPrestados)
            //            @RequestParam Integer ejemplaresRestantes)
            throws ErroresServicios {
        try {
            libroServicio.modificarLibro(id, isbn, titulo, anio, ejemplares, ejemplaresPrestados);
            attribute.addFlashAttribute("exito", "Registro exitoso");
            return "redirect:/libro/lista";
        } catch (ErroresServicios e) {
            attribute.addFlashAttribute("error", "Falto algun dato");
            return "redirect:/libro/modificar/{id}";
        }
    }

    @GetMapping("/lista")
    public String lista(ModelMap modelo) {
        List<Libro> listaLibros = libroServicio.listaLibro();
        modelo.addAttribute("libros", listaLibros);
        return "listaLibros.html";
    }

    @GetMapping("/baja/{id}")
    public String baja(@PathVariable String id) {

        libroServicio.baja(id);
        return "redirect:/libro/lista";

    }

    @GetMapping("/alta/{id}")
    public String alta(@PathVariable String id) throws ErroresServicios {

        libroServicio.alta(id);
        return "redirect:/libro/lista";

    }
}
