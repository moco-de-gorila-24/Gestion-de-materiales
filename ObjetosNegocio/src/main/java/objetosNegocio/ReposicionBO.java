/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetosNegocio;

import almacen.Almacen;
import dtos.DetalleReposicionDTO;
import dtos.SolicitudReposicionDTO;
import interfaces.IReposicionBO;
import java.util.List;

/**
 *
 * @author Jorge
 */
public class ReposicionBO implements IReposicionBO {

    @Override
    public boolean validarDetalle(DetalleReposicionDTO detalle) {

        if (detalle == null || detalle.getMaterial() == null) {
            return false;
        }

        if (detalle.getCantidadActual() < 0 || detalle.getStockMinimo() < 0 || detalle.getCantidadSolicitada() <= 0) {
            return false;
        }

        if (detalle.getCantidadActual() >= detalle.getStockMinimo()) {
            return false;
        }

        int cantidadNecesaria = detalle.getStockMinimo() - detalle.getCantidadActual();
        return detalle.getCantidadSolicitada() == cantidadNecesaria;
    }

    @Override
    public boolean validarSolicitud(SolicitudReposicionDTO solicitud) {

        if (solicitud == null || solicitud.getDetalles() == null || solicitud.getDetalles().isEmpty()) {
            return false;
        }

        if (solicitud.getFechaSolicitud() == null || solicitud.getEstado() == null) {
            return false;
        }

        if (!solicitud.getEstado().equals("PENDIENTE")) {
            return false;
        }

        for (DetalleReposicionDTO detalle : solicitud.getDetalles()) {

            if (!validarDetalle(detalle)) {
                return false;
            }
        }

        return true;
    }
    
    @Override
    public SolicitudReposicionDTO guardarSolicitud(SolicitudReposicionDTO solicitud) {
        if (!validarSolicitud(solicitud)) {
            return null;
        }

        if (Almacen.guardarSolicitudReposicion(solicitud)) {
            return solicitud;
        }

        return null;
    }

    @Override
    public List<SolicitudReposicionDTO> consultarSolicitudes() {
        return Almacen.obtenerSolicitudesReposicion();
    }
}
