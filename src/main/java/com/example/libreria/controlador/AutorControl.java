package com.example.libreria.controlador;

import com.example.libreria.entidades.Autor;
import com.example.libreria.errores.ErroresServicios;
import com.example.libreria.servicios.AutorServicios;
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
@RequestMapping("/autor")
public class AutorControl {

    @Autowired
    private AutorServicios autorServicio;

    @GetMapping("/registro")
    public String formulario() {
        return "formularioAutor";
    }

    @PostMapping("/registro")
    public String guardar(ModelMap modelo, @RequestParam String nombre) throws ErroresServicios {
        try {
            autorServicio.cargarAutor(nombre);
            modelo.put("exito", "registro exitoso");
            return "formularioAutor";
        } catch (ErroresServicios e) {
            modelo.put("error", "Falto algun dato");
            return "formularioAutor";
        }
    }

    @GetMapping("/modificar/{id}")
    public String modificar(ModelMap modelo, @PathVariable String id) {

        modelo.addAttribute("autor", autorServicio.listarAutor(id));
        return "modificarAutor";

    }

    @PostMapping("/modificar/{id}")
    public String confirmarModificacion(RedirectAttributes attribute, @PathVariable String id, @RequestParam String nombre) {
        try {
            autorServicio.modificarAutor(nombre, id);
            attribute.addFlashAttribute("exito", "Registro exitoso");
            return "redirect:/autor/lista";
        } catch (ErroresServicios e) {
            attribute.addFlashAttribute("error", "Falto algun dato");
            return "redirect:/autor/modificar/{id}";
        }
    }

    @GetMapping("/lista")
    public String lista(ModelMap modelo) {
        List<Autor> autores = autorServicio.listarAutores();
        modelo.addAttribute("autores", autores);
        return "listaAutor.html";
    }

    @GetMapping("/baja/{id}")
    public String deshabilitarAutor(@PathVariable String id) {

        autorServicio.baja(id);
        return "redirect:/autor/lista";
    }

    @GetMapping("/alta/{id}")
    public String alta(@PathVariable String id) throws ErroresServicios {

        autorServicio.alta(id);
        return "redirect:/autor/lista";
    }
}
