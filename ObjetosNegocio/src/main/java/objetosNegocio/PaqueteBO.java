/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetosNegocio;

import almacen.Almacen;
import dtos.PaqueteDTO;
import interfaces.IPaqueteBO;
import java.util.List;

/**
 * Objeto negocio que usa AlmacenTemporal temporalmente.
 * @author cinca
 */
public class PaqueteBO implements IPaqueteBO {
    @Override
    public List<PaqueteDTO> obtenerPaquetes() {
        return Almacen.obtenerPaquetes();
    }
    
}
