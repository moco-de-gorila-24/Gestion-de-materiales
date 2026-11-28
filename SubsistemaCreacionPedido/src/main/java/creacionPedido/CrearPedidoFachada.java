/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package creacionPedido;

import dtos.CotizacionDTO;
import dtos.DetallePropuestaDTO;
import dtos.DireccionDTO;
import dtos.MaterialDTO;
import dtos.PaqueteDTO;
import dtos.PedidoDTO;
import dtos.PropuestaMaterialesDTO;
import excepciones.CreacionPedidoException;


import java.util.List;

/**
 * Fachada del subsistema que se encarga de comunicarse con el controlador.
 * 
 * @author cinca
 */
public class CrearPedidoFachada implements ISubsistemaCrearPedido {
    private ControlCarrito controlCarrito;
   
    private GestorCotizacion gestorCotizacion;
    private GestorPedido gestorPedido;

    /**
     * Crea la fachade creacion de pedidos e inicializa el
     * control del las clases de control.
     */
    public CrearPedidoFachada() {
        controlCarrito = new ControlCarrito();
  
        gestorCotizacion = new GestorCotizacion();
        gestorPedido = new GestorPedido();
    }
   
    /**
     * Pide al control del carrito agregar el material indicado.
     * 
     * @param material material que se agregara.
     * @param cantidad cantidad que se agregara.
     */
    @Override
    public void agregarAlCarrito(MaterialDTO material, int cantidad) {
        controlCarrito.AgregarAlCarrito(material, cantidad);
    }

    /**
     * Pide al control del carrito agregar el material/material indicado.
     *
     * @param paqueteInstancia paquete que esta en la instancia de cada panel
     */
    @Override
    public void agregarAlCarrito(PaqueteDTO paqueteInstancia) {
        controlCarrito.AgregarAlCarrito(paqueteInstancia);

    }

    /**
     * Establece la propuesta activa que usara el control del carrito.
     * 
     * @param propuesta propuesta asociada al carrito.
     */
    @Override
    public void iniciarCarrito(PropuestaMaterialesDTO propuesta) {
        controlCarrito.iniciarCarrito(propuesta);
    }

    @Override
    public boolean eliminarDelCarrito(DetallePropuestaDTO detalle) {
        return controlCarrito.eliminarDelCarrito(detalle);
    }

    @Override
    public void limpiarPropuestaActual() {
        controlCarrito.limpiarPropuestaActual();
    }

    @Override
    public void cancelarVenta() {
        controlCarrito.cancelarVenta();
    }

    @Override
    public boolean sustituirMaterial(DetallePropuestaDTO detalle, MaterialDTO nuevo) {
        return controlCarrito.sustituirMaterial(detalle, nuevo);          
    }

    @Override
    public List<DetallePropuestaDTO> getDetallesCarrito() {
        return controlCarrito.getDetallesCarrito();
    }

    @Override
    public int obtenerCantidadNecesaria(DetallePropuestaDTO detalle, MaterialDTO candidato) {
        return controlCarrito.obtenerCantidadNecesaria(detalle, candidato);
    }

    @Override
    public List<MaterialDTO> obtenerMaterialesSustitutos(DetallePropuestaDTO detalle) {
        return controlCarrito.obtenerMaterialesSustitutos(detalle);
    }

    @Override
    public float obtenerSubtotal() {
        return controlCarrito.obtenerSubtotal();
    }

    @Override
    public void cambiarCantidad(DetallePropuestaDTO detalle, int cantidadCambiar, int cantidadOriginal) {
        controlCarrito.cambiarCantidad(detalle, cantidadCambiar, cantidadOriginal);
    }

    @Override
    public void establecerTipoEntrega(String tipo) {
        gestorCotizacion.establecerTipoEntrega(tipo);
    }

    @Override
    public String obtenerTipoEntrega() {
        return gestorCotizacion.obtenerTipoEntrega();
    }

    @Override
    public void seleccionarDireccion(DireccionDTO direccion) {
        gestorPedido.seleccionarDireccion(direccion);
    }

    @Override
    public DireccionDTO obtenerDireccionSeleccionada() {
        return gestorPedido.obtenerDireccionSeleccionada();
    }

    @Override
    public List<DireccionDTO> obtenerDirecciones(int idCliente) {
        return gestorPedido.obtenerDirecciones(idCliente);
    }

    @Override
    public boolean guardarDireccion(int idCliente, DireccionDTO direccion) {
        return gestorPedido.guardarDireccion(idCliente, direccion);
    }

    /**
     * Metodo encargado de confirmar el pedido a partir de la cotizacion actual, 
     * donde se obtiene la cotizacion generada del GestorCotizacion
     * y se envia al GestorPedido para realizar el proceso de confirmacion y 
     * almacenado del pedido.
     * 
     * @return el pedido almacenado y confirmado.
     * @throws CreacionPedidoExcepption si ocurre un error durante la confirmacion del pedido.
     */
    @Override
    public PedidoDTO confirmarPedido() throws CreacionPedidoException {
        return gestorPedido.confirmarPedido(gestorCotizacion.obtenerCotizacionActual());       
    }
    
    /**
     * Metodo encargado de generar la cotizacion a partir de la propuesta actual y
     * su tipo de entrega actual, donde se obtiene la propuesta actual desde el
     * control carrito y el tipo de entrega desde el gestor de cotizacion.
     * 
     * @return la cotizacion generada con los materiales cargados desde la propuesta
     * y el tipo de entrega seleccionado.
     */
    @Override
    public CotizacionDTO generarCotizacion() {
        return gestorCotizacion.generarCotizacion(controlCarrito.obtenerPropuestaActual(), gestorCotizacion.obtenerTipoEntrega());    
    }
    /**
     * Metodo encargado de obtener la cotizacion actual desde el gestor cotizacion.
     * 
     * @return la cotizacion actual.
     */
    @Override
    public CotizacionDTO obtenerCotizacionActual() {
        return gestorCotizacion.obtenerCotizacionActual();
    }
    
}
