/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

import java.util.List;

/**
 *
 * @author cinca
 */
public class PaqueteDTO {
    private int idPaquete;
    private String descripcion;
    private int medida;
    private String unidadMedida;
    private String tipoPaquete;
    private List<DetallePaqueteDTO> detalles;

    public PaqueteDTO(int idPaquete, String descripcion, int medida, String unidadMedida, String tipoPaquete, List<DetallePaqueteDTO> detalles) {
        this.idPaquete = idPaquete;
        this.descripcion = descripcion;
        this.medida = medida;
        this.unidadMedida = unidadMedida;
        this.tipoPaquete = tipoPaquete;
        this.detalles = detalles;
    }

    public int getIdPaquete() {
        return idPaquete;
    }

    public void setIdPaquete(int idPaquete) {
        this.idPaquete = idPaquete;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getMedida() {
        return medida;
    }

    public void setMedida(int medida) {
        this.medida = medida;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public String getTipoPaquete() {
        return tipoPaquete;
    }

    public void setTipoPaquete(String tipoPaquete) {
        this.tipoPaquete = tipoPaquete;
    }

    public List<DetallePaqueteDTO> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetallePaqueteDTO> detalles) {
        this.detalles = detalles;
    }
    
    
}
