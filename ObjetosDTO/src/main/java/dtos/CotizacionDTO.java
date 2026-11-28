/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

import java.util.Date;

/**
 *
 * @author cinca
 */
public class CotizacionDTO {
    private float subtotal;
    private float total;
    private Date fechaEntrega;
    private Date fechaLimite;
    private float impuesto;
    private float costoEnvio;
    private PropuestaMaterialesDTO propuesta;
    private String tipoEntrega;

    public CotizacionDTO(float subtotal, float total, Date fechaEntrega, Date fechaLimite, float impuesto, float costoEnvio, PropuestaMaterialesDTO propuesta, String tipoEntrega) {
        this.subtotal = subtotal;
        this.total = total;
        this.fechaEntrega = fechaEntrega;
        this.fechaLimite= fechaLimite;
        this.impuesto = impuesto;
        this.costoEnvio = costoEnvio;
        this.propuesta = propuesta;
        this.tipoEntrega = tipoEntrega;
    }

    public float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public float getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(float impuesto) {
        this.impuesto = impuesto;
    }

    public float getCostoEnvio() {
        return costoEnvio;
    }

    public void setCostoEnvio(float costoEnvio) {
        this.costoEnvio = costoEnvio;
    }

    public PropuestaMaterialesDTO getPropuesta() {
        return propuesta;
    }

    public void setPropuesta(PropuestaMaterialesDTO propuesta) {
        this.propuesta = propuesta;
    }

    public String getTipoEntrega() {
        return tipoEntrega;
    }

    public void setTipoEntrega(String tipoEntrega) {
        this.tipoEntrega = tipoEntrega;
    }

    public Date getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(Date fechaLimite) {
        this.fechaLimite = fechaLimite;
    }
    
    
}
