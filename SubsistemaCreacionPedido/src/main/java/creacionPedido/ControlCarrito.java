/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package creacionPedido;

import dtos.DetallePropuestaDTO;
import dtos.MaterialDTO;
import dtos.PropuestaMaterialesDTO;
import interfaces.IPropuestaMaterialesBO;
import java.util.List;
import objetosNegocio.PropuestaMaterialesBO;

/**
 * Controlador del carrito (propuesta). Se comunica con las BO.
 * 
 * @author cinca
 */
public class ControlCarrito {
    private IPropuestaMaterialesBO propuestaBO;
    private PropuestaMaterialesDTO propuestaActual;
    
    public ControlCarrito() {
        propuestaBO = new PropuestaMaterialesBO();
    }   

    /**
     * Se asigna la propuesta activa de la venta para que se puedan realizar las 
     * operaciones de agregar, modificar o eliminar detalles.
     * 
     * @param propuesta la propuesta activa.
     */
    public void iniciarCarrito(PropuestaMaterialesDTO propuesta) {
        propuestaActual = propuesta;
    }

    /**
     * Metodo encargado de realizar la operacion de agregar un producto al 
     * carrito.
     * Si el material ya esta en la propuesta activa, se incrementa su cantidad,
     * de lo contrario se crea un nuevo detalle y se recalculan los totales y
     * se actualiza la propuesta mediante el BO correspondiente..
     * 
     * @param material material de la lista de materiales.
     * @param cantidad cantidad de unidades del material que se añadiran.
     */
    public void AgregarAlCarrito(MaterialDTO material, int cantidad) {
        if (material == null || cantidad <= 0) {
            return;
        }
        
        if (propuestaActual == null) {
            return;
        }

        List<DetallePropuestaDTO> detalles = propuestaActual.getDetalles();

        for (int i = 0; i < detalles.size(); i++) {
            DetallePropuestaDTO detalle = detalles.get(i);

            if (detalle.getMaterial().getIdMaterial() == material.getIdMaterial()) {
                detalle.setCantidad(detalle.getCantidad() + cantidad);
                recalcularPropuesta();
                propuestaBO.actualizarPropuesta(propuestaActual);
                return;
            }
        }

        detalles.add(new DetallePropuestaDTO(material, cantidad, material.getPrecioUnitario()));
        recalcularPropuesta();   
        propuestaBO.actualizarPropuesta(propuestaActual);
    }

     /**
     * Recalcula el subtotal y la cantidad de productos de la propuesta activa.
     * 
     */
    private void recalcularPropuesta() {
       if(propuestaActual == null) {
            return;
        }
        
        float subtot = 0;
        int cantidadTot = 0;
        for(int i= 0; i < propuestaActual.getDetalles().size(); i++) {
            DetallePropuestaDTO detalle = propuestaActual.getDetalles().get(i);
            subtot += detalle.getCantidad() * detalle.getPrecioUnitario();
            cantidadTot += detalle.getCantidad();
        }
        
        propuestaActual.setSubtotal(subtot);
        propuestaActual.setCantidadTotalProductos(cantidadTot);    
    }
    
}
