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
public class OrdenEntregaDTO {
    private String tipoEntrega;
    private Date fecha;
    private DireccionDTO direccion;
    private PedidoDTO pedido;

    public OrdenEntregaDTO(String tipoEntrega, Date fecha, DireccionDTO direccion, PedidoDTO pedido) {
        this.tipoEntrega = tipoEntrega;
        this.fecha = fecha;
        this.direccion = direccion;
        this.pedido = pedido;
    }

    public String getTipoEntrega() {
        return tipoEntrega;
    }

    public void setTipoEntrega(String tipoEntrega) {
        this.tipoEntrega = tipoEntrega;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public DireccionDTO getDireccion() {
        return direccion;
    }

    public void setDireccion(DireccionDTO direccion) {
        this.direccion = direccion;
    }

    public PedidoDTO getPedido() {
        return pedido;
    }

    public void setPedido(PedidoDTO pedido) {
        this.pedido = pedido;
    }
    
    
}
