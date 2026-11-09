/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestionInventario;

import dtos.DetalleReposicionDTO;
import dtos.MaterialDTO;
import dtos.PaqueteDTO;
import dtos.SolicitudReposicionDTO;
import java.util.List;

/**
 *
 * @author cinca
 */
public class GestionInventarioFachada implements ISubsistemaInventario  {
    private ControlInventario controlInventario;
    
    public GestionInventarioFachada() {
        controlInventario = new ControlInventario();
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
    public List<DetalleReposicionDTO> consultarMaterialesInsuficientes() {
        return controlInventario.consultarMaterialesInsuficientes();
    }
    
    @Override
    public SolicitudReposicionDTO generarSolicitudReposicion() {
        return controlInventario.generarSolicitudReposicion();
    }

    @Override
    public List<SolicitudReposicionDTO> consultarSolicitudesReposicion() {
        return controlInventario.consultarSolicitudesReposicion();
    }
}
