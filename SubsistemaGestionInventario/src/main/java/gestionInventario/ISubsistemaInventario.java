/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package gestionInventario;

import dtos.MaterialDTO;
import java.util.List;

/**
 *
 * @author cinca
 */
public interface ISubsistemaInventario {

    boolean validarMaterialDisponible(MaterialDTO material);
    List<MaterialDTO> consultarMaterial();
    MaterialDTO consultarMaterial(String nombre);
    boolean agregarMaterial(MaterialDTO material);
       
}
