/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestionInventario;

import dtos.DetalleReposicionDTO;
import dtos.MaterialDTO;
import dtos.SolicitudReposicionDTO;
import interfaces.IMaterialBO;
import interfaces.IReposicionBO;
import java.util.ArrayList;
import java.util.List;
import objetosNegocio.MaterialBO;
import objetosNegocio.ReposicionBO;

/**
 *
 * @author Jorge
 */
public class ControlReposicion {
    private final ISubsistemaInventario subsistemaInventario;
    private final IReposicionBO reposicionBO;

    public ControlReposicion(ISubsistemaInventario subsistemaInventario, IReposicionBO reposicionBO) {
        this.subsistemaInventario = subsistemaInventario;
        this.reposicionBO = reposicionBO;
    }

    /**
     * Consulta todos los materiales del inventario.
     * @return lista de materiales.
     */
    public List<DetalleReposicionDTO> consultarTodosLosMateriales() {
        List<DetalleReposicionDTO> detalles = new ArrayList<>();
        List<MaterialDTO> materiales = subsistemaInventario.consultarMaterial();

        if (materiales == null) {
            return detalles;
        }

        for (MaterialDTO material : materiales) {
            if (material == null) {
                continue;
            }

            DetalleReposicionDTO detalle = new DetalleReposicionDTO(material, material.getCantidadDisponible(), material.getStockMinimo(),0);
            detalles.add(detalle);
        }

        return detalles;
    }

    /**
     * Comprueba si un material ya tiene una solicitud pendiente.
     */
    private boolean tieneSolicitudPendiente(MaterialDTO material) {
        List<SolicitudReposicionDTO> solicitudes = reposicionBO.consultarSolicitudes();

        if (solicitudes == null) {
            return false;
        }

        for (SolicitudReposicionDTO solicitud : solicitudes) {
            if (solicitud == null || !"PENDIENTE".equalsIgnoreCase(solicitud.getEstado()) || solicitud.getDetalles() == null) {
                continue;
            }

            for (DetalleReposicionDTO detalle : solicitud.getDetalles()) {
                if (detalle != null && detalle.getMaterial() != null && detalle.getMaterial().getIdMaterial() == material.getIdMaterial()) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Genera una solicitud con los materiales y cantidades seleccionados.
     * @param detallesSeleccionados materiales que se desean reponer.
     * @return solicitud generada o null si no se pudo generar.
     */
    public SolicitudReposicionDTO generarSolicitudReposicion(List<DetalleReposicionDTO> detallesSeleccionados) {
        if (detallesSeleccionados == null || detallesSeleccionados.isEmpty()) {
            return null;
        }

        List<DetalleReposicionDTO> detallesValidos = new ArrayList<>();
        
        for (DetalleReposicionDTO detalle : detallesSeleccionados) {
            if (detalle == null || detalle.getMaterial() == null) {
                continue;
            }

            if (detalle.getCantidadSolicitada() <= 0) {
                continue;
            }

            if (tieneSolicitudPendiente(detalle.getMaterial())) {
                return null;
            }

            if (!reposicionBO.validarDetalle(detalle)) {
                return null;
            }

            detallesValidos.add(detalle);
        }

        if (detallesValidos.isEmpty()) {
            return null;
        }

        SolicitudReposicionDTO solicitud = new SolicitudReposicionDTO();
        solicitud.setDetalles(detallesValidos);
        return reposicionBO.guardarSolicitud(solicitud);
    }

    /**
     * Consulta las solicitudes de reposición.
     */
    public List<SolicitudReposicionDTO> consultarSolicitudesReposicion() {
        return reposicionBO.consultarSolicitudes();
    }
}