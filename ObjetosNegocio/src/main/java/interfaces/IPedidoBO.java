/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import dtos.PedidoDTO;

/**
 * Interfaz/Contrato de pedidoBo.
 * @author cinca
 */
public interface IPedidoBO {
    PedidoDTO guardarPedido(PedidoDTO pedido);
}
