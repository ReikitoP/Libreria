package com.example.libreria.servicios;

import com.example.libreria.entidades.Cliente;
import com.example.libreria.entidades.Prestamos;
import com.example.libreria.errores.ErroresServicios;
import com.example.libreria.repositorio.PrestamosRepositorio;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrestamosServicios {
    @Autowired
    private PrestamosRepositorio prestamosRepositorio;
    
    
    @Transactional
    public void cargarPrestamo(Date fecha, Long documento, String nombre, String apellido, String telefono) throws ErroresServicios {
        validar(fecha);
        Prestamos prestamos = new Prestamos();
        prestamos.setFechaPrestamo(fecha);
        Cliente cliente = new Cliente();
        cliente.setDocumento(documento);
        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setTelefono(telefono);
        prestamos.setCliente(cliente);
        prestamosRepositorio.save(prestamos);
        System.out.println(prestamos);
    }
    
    private void validar(Date fechaPrestamo) throws ErroresServicios {
        if (fechaPrestamo == null) {
            throw new ErroresServicios("La fecha de prestamo no puede estar vacia");
        }
    }
    
    @Transactional
    public void modificarPrestamo(Date fechaPrestamo, Date fechaDevolucion, String id) throws ErroresServicios {
        Prestamos prestamo = prestamosRepositorio.buscarPrestamosPorId(id);
        validar(fechaPrestamo);
        prestamo.setFechaPrestamo(fechaPrestamo);
        prestamo.setFechaDevolucion(fechaDevolucion);
        prestamosRepositorio.save(prestamo);
    }
    
    @Transactional
    public void baja(String id) {
        Optional<Prestamos> respuesta = prestamosRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Prestamos prestamo = respuesta.get();
            prestamo.setAlta(false);

            prestamosRepositorio.save(prestamo);
        }
    }
    
    @Transactional
    public void alta(String id) {
        Optional<Prestamos> respuesta = prestamosRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Prestamos prestamo = respuesta.get();
            prestamo.setAlta(true);

            prestamosRepositorio.save(prestamo);
        }
    }
    
    public List<Prestamos> listaPrestamos() {
        List<Prestamos> prestamos = prestamosRepositorio.listarPrestamos();
        return prestamos;
    }
    
    public Prestamos listarPrestamos(String id) {
        Prestamos prestamos = prestamosRepositorio.buscarPrestamosPorId(id);
        return prestamos;
    }
}
