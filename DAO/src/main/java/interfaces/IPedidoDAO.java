/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import Entidades.Pedido;
import excepciones.PersistenciaException;
import java.util.List;

/**
 *
 * @author cinca
 */
public interface IPedidoDAO {
    Pedido obtenerPedido(int idPedido) throws PersistenciaException;
    List<Pedido> obtenerPedidos()throws PersistenciaException;
    Pedido guardarPedido(Pedido pedido)throws PersistenciaException; 
}
