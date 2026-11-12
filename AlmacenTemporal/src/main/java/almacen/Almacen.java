/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package almacen;

import dtos.ClienteDTO;
import dtos.CotizacionDTO;
import dtos.DetallePaqueteDTO;
import dtos.DetallePropuestaDTO;
import dtos.DireccionDTO;
import dtos.MaterialDTO;
import dtos.OrdenEntregaDTO;
import dtos.PaqueteDTO;
import dtos.PedidoDTO;
import dtos.PropuestaMaterialesDTO;
import dtos.SolicitudReposicionDTO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author cinca
 */
public class Almacen {
    
    private static List<ClienteDTO> clientes = new ArrayList<>();
    private static List<DireccionDTO> direcciones = new ArrayList<>();
    private static List<PropuestaMaterialesDTO> propuestas = new ArrayList<>();
    private static List<CotizacionDTO> cotizaciones = new ArrayList<>();
    private static List<PedidoDTO> pedidos = new ArrayList<>();
    private static List<OrdenEntregaDTO> ordenesEntrega = new ArrayList<>();
    private static List<MaterialDTO> materiales = new ArrayList<>();
    private static List<SolicitudReposicionDTO> solicitudesReposicion = new ArrayList<>();
    private static int siguienteIdSolicitudReposicion = 1;
    
    private static int siguienteIdPropuesta = 1;

    static {
        clientes.add(new ClienteDTO(1, "Michelle", "Castillo", "Vizcarra", "1234567890"));
        direcciones.add(new DireccionDTO(1, "Mar amarillo", "Prados del Tepeyac", 123, 54678));
        materiales.add(new MaterialDTO(1, "Block de construccion", "cualquiera", 12f, "pieza", 500, "Block"));
        materiales.add(new MaterialDTO(2, "Varilla metalica corrugada 3/8", "cualquiera", 20f, "m", 300, "Varilla"));
        materiales.add(new MaterialDTO(3, "Bulto de cemento 25kg", "Cemex", 50f, "bulto", 9, "Cemento"));
        materiales.add(new MaterialDTO(4, "Bulto de cemento 25kg", "PANAM", 48f, "bulto", 40, "Cemento"));
        
    }
    
    public static ClienteDTO obtenerCliente() {
        if (clientes.isEmpty()) {
            return null;
        }

        return clientes.get(0);
    }

    public static boolean GuardarCliente(ClienteDTO cliente) {
        if (cliente == null) {
            return false;
        }

        if (cliente.getId() <= 0) {
            int siguienteId = 1;

            for (int i = 0; i < clientes.size(); i++) {
                if (clientes.get(i).getId() >= siguienteId) {
                    siguienteId = clientes.get(i).getId() + 1;
                }
            }

            cliente.setId(siguienteId);
        }
        
        return clientes.add(cliente);
    }

    public static List<ClienteDTO> obtenerClientes() {
        return clientes;
    }

