/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

/**
 *
 * @author cinca
 */
public class DetallePropuestaDTO {
    private MaterialDTO material;
    private int cantidad;
    private float precioUnitario;

    public DetallePropuestaDTO(MaterialDTO material, int cantidad, float precioUnitario) {
        this.material = material;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    public MaterialDTO getMaterial() {
        return material;
    }

    public void setMaterial(MaterialDTO material) {
        this.material = material;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(float precioUnitario) {
        this.precioUnitario = precioUnitario;
    }
    
    
}
