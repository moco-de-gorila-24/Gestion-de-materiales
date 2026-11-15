/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestionInventario;

import dtos.DetalleReposicionDTO;
import dtos.MaterialDTO;
import dtos.PaqueteDTO;
import dtos.SolicitudReposicionDTO;
import interfaces.IReposicionBO;
import java.util.List;
import objetosNegocio.ReposicionBO;

/**
 *
 * @author cinca
 */
public class GestionInventarioFachada implements ISubsistemaInventario  {
    private ControlInventario controlInventario;
    private final ControlReposicion controlReposicion;
    
    public GestionInventarioFachada() {
        controlInventario = new ControlInventario();
        IReposicionBO reposicionBO = new ReposicionBO();
        controlReposicion = new ControlReposicion(this, reposicionBO);
    }

    @Override
    public boolean validarMaterialDisponible(MaterialDTO material) {
        return controlInventario.validarMaterialDisponible(material);
    }

    @Override
    public List<MaterialDTO> consultarMaterial() {
        return controlInventario.consultarMaterial();
    }

    @Override
    public MaterialDTO consultarMaterial(String nombre) {
        return controlInventario.consultarMaterial(nombre);
    }

    @Override
    public boolean agregarMaterial(MaterialDTO material) {
        return controlInventario.agregarMaterial(material);
    }

    @Override
    public List<PaqueteDTO> consultarPaquetes() {
        return controlInventario.consultarPaquetes();
    }
    
    @Override
    public List<DetalleReposicionDTO> consultarTodosLosMateriales() {
        return controlReposicion.consultarTodosLosMateriales();
    }
    
    public SolicitudReposicionDTO generarSolicitudReposicion(List<DetalleReposicionDTO> detalles) {
        return controlReposicion.generarSolicitudReposicion(detalles);
    }
    
    @Override
    public List<SolicitudReposicionDTO> consultarSolicitudesReposicion() {
        return controlReposicion.consultarSolicitudesReposicion();
    }

    @Override
    public SolicitudReposicionDTO generarSolicitudReposicion() {
        return controlReposicion.generarSolicitudReposicion(controlReposicion.consultarTodosLosMateriales());
    }
}
