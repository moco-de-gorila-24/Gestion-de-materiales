/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestionInventario;

import dtos.MaterialDTO;
import dtos.PaqueteDTO;
import interfaces.IMaterialBO;
import interfaces.IPaqueteBO;
import java.util.List;
import objetosNegocio.MaterialBO;
import objetosNegocio.PaqueteBO;

/**
 *
 * @author cinca
 */
public class ControlInventario {
    
    private IPaqueteBO paqueteBO;
    private IMaterialBO materialBO;
    
    public ControlInventario() {
        materialBO = new MaterialBO();
        paqueteBO = new PaqueteBO();
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

    public List<PaqueteDTO> consultarPaquetes() {
        return paqueteBO.obtenerPaquetes();
    }
}
