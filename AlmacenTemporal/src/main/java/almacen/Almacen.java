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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author cinca
 */
public class Almacen {
    
    private static List<ClienteDTO> clientes = new ArrayList<>();
    private static List<PropuestaMaterialesDTO> propuestas = new ArrayList<>();
    private static List<CotizacionDTO> cotizaciones = new ArrayList<>();
    private static List<PedidoDTO> pedidos = new ArrayList<>();
    private static List<OrdenEntregaDTO> ordenesEntrega = new ArrayList<>();
    
    private static int siguienteIdPropuesta = 1;
    private static ClienteDTO clienteActual;
    private static PropuestaMaterialesDTO propuestaActual;
    private String tipoEntrega;

    static {
        clientes.add(new ClienteDTO(1, "Michelle", "Castillo", "Vizcarra", "1234567890"));
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

        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getTelefono().equals(cliente.getTelefono())) {
                return false;
            }
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
        List<MaterialDTO> materialesDto = new ArrayList<>();
        materialesDto.add(new MaterialDTO(1, "Block de construccion", "cualquiera", 12f, "pieza", 500, "Block"));
        materialesDto.add(new MaterialDTO(2, "Varilla metalica corrugada 3/8", "cualquiera", 20f, "m", 300, "Varilla"));
        materialesDto.add(new MaterialDTO(3, "Bulto de cemento 25kg", "Cemex", 50f, "bulto", 9, "Cemento"));
        materialesDto.add(new MaterialDTO(4, "Bulto de cemento 25kg", "PANAM", 48f, "bulto", 40, "Cemento"));
        return materialesDto;
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
    
    public void seleccionarCliente(ClienteDTO cliente) {
        clienteActual = cliente;
        propuestaActual = null;

        if (cliente != null) {
            propuestaActual = buscarPropuestaPendiente(cliente);
        }
    }
    
    private PropuestaMaterialesDTO buscarPropuestaPendiente(ClienteDTO cliente) {
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
    
    public PropuestaMaterialesDTO obtenerPropuesta() {
        if (clienteActual == null) {
            return null;
        }

        if (propuestaActual == null) {
            propuestaActual = buscarPropuestaPendiente(clienteActual);
        }

        if (propuestaActual == null) {
            propuestaActual = crearPropuesta(clienteActual);
        }

        return propuestaActual;
    }
    
    public void cancelarPropuestaActual() {
        if (propuestaActual == null) {
            return;
        }

        propuestaActual.setEstado("CANCELADA");
        propuestaActual = null;
    }
    
    public static List<DireccionDTO> obtenerDirecciones() {
        List<DireccionDTO> direcciones = new ArrayList<>();
        direcciones.add(new DireccionDTO("Mar amarillo", "Prados del Tepeyac", 123, 54678));
        return direcciones;
    }
    
    public PropuestaMaterialesDTO crearPropuesta(ClienteDTO cliente) {
        if (cliente == null) {
            return null;
        }

        PropuestaMaterialesDTO nuevaPropuesta = new PropuestaMaterialesDTO(siguienteIdPropuesta, cliente, "PENDIENTE", 0f, 0, new ArrayList<>());

        siguienteIdPropuesta++;
        propuestas.add(nuevaPropuesta);

        return nuevaPropuesta;
    }
    
    public void crearDetallePropuesta(MaterialDTO material, int cantidad) {
        if (material == null || cantidad <= 0) {
            return;
        }

        PropuestaMaterialesDTO propuesta = obtenerPropuesta();

        if (propuesta == null) {
            return;
        }

        List<DetallePropuestaDTO> detalles = propuesta.getDetalles();

        for (int i = 0; i < detalles.size(); i++) {
            DetallePropuestaDTO detalle = detalles.get(i);

            if (detalle.getMaterial().getIdMaterial() == material.getIdMaterial()) {
                detalle.setCantidad(detalle.getCantidad() + cantidad);

                propuesta.setSubtotal(propuesta.getSubtotal() + cantidad * detalle.getPrecioUnitario());

                propuesta.setCantidadTotalProductos(propuesta.getCantidadTotalProductos() + cantidad);
                return;
            }
        }

        detalles.add(new DetallePropuestaDTO(material, cantidad, material.getPrecioUnitario()));

        propuesta.setSubtotal(propuesta.getSubtotal() + cantidad * material.getPrecioUnitario());

        propuesta.setCantidadTotalProductos(propuesta.getCantidadTotalProductos() + cantidad);
    }
    
    public boolean eliminarDetallePropuesta(DetallePropuestaDTO detalle) {
        PropuestaMaterialesDTO propuesta = obtenerPropuesta();

        if (propuesta == null || detalle == null) {
            return false;
        }

        return propuesta.getDetalles().remove(detalle);
           
    }
    
    public ClienteDTO obtenerClienteActual() {
        return clienteActual;
    }
    
    public DireccionDTO obtenerDireccionActual() {
        List<DireccionDTO> direcciones = obtenerDirecciones();

        if (direcciones.isEmpty()) {
            return null;
        }

        return direcciones.get(0);
    }
    
    public void establecerTipoEntrega(String tipo) {
        this.tipoEntrega = tipo;
    } 

    public String obtenerTipoEntrega() {
        return tipoEntrega;
    }
    
    public CotizacionDTO obtenerCotizacion() {
        PropuestaMaterialesDTO propuesta = obtenerPropuesta();

        if (propuesta == null) {
            return null;
        }

        float subtotal = propuesta.getSubtotal();
        float impuesto = subtotal * 0.16f;
        float costoEnvio = 0f;
        
        if("DOMICILIO".equals(obtenerTipoEntrega())) {
           costoEnvio = 100f;
        }
        
        float total = subtotal + impuesto + costoEnvio;

        return new CotizacionDTO(subtotal, total, new Date(), impuesto, costoEnvio, propuesta);
    }
    
    public OrdenEntregaDTO crearOrdenEntrega(String tipoEntrega, Date fecha, DireccionDTO direccion, PedidoDTO pedido) {
        OrdenEntregaDTO orden = new OrdenEntregaDTO(tipoEntrega, fecha, direccion, pedido);
        ordenesEntrega.add(orden);
        return orden;
    }
    
    public PedidoDTO guardarPedido(PedidoDTO pedido) {
        if(pedido == null) {           
           return null;
        }
        
        int id = 1;
        for(int i = 0; i<pedidos.size(); i++) {
            PedidoDTO pedidoExistente = pedidos.get(i);
            
            if(pedidoExistente.getIdPedido() >= id) {
                id= pedidoExistente.getIdPedido();
            }
        }
        pedido.setIdPedido(id);
        pedidos.add(pedido);
        return pedido;
    }

}
