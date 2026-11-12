/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package creacionPedido;

import dtos.ClienteDTO;
import dtos.CotizacionDTO;
import dtos.DetallePropuestaDTO;
import dtos.DireccionDTO;
import dtos.MaterialDTO;
import dtos.OrdenEntregaDTO;
import dtos.PedidoDTO;
import dtos.PaqueteDTO;
import dtos.PropuestaMaterialesDTO;
import excepciones.CreacionPedidoException;
import java.util.Date;
import java.util.List;
import objetosNegocio.PaqueteBO;

import java.util.List;

/**
 * Fachada del subsistema que se encarga de comunicarse con el controlador.
 * 
 * @author cinca
 */
public class CrearPedidoFachada implements ISubsistemaCrearPedido {
    private ControlCarrito controlCarrito;
    private GestorOrdenEntrega gestorOrdenEntrega;
    private GestorCotizacion gestorCotizacion;
    private GestorPedido gestorPedido;

    /**
     * Crea la fachada del subsistema de creacion de pedidos e inicializa el
     * control del las clases de control.
     */
    public CrearPedidoFachada() {
        controlCarrito = new ControlCarrito();
        gestorOrdenEntrega = new GestorOrdenEntrega();
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

        PaqueteBO paqueteBO = new PaqueteBO();
        List<PaqueteDTO> paquetes = paqueteBO.obtenerPaquetes();

        for(int i = 0; i < paquetes.size(); i++){
            if (paquetes.get(i).getIdPaquete() == paqueteInstancia.getIdPaquete()){
                agregarAlCarrito(paquetes.get(i).getDetalles().get(i).getMaterial(), paquetes.get(i).getDetalles().get(i).getCantidadMaterial());
            }
        }
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
        gestorOrdenEntrega.limpiarDatos();
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
        gestorOrdenEntrega.establecerTipoEntrega(tipo);
    }

    @Override
    public String obtenerTipoEntrega() {
        return gestorOrdenEntrega.obtenerTipoEntrega();
    }

    @Override
    public void seleccionarDireccion(DireccionDTO direccion) {
        gestorOrdenEntrega.seleccionarDireccion(direccion);
    }

    @Override
    public DireccionDTO obtenerDireccionSeleccionada() {
        return gestorOrdenEntrega.obtenerDireccionSeleccionada();
    }

    @Override
    public List<DireccionDTO> obtenerDirecciones(int idCliente) {
        return gestorOrdenEntrega.obtenerDirecciones(idCliente);
    }

    @Override
    public boolean guardarDireccion(int idCliente, DireccionDTO direccion) {
        return gestorOrdenEntrega.guardarDireccion(idCliente, direccion);
    }

    @Override
    public Date programarFechaEstimadaEntrega() {
        return gestorOrdenEntrega.programarFechaEstimadaEntrega();
    }

    @Override
    public Date programarFechaEstimadaLimiteRecoger() {
        return programarFechaEstimadaEntrega();
    }

    @Override
    public OrdenEntregaDTO confirmarOrdenEntrega(PedidoDTO pedido) {
        return gestorOrdenEntrega.confirmarOrdenEntrega(pedido);
    }

    @Override
    public PedidoDTO confirmarPedido() throws CreacionPedidoException {
        PedidoDTO pedido = gestorPedido.confirmarPedido(controlCarrito.obtenerPropuestaActual(), gestorOrdenEntrega.obtenerTipoEntrega(),
                           gestorOrdenEntrega.obtenerDireccionSeleccionada(),
                           gestorCotizacion.generarCotizacion(controlCarrito.obtenerPropuestaActual(), gestorOrdenEntrega.obtenerTipoEntrega()));

        gestorOrdenEntrega.confirmarOrdenEntrega(pedido);
        controlCarrito.finalizarPropuestaConfirmada();
        return pedido;

    }

    @Override
    public CotizacionDTO generarCotizacion() {
        return gestorCotizacion.generarCotizacion(controlCarrito.obtenerPropuestaActual(), gestorOrdenEntrega.obtenerTipoEntrega());
    }



}
