package com.example.libreria.servicios;

import com.example.libreria.entidades.Cliente;
import com.example.libreria.errores.ErroresServicios;
import com.example.libreria.repositorio.ClienteRepositorio;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

public class ClienteServicios {
    @Autowired
    private ClienteRepositorio clienteRepositorio;
    
    @Transactional
    public void cargarCliente(Long documento, String nombre, String apellido, String telefono) throws ErroresServicios {
        validar(documento,nombre, apellido, telefono);
        Cliente cliente = new Cliente();
        cliente.setDocumento(documento);
        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setTelefono(telefono);
        clienteRepositorio.save(cliente);
    }
    
    private void validar(Long documento, String nombre, String apellido, String telefono) throws ErroresServicios {
        if (documento == null || documento < 0) {
            throw new ErroresServicios("El documento debe tener un número por arriba de 0");
        }
        if (nombre == null || nombre.isEmpty()) {
            throw new ErroresServicios("El cliente tiene que tener un nombre");
        }
        if (apellido == null || apellido.isEmpty()) {
            throw new ErroresServicios("El cliente tiene que tener un apellido");
        }
        if (telefono == null || telefono.isEmpty() || telefono.length() < 10) {
            throw new ErroresServicios("El telefono del cliente tiene que tener al menos 10 digitos");
        }
    }
    
    @Transactional
    public void modificarCliente(Long documento, String nombre, String apellido, String telefono, String id) throws ErroresServicios {
        Cliente cliente = clienteRepositorio.buscarClientePorId(id);
        validar(documento, nombre, apellido, telefono);
        cliente.setDocumento(documento);
        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setTelefono(telefono);
        clienteRepositorio.save(cliente);
    }
    
    public List<Cliente> listarClientes() {
        List<Cliente> clientes = clienteRepositorio.listarClientes();
        return clientes;
    }
    
    public Cliente listarCliente(String id) {
        Cliente cliente = clienteRepositorio.buscarClientePorId(id);
        return cliente;
    }
    
    @Transactional
    public void baja(String id){
        Optional<Cliente> respuesta = clienteRepositorio.findById(id);
        if(respuesta.isPresent()){
        Cliente cliente =respuesta.get();
        cliente.setAlta(false);
        
        clienteRepositorio.save(cliente);
        }
    }
    
    @Transactional
    public void alta( String id) throws ErroresServicios {
        Optional<Cliente> resp = clienteRepositorio.findById(id);
        if(resp.isPresent()){
        Cliente cliente =resp.get();
        cliente.setAlta(true);
        
        clienteRepositorio.save(cliente);
        }else{
            throw new ErroresServicios("no se encontro el autor que busca");
        }
    }
}
