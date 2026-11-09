/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import dtos.MaterialDTO;
import java.util.List;

/**
 * Interfaz/Contrato de MaterialBO.
 * @author cinca
 */
public interface IMaterialBO {
    boolean validarMaterialDisponible(MaterialDTO material);
    MaterialDTO consultarMaterial(String nombre);
    List<MaterialDTO> consultarMateriales();
}
