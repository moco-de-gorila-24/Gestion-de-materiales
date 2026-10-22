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
public class PedidoDTO {
    private int idPedido;
    private String formaSuministro;
    private Date fecha;
    private String estado;
    private float total;
    private ClienteDTO cliente;

    public PedidoDTO(int idPedido, String formaSuministro, Date fecha, String estado, float total, ClienteDTO cliente) {
        this.idPedido = idPedido;
        this.formaSuministro = formaSuministro;
        this.fecha = fecha;
        this.estado = estado;
        this.total = total;
        this.cliente = cliente;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public String getFormaSuministro() {
        return formaSuministro;
    }

    public void setFormaSuministro(String formaSuministro) {
        this.formaSuministro = formaSuministro;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public ClienteDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }
    
    
}
