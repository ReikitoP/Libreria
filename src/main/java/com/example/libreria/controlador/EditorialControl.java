package com.example.libreria.controlador;

import com.example.libreria.entidades.Editorial;
import com.example.libreria.errores.ErroresServicios;
import com.example.libreria.servicios.EditorialServicios;
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
@RequestMapping("/editorial")
public class EditorialControl {

    @Autowired
    private EditorialServicios editorialServicio;

    @GetMapping("/registro")
    public String formulario() {
        return "formularioEditorial";
    }

    @PostMapping("/registro")
    public String guardar(ModelMap modelo, @RequestParam String nombre) throws ErroresServicios {
        try {
            editorialServicio.cargarEditorial(nombre);
            modelo.put("exito", "registro exitoso");
            return "formularioEditorial";
        } catch (ErroresServicios e) {
            modelo.put("error", "Falto algun dato");
            return "formularioEditorial";
        }
    }

    @GetMapping("/modificar/{id}")
    public String modificar(ModelMap modelo, @PathVariable String id) {

        modelo.addAttribute("editorial", editorialServicio.listarEditorial(id));
        return "modificarEditorial";

    }

    @PostMapping("/modificar/{id}")
    public String confirmarModificacion(RedirectAttributes attribute, @PathVariable String id, @RequestParam String nombre) {
        try {
            editorialServicio.modificarEditorial(nombre, id);
            attribute.addFlashAttribute("exito", "Registro exitoso");
            return "redirect:/editorial/lista";
        } catch (ErroresServicios e) {
            attribute.addFlashAttribute("error", "Falto algun dato");
            return "redirect:/editorial/modificar/{id}";
        }
    }

    @GetMapping("/lista")
    public String lista(ModelMap modelo) {
        List<Editorial> editoriales = editorialServicio.listarEditoriales();
        modelo.addAttribute("editoriales", editoriales);
        return "listaEditorial";
    }

    @GetMapping("/baja/{id}")
    public String deshabilitarEditorial(@PathVariable String id) {
        editorialServicio.baja(id);
        return "redirect:/editorial/lista";
    }

    @GetMapping("/alta/{id}")
    public String alta(@PathVariable String id) throws ErroresServicios {

        editorialServicio.alta(id);
        return "redirect:/editorial/lista";

    }
}
