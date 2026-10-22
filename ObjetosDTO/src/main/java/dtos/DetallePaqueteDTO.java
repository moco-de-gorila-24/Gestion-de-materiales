/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

/**
 *
 * @author cinca
 */
public class DetallePaqueteDTO {
    private MaterialDTO material;
    private int cantidadMaterial;

    public DetallePaqueteDTO(MaterialDTO material, int cantidadMaterial) {
        this.material = material;
        this.cantidadMaterial = cantidadMaterial;
    }

    public MaterialDTO getMaterial() {
        return material;
    }

    public void setMaterial(MaterialDTO material) {
        this.material = material;
    }

    public int getCantidadMaterial() {
        return cantidadMaterial;
    }

    public void setCantidadMaterial(int cantidadMaterial) {
        this.cantidadMaterial = cantidadMaterial;
    }
    
    
}
