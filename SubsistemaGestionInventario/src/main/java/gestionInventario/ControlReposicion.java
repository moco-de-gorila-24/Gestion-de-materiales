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

    private IMaterialBO materialBO;
    private IReposicionBO reposicionBO;

    public ControlReposicion() {
        materialBO = new MaterialBO();
        reposicionBO = new ReposicionBO();
    }

    /**
     * Identifica los materiales con existencias inferiores al minimo.
     *
     * @return lista de detalles de reposicion.
     */
    public List<DetalleReposicionDTO> identificarMaterialesInsuficientes() {

        List<DetalleReposicionDTO> materialesInsuficientes = new ArrayList<>();
        List<MaterialDTO> materiales = materialBO.consultarMateriales();

        for (MaterialDTO material : materiales) {

            if (material == null) {
                continue;
            }

            int cantidadActual = material.getCantidadDisponible();
            int stockMinimo = material.getStockMinimo();

            if (cantidadActual < stockMinimo) {

                int cantidadNecesaria = stockMinimo - cantidadActual;
                DetalleReposicionDTO detalle = new DetalleReposicionDTO(material, cantidadActual, stockMinimo, cantidadNecesaria);

                if (reposicionBO.validarDetalle(detalle)) {
                    materialesInsuficientes.add(detalle);
                }
            }
        }

        return materialesInsuficientes;
    }
    
    public SolicitudReposicionDTO generarSolicitudReposicion() {
        List<DetalleReposicionDTO> detalles = identificarMaterialesInsuficientes();

        if (detalles.isEmpty()) {
            return null;
        }

        SolicitudReposicionDTO solicitud = new SolicitudReposicionDTO();
        solicitud.setDetalles(detalles);
        return reposicionBO.guardarSolicitud(solicitud);
    }

    public List<SolicitudReposicionDTO> consultarSolicitudesReposicion() {
        return reposicionBO.consultarSolicitudes();
    }

}
