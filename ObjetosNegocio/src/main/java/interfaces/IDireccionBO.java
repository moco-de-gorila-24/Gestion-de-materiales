/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import dtos.DireccionDTO;
import java.util.List;

/**
 *
 * @author cinca
 */
public interface IDireccionBO {
        
    List<DireccionDTO> consultarDirecciones(int idCliente);
    
    boolean guardarDireccion(int idCliente, DireccionDTO direccion);
}
