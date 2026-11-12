/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetosNegocio;

import almacen.Almacen;
import dtos.PedidoDTO;
import interfaces.IPedidoBO;

/**
 * Objeto negocio que usa AlmacenTemporal temporalmente.
 * @author cinca
 */
public class PedidoBO implements IPedidoBO {
    
    @Override
    public PedidoDTO guardarPedido(PedidoDTO pedido) {
        return Almacen.guardarPedido(pedido);
    }
    
}
