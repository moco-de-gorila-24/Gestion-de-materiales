/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestionInventario;

import dtos.MaterialDTO;
import interfaces.IMaterialBO;
import java.util.List;
import objetosNegocio.MaterialBO;

/**
 *
 * @author cinca
 */
public class ControlInventario {
    
    private IMaterialBO materialBO;
    
    public ControlInventario() {
        materialBO = new MaterialBO();
    }
    
    public boolean validarMaterialDisponible(MaterialDTO material) {
        
        return false;
        
    }
    
    public List<MaterialDTO> consultarMaterial() {
        
       return materialBO.consultarMateriales();
        
    }
    
    public MaterialDTO consultarMaterial(String nombre) {
        
        return materialBO.consultarMaterial(nombre);
        
    }
    
    public boolean agregarMaterial(MaterialDTO material) {
        
        return false;
        
    }
}
