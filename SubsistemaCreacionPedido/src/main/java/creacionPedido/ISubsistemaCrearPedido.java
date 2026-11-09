/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package creacionPedido;

import dtos.MaterialDTO;
import dtos.PropuestaMaterialesDTO;

/**
 * Interfaz del subsistema para la creacion del pedido. Ofrece a  
 * la obtencion de la informacion que necesite cualquier modulo.
 * 
 * @author cinca
 */
public interface ISubsistemaCrearPedido { 

    /**
     *
     * @param propuesta
     */
    void iniciarCarrito(PropuestaMaterialesDTO propuesta);

    /**
     *
     * @param material
     * @param cantidad
     */
    void agregarAlCarrito(MaterialDTO material, int cantidad);
    
}
