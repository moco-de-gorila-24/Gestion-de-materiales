/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Jorge
 */
public class SolicitudReposicionDTO {

    private int idSolicitud;
    private Date fechaSolicitud;
    private String estado;
    private List<DetalleReposicionDTO> detalles;

    public SolicitudReposicionDTO() {
        this.fechaSolicitud = new Date();
        this.estado = "PENDIENTE";
        this.detalles = new ArrayList<>();
    }

    public SolicitudReposicionDTO(int idSolicitud, Date fechaSolicitud, String estado, List<DetalleReposicionDTO> detalles) {
        this.idSolicitud = idSolicitud;
        this.fechaSolicitud = fechaSolicitud;
        this.estado = estado;
        this.detalles = detalles != null ? detalles : new ArrayList<>();
    }

    public int getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(int idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<DetalleReposicionDTO> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleReposicionDTO> detalles) {
        this.detalles = detalles != null ? detalles : new ArrayList<>();
    }
}
