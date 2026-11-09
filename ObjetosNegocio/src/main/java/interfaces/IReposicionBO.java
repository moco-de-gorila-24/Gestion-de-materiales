/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import dtos.DetalleReposicionDTO;
import dtos.SolicitudReposicionDTO;
import java.util.List;

/**
 *
 * @author Jorge
 */
public interface IReposicionBO {
    boolean validarDetalle(DetalleReposicionDTO detalle);
    boolean validarSolicitud(SolicitudReposicionDTO solicitud);
    SolicitudReposicionDTO guardarSolicitud(SolicitudReposicionDTO solicitud);
    List<SolicitudReposicionDTO> consultarSolicitudes();
}
