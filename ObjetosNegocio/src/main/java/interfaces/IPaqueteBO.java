/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import dtos.PaqueteDTO;
import java.util.List;

/**
 * Interfaz/Contrato de PaqueteBO.
 * @author cinca
 */
public interface IPaqueteBO {

    public List<PaqueteDTO> obtenerPaquetes();
    
}
