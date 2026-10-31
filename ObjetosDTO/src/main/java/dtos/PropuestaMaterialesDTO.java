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
public class PropuestaMaterialesDTO {
    private int idPropuesta;
    private ClienteDTO cliente;
    private String estado;
    private float subtotal;
    private int cantidadTotalProductos;
    private List<DetallePropuestaDTO> detalles;

    public PropuestaMaterialesDTO() {
    }

    public PropuestaMaterialesDTO(float subtotal, int cantidadTotalProductos, List<DetallePropuestaDTO> detalles) {
        this.subtotal = subtotal;
        this.cantidadTotalProductos = cantidadTotalProductos;
        this.detalles = detalles;
        this.estado = "PENDIENTE";
    }
    
    public PropuestaMaterialesDTO(int idPropuesta, ClienteDTO cliente, String estado, float subtotal, int cantidadTotalProductos, List<DetallePropuestaDTO> detalles) {
        this.idPropuesta = idPropuesta;
        this.cliente = cliente;
        this.estado = estado;
        this.subtotal = subtotal;
        this.cantidadTotalProductos = cantidadTotalProductos;
        this.detalles = detalles;
    }

    public int getIdPropuesta() {
        return idPropuesta;
    }

    public void setIdPropuesta(int idPropuesta) {
        this.idPropuesta = idPropuesta;
    }

    public ClienteDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }
    
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
    }

    public int getCantidadTotalProductos() {
        return cantidadTotalProductos;
    }

    public void setCantidadTotalProductos(int cantidadTotalProductos) {
        this.cantidadTotalProductos = cantidadTotalProductos;
    }

    public List<DetallePropuestaDTO> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetallePropuestaDTO> detalles) {
        this.detalles = detalles;
    }
    
    
}
