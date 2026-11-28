/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package creacionPedido;

import dtos.CotizacionDTO;
import dtos.DetallePedidoDTO;
import dtos.DetallePropuestaDTO;
import dtos.DireccionDTO;
import dtos.PedidoDTO;
import dtos.PropuestaMaterialesDTO;
import excepciones.CreacionPedidoException;
import interfaces.IDireccionBO;
import interfaces.IPedidoBO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import objetosNegocio.DireccionBO;
import objetosNegocio.PedidoBO;

/**
 *
 * @author cinca
 */
public class GestorPedido {
    
    private IPedidoBO pedidoBO;
    private IDireccionBO direccionBO;
    private DireccionDTO direccionActual;
    
    public GestorPedido() {
        this.pedidoBO = PedidoBO.getInstance();
        this.direccionBO = DireccionBO.getInstance();
    }
    
    public PedidoDTO confirmarPedido(CotizacionDTO cotizacion) throws CreacionPedidoException {
        if(cotizacion == null || cotizacion.getPropuesta() == null) {
            throw new CreacionPedidoException("No hay una cotizacion disponible.");
        }
        
        PropuestaMaterialesDTO propuesta = cotizacion.getPropuesta();
        
        List<DetallePedidoDTO> detalles = new ArrayList<>();
        
        // creacion del pedido
        for (int i = 0; i < propuesta.getDetalles().size(); i++) {
            DetallePropuestaDTO detalle = propuesta.getDetalles().get(i);
           
            detalles.add(new DetallePedidoDTO(detalle.getMaterial(), detalle.getCantidad(), 
                detalle.getPrecioUnitario(), detalle.getCantidad() * detalle.getPrecioUnitario()));
        }
        
        PedidoDTO pedido = new PedidoDTO(0, cotizacion.getTipoEntrega(), new Date(), "CONFIRMADO",
                           cotizacion.getTotal(), propuesta.getCliente(), detalles);
        
        PedidoDTO pedidoAlmacenado = pedidoBO.guardarPedido(pedido);
        
        if (pedidoAlmacenado == null) {
           throw new CreacionPedidoException("Error al guardar el pedido.");
        }
        
        return pedidoAlmacenado;

    }
    
    private void validarDatosConfirmacionPedido(PropuestaMaterialesDTO propuesta, String tipoEntrega, DireccionDTO direccion, CotizacionDTO cotizacion) throws CreacionPedidoException {
        if(propuesta == null || !"PENDIENTE".equals(propuesta.getEstado())) {
            throw new CreacionPedidoException("No hay una propuesta pendiente para confirmar");
        }   
        
        if(propuesta.getDetalles() == null || propuesta.getDetalles().isEmpty()) {
           throw new CreacionPedidoException("Agrega minimo un material para confirmar el pedido."); 
        }
        
        if(tipoEntrega == null || cotizacion== null) {
            throw new CreacionPedidoException("Se necesitan datos para confirmar el pedido.");
        }
        
        if("DOMICILIO".equals(tipoEntrega) && direccion == null) {          
            throw new CreacionPedidoException("No hay una propuesta pendiente para confirmar");
        }   
        
    }
    
    public void seleccionarDireccion(DireccionDTO direccion) {
        direccionActual = direccion;
    }
    
    public DireccionDTO obtenerDireccionSeleccionada() {
        return direccionActual;
    }
    
    public List<DireccionDTO> obtenerDirecciones(int idCliente) {
        return direccionBO.consultarDirecciones(idCliente);
    }
    
    public boolean guardarDireccion(int idCliente, DireccionDTO direccion) {
        return direccionBO.guardarDireccion(idCliente, direccion);
    }
}
