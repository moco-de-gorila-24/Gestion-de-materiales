/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetosNegocio;

import almacen.Almacen;
import dtos.DireccionDTO;
import interfaces.IDireccionBO;
import java.util.List;

/**
 *
 * @author cinca
 */
public class DireccionBO implements IDireccionBO {
    private static DireccionBO instancia;
    
    private DireccionBO() {
        
    }
    
    public static DireccionBO getInstance() {
        if(instancia == null) {
            instancia = new DireccionBO();
        }
        return instancia;
    }
    
    @Override
    public List<DireccionDTO> consultarDirecciones(int idCliente) {
        return Almacen.obtenerDirecciones(idCliente);
    }    

    @Override
    public boolean guardarDireccion(int idCliente, DireccionDTO direccion) {
        return Almacen.guardarDireccion(idCliente, direccion);
    } 
}
