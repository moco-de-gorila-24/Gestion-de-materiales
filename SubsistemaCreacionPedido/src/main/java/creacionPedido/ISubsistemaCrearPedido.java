/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package creacionPedido;

import dtos.DetallePropuestaDTO;
import dtos.MaterialDTO;
import dtos.PropuestaMaterialesDTO;
import java.util.List;

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
    
    boolean eliminarDelCarrito(DetallePropuestaDTO detalle);
    
    void limpiarPropuestaActual();
    
    void cancelarVenta();
    
    boolean sustituirMaterial(DetallePropuestaDTO detalle, MaterialDTO nuevo);
    
    List<DetallePropuestaDTO> getDetallesCarrito();
    
    int obtenerCantidadNecesaria(DetallePropuestaDTO detalle, MaterialDTO candidato);
    
    List<MaterialDTO> obtenerMaterialesSustitutos(DetallePropuestaDTO detalle);
    
    float obtenerSubtotal();
    
    void cambiarCantidad(DetallePropuestaDTO detalle, int cantidadCambiar, int cantidadOriginal);
}
