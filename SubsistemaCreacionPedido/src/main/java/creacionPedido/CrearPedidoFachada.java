/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package creacionPedido;

import dtos.MaterialDTO;
import dtos.PropuestaMaterialesDTO;

/**
 * Fachada del subsistema que se encarga de comunicarse con el controlador.
 * 
 * @author cinca
 */
public class CrearPedidoFachada implements ISubsistemaCrearPedido {
    private ControlCarrito controlCarrito;

    /**
     * Crea la fachada del subsistema de creacion de pedidos e inicializa el
     * control del las clases de control.
     */
    public CrearPedidoFachada() {
        controlCarrito = new ControlCarrito();
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
    
}
