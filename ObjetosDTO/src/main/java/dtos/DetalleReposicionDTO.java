/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

/**
 *
 * @author Jorge
 */
public class DetalleReposicionDTO {

    private MaterialDTO material;
    private int cantidadActual;
    private int stockMinimo;
    private int cantidadSolicitada;

    public DetalleReposicionDTO() {
    }

    public DetalleReposicionDTO(MaterialDTO material, int cantidadActual, int stockMinimo, int cantidadSolicitada) {
        this.material = material;
        this.cantidadActual = cantidadActual;
        this.stockMinimo = stockMinimo;
        this.cantidadSolicitada = cantidadSolicitada;
    }

    public MaterialDTO getMaterial() {
        return material;
    }

    public void setMaterial(MaterialDTO material) {
        this.material = material;
    }

    public int getCantidadActual() {
        return cantidadActual;
    }

    public void setCantidadActual(int cantidadActual) {
        this.cantidadActual = cantidadActual;
    }

    public int getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(int stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public int getCantidadSolicitada() {
        return cantidadSolicitada;
    }

    public void setCantidadSolicitada(int cantidadSolicitada) {
        this.cantidadSolicitada = cantidadSolicitada;
    }
}
