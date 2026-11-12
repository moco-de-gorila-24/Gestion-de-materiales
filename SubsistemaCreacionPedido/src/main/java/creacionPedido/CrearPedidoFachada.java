/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package creacionPedido;

import dtos.DetallePropuestaDTO;
import dtos.MaterialDTO;
import dtos.PropuestaMaterialesDTO;
import java.util.List;

/**
 * Fachada del subsistema que se encarga de comunicarse con el controlador.
 * 
 * @author cinca
 */
public class CrearPedidoFachada implements ISubsistemaCrearPedido {
    private ControlCarrito controlCarrito;
    private GestorOrdenEntrega gestorOrdenEntrega;

    /**
     * Crea la fachada del subsistema de creacion de pedidos e inicializa el
     * control del las clases de control.
     */
    public CrearPedidoFachada() {
        controlCarrito = new ControlCarrito();
        gestorOrdenEntrega = new GestorOrdenEntrega();
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
        controlCarrito.eliminarDelCarrito(detalle);
        return true;
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
        controlCarrito.sustituirMaterial(detalle, nuevo);
        return true;    
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
    
    
    
}
