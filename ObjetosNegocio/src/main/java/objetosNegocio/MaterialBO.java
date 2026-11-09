/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetosNegocio;

import almacen.Almacen;
import dtos.MaterialDTO;
import interfaces.IMaterialBO;
import java.util.List;

/**
 * Objeto negocio que usa AlmacenTemporal temporalmente.
 * @author cinca
 */
public class MaterialBO implements IMaterialBO {
    
    @Override
    public boolean validarMaterialDisponible(MaterialDTO material) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
     /**
     * Obtiene un material mediante su nombre.
     * 
     * @return el material que coincida con el nombre buscado.
     */
    @Override
    public MaterialDTO consultarMaterial(String nombre) {
        return Almacen.buscarMaterial(nombre);
    }

    /**
     * Obtiene la lista de materiales actualmente almacenados.
     * 
     * @return la lista de materiales almcenados.
     */
    @Override
    public List<MaterialDTO> consultarMateriales() {
        return Almacen.obtenerMateriales();
    }
    
}
