/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

import java.util.List;

/**
 *
 * @author cinca
 */
public class PropuestaMaterialesDTO {
    private float subtotal;
    private int cantidadTotalProductos;
    private List<DetallePropuestaDTO> detalles;

    public PropuestaMaterialesDTO() {
    }

    public PropuestaMaterialesDTO(float subtotal, int cantidadTotalProductos, List<DetallePropuestaDTO> detalles) {
        this.subtotal = subtotal;
        this.cantidadTotalProductos = cantidadTotalProductos;
        this.detalles = detalles;
    }

    public float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
    }

    public int getCantidadTotalProductos() {
        return cantidadTotalProductos;
    }

    public void setCantidadTotalProductos(int cantidadTotalProductos) {
        this.cantidadTotalProductos = cantidadTotalProductos;
    }

    public List<DetallePropuestaDTO> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetallePropuestaDTO> detalles) {
        this.detalles = detalles;
    }
    
    
}