    public static ClienteDTO buscarCliente(String telefono) {
        if (telefono == null) {
            return null;
        }

        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getTelefono().equals(telefono.trim())) {
                return clientes.get(i);
            }
        }

        return null;
    }
    
    
    public static List<MaterialDTO> obtenerMateriales() {
        return new ArrayList<>(materiales);
    }

    public static MaterialDTO buscarMaterial(String nombre){
        if(nombre == null) {
           return null;
        }
        for(MaterialDTO material: obtenerMateriales()){
            if(material.getNombre() != null && material.getNombre().toLowerCase().contains(nombre.toLowerCase())){
                return material;
            }
        }
        return null;
    }

    public static List<PaqueteDTO> obtenerPaquetes() {
        List<DetallePaqueteDTO> detallespaqueteDto = new ArrayList<>();
        // o sea que la barda usa 50 blocks de construccion y 10 bultos de cemento
        detallespaqueteDto.add(new DetallePaqueteDTO(obtenerMateriales().get(0), 50));
        detallespaqueteDto.add(new DetallePaqueteDTO(obtenerMateriales().get(2), 10));
        
        List<PaqueteDTO> paquetesDto = new ArrayList<>();
        paquetesDto.add(new PaqueteDTO(1, "Barda de block 10m", 10, "m", "Barda", detallespaqueteDto));
        return paquetesDto;
    }
    
    public PropuestaMaterialesDTO buscarPropuestaPendiente(ClienteDTO cliente) {
        if (cliente == null) {
            return null;
        }

        for (int i = 0; i < propuestas.size(); i++) {
            PropuestaMaterialesDTO propuesta = propuestas.get(i);

            if (propuesta.getCliente() != null && propuesta.getCliente().getId() == cliente.getId() && "PENDIENTE".equals(propuesta.getEstado())) {
                return propuesta;
            }
        }

        return null;
    }
    
    public static PropuestaMaterialesDTO obtenerPropuestaMock() {
        
        List<DetallePropuestaDTO> detallesPropuestaDto = new ArrayList<>();
        
        detallesPropuestaDto.add(new DetallePropuestaDTO(obtenerMateriales().get(0), 50, 12f));
        detallesPropuestaDto.add(new DetallePropuestaDTO(obtenerMateriales().get(2), 10, 50f));
        return new PropuestaMaterialesDTO(1100f, 60, detallesPropuestaDto);
    }
    
    public static List<DireccionDTO> obtenerDirecciones(int idCliente) {
        List<DireccionDTO> direccionesCliente = new ArrayList<>();
        
        for(int i = 0; i< direcciones.size(); i++) {
            DireccionDTO direccion = direcciones.get(i);           
            if(direccion.getIdCliente() == idCliente) {
                direccionesCliente.add(direccion);
            }
        }

        return direccionesCliente;
     }
    
    
    public static boolean guardarDireccion(int idCliente, DireccionDTO direccion) {
        if (idCliente<= 0 || direccion == null) {
            return false;
        }
        direccion.setIdCliente(idCliente);
        return direcciones.add(direccion);
    }
    
    public PropuestaMaterialesDTO guardarPropuesta(ClienteDTO cliente) {
        if (cliente == null) {
            return null;
        }

        PropuestaMaterialesDTO nuevaPropuesta = new PropuestaMaterialesDTO(siguienteIdPropuesta, cliente, "PENDIENTE", 0f, 0, new ArrayList<>());

        siguienteIdPropuesta++;
        propuestas.add(nuevaPropuesta);

        return nuevaPropuesta;
    }
    
    public static boolean actualizarPropuesta(PropuestaMaterialesDTO propuestaActualizada) {
        if(propuestaActualizada == null) {
            return false;
        }
        
        for(int i = 0; i < propuestas.size(); i++) {
            if(propuestas.get(i).getIdPropuesta() == propuestaActualizada.getIdPropuesta()) {
                propuestas.set(i, propuestaActualizada);
                return true;
            }
        }
        
        return false;
    }
    
    public static OrdenEntregaDTO crearOrdenEntrega(String tipoEntrega, Date fecha, DireccionDTO direccion, PedidoDTO pedido) {
        OrdenEntregaDTO orden = new OrdenEntregaDTO(tipoEntrega, fecha, direccion, pedido);
        ordenesEntrega.add(orden);
        return orden;
    }
    
    public static PedidoDTO guardarPedido(PedidoDTO pedido) {
        if(pedido == null) {           
           return null;
        }
        
        int id = 1;
        for(int i = 0; i<pedidos.size(); i++) {
            if(pedidos.get(i).getIdPedido() >= id) {
                id = pedidos.get(i).getIdPedido() + 1;
            }
        }
        pedido.setIdPedido(id);
        pedidos.add(pedido);
        return pedido;
    }
    
    public static boolean guardarSolicitudReposicion(SolicitudReposicionDTO solicitud) {
        if (solicitud == null) {
            return false;
        }

        solicitud.setIdSolicitud(siguienteIdSolicitudReposicion++);
        solicitud.setFechaSolicitud(new Date());
        solicitud.setEstado("PENDIENTE");
        return solicitudesReposicion.add(solicitud);
    }

    public static List<SolicitudReposicionDTO> obtenerSolicitudesReposicion() {
        return new ArrayList<>(solicitudesReposicion);
    }
}
