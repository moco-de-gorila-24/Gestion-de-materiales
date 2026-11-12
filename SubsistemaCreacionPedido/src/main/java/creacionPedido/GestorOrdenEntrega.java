/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package creacionPedido;

import dtos.DireccionDTO;
import dtos.OrdenEntregaDTO;
import dtos.PedidoDTO;
import excepciones.CreacionPedidoException;
import interfaces.IOrdenEntregaBO;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import objetosNegocio.OrdenEntregaBO;

/**
 *
 * @author cinca
 */
public class GestorOrdenEntrega {
    
    private Date fechaEntrega;
    private Date fechaLimite;
    private String tipoEntrega;
    private DireccionDTO direccionActual;
    private IOrdenEntregaBO ordenEntregaBO;
    
    public GestorOrdenEntrega() {
        ordenEntregaBO = new OrdenEntregaBO();
    }

    public void limpiarDatos() {
        tipoEntrega = null;
        fechaEntrega = null;
        fechaLimite = null;
        direccionActual = null;
    }
    
    public void establecerTipoEntrega(String tipo) {
        tipoEntrega = tipo;
    }
    
    public String obtenerTipoEntrega() {
        return tipoEntrega;
    }
    
    public void seleccionarDireccion(DireccionDTO direccion) {
        direccionActual = direccion;
    }
    
    public DireccionDTO obtenerDireccionSeleccionada() {
        return direccionActual;
    }
    
    public List<DireccionDTO> obtenerDirecciones(int idCliente) {
        return ordenEntregaBO.consultarDirecciones(idCliente);
    }
    
    public boolean guardarDireccion(int idCliente, DireccionDTO direccion) {
        return ordenEntregaBO.guardarDireccion(idCliente, direccion);
    }
    
    public Date programarFechaEstimadaEntrega() {
        if(fechaEntrega == null) {
            GregorianCalendar fecha = new GregorianCalendar();
            fecha.add(GregorianCalendar.DAY_OF_MONTH, 5);
            fechaEntrega = fecha.getTime();
        }
        return fechaEntrega;
    }
    
    public Date programarFechaEstimadaLimiteRecoger() {
        if(fechaLimite == null) {
            GregorianCalendar fecha = new GregorianCalendar();
            fecha.add(GregorianCalendar.DAY_OF_MONTH, 10);
            fechaLimite = fecha.getTime();
        }
        return fechaLimite;
    }
    
    public OrdenEntregaDTO confirmarOrdenEntrega(PedidoDTO pedido) {
        if(pedido == null) {
            throw new CreacionPedidoException("Error, no se pudo recibir el pedido.");
        }
        if(!"DOMICILIO".equals(tipoEntrega)) {
            return null;
        }
        if(direccionActual== null) {
           throw new CreacionPedidoException("Por favor selecciona una direccion antes de confirmar el pedido.");
        }
        
        return ordenEntregaBO.guardarOrdenEntrega(tipoEntrega, programarFechaEstimadaEntrega(), direccionActual, pedido);
    }
    
}
