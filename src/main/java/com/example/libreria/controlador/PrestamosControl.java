package com.example.libreria.controlador;

import com.example.libreria.entidades.Prestamos;
import com.example.libreria.errores.ErroresServicios;
import com.example.libreria.servicios.PrestamosServicios;
import java.util.Date;
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
@RequestMapping("/prestamos")
public class PrestamosControl {
    @Autowired
    private PrestamosServicios prestamosServicio;

    @GetMapping("/registro")
    public String formulario() {
        return "formularioPrestamo";
    }
    
    @GetMapping("/lista")
    public String lista(ModelMap modelo) {
        List<Prestamos> prestamos = prestamosServicio.listaPrestamos();
        modelo.addAttribute("prestamos", prestamos);
        return "listaPrestamos.html";
    }
    
    @PostMapping("/registro")
    public String guardar(ModelMap modelo, @RequestParam Date fechaPrestamo,@RequestParam Long documento,
            @RequestParam String nombre, @RequestParam String apellido, @RequestParam String telefono) throws ErroresServicios {
        try {
            prestamosServicio.cargarPrestamo(fechaPrestamo, documento, nombre, apellido, telefono);
            modelo.put("exito", "registro exitoso");
            return "formularioPrestamo";
        } catch (ErroresServicios e) {
            modelo.put("error", "Falto algun dato");
            return "formularioPrestamo";
        }
    }
    
    @GetMapping("/modificar/{id}")
    public String modificar(ModelMap modelo, @PathVariable String id) {
        modelo.addAttribute("prestamo", prestamosServicio.listarPrestamos(id));
        return "modificarPrestamo";
    }
    
    @PostMapping("/modificar/{id}")
    public String confirmarModificacion(RedirectAttributes attribute, @RequestParam String id,
            @RequestParam Date fechaPrestamo,@RequestParam Date fechaDevolucion)
            throws ErroresServicios {
        try {
            prestamosServicio.modificarPrestamo(fechaPrestamo, fechaDevolucion, id);
            attribute.addFlashAttribute("exito", "Registro exitoso");
            return "redirect:/prestamos/lista";
        } catch (ErroresServicios e) {
            attribute.addFlashAttribute("error", "Falto algun dato");
            return "redirect:/prestamos/modificar/{id}";
        }
    }
    
    @GetMapping("/baja/{id}")
    public String baja(@PathVariable String id) {
        prestamosServicio.baja(id);
        return "redirect:/prestamos/lista";
    }
    
    @GetMapping("/alta/{id}")
    public String alta(@PathVariable String id) throws ErroresServicios {
        prestamosServicio.alta(id);
        return "redirect:/libro/lista";
    }
}
