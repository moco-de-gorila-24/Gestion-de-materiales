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
    private Date fecha;
    private float impuesto;
    private float costoEnvio;
    private PropuestaMaterialesDTO propuesta;

    public CotizacionDTO(float subtotal, float total, Date fecha, float impuesto, float costoEnvio, PropuestaMaterialesDTO propuesta) {
        this.subtotal = subtotal;
        this.total = total;
        this.fecha = fecha;
        this.impuesto = impuesto;
        this.costoEnvio = costoEnvio;
        this.propuesta = propuesta;
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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
    
    
}
