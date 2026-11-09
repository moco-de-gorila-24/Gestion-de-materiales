/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetosNegocio;

import almacen.Almacen;
import dtos.PropuestaMaterialesDTO;
import interfaces.IPropuestaMaterialesBO;

/**
 * Objeto negocio que usa AlmacenTemporal temporalmente.
 * @author cinca
 */
public class PropuestaMaterialesBO  implements IPropuestaMaterialesBO {

    /**
     * Actualiza en almacen la propuesta obtenida.
     * 
     * @param propuesta propuesta con los datos actualizados.
     * @return true si se actualiza la propuesta, false en caso de lo contrario.
     */
    @Override
    public boolean actualizarPropuesta(PropuestaMaterialesDTO propuesta) {
        if(propuesta == null) {
            return false;
        }
        return Almacen.actualizarPropuesta(propuesta);
    }
    
}
