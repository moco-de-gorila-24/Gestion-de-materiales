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

    @Override
    public MaterialDTO consultarMaterial(String nombre) {
        return Almacen.buscarMaterial(nombre);
    }

    @Override
    public List<MaterialDTO> consultarMateriales() {
        return Almacen.obtenerMateriales();
    }
    
}
