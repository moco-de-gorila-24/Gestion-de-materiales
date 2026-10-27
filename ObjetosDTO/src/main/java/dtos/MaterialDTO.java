/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

/**
 *
 * @author cinca
 */
public class MaterialDTO {
    private int idMaterial;
    private String nombre;
    private String marca;
    private float precioUnitario;
    private String unidadMedida;
    private int cantidadDisponible;
    private String tipo;

    public MaterialDTO() {
    }

    public MaterialDTO(int idMaterial, String nombre, String marca, float precioUnitario, String unidadMedida, int cantidadDisponible) {
        this.idMaterial = idMaterial;
        this.nombre = nombre;
        this.marca = marca;
        this.precioUnitario = precioUnitario;
        this.unidadMedida = unidadMedida;
        this.cantidadDisponible = cantidadDisponible;
        this.tipo = obtenerTipo(nombre);
    }

    public MaterialDTO(int idMaterial, String nombre, String marca, float precioUnitario, String unidadMedida, int cantidadDisponible, String tipo) {
        this.idMaterial = idMaterial;
        this.nombre = nombre;
        this.marca = marca;
        this.precioUnitario = precioUnitario;
        this.unidadMedida = unidadMedida;
        this.cantidadDisponible = cantidadDisponible;
        this.tipo = tipo;
    }

    private static String obtenerTipo(String nombre) {
        String nombreMin = nombre == null ? "" : nombre.toLowerCase();

        if (nombreMin.contains("cemento") || nombreMin.contains("mortero")) {
            return "Cemento";
        }

        if (nombreMin.contains("block") || nombreMin.contains("bloque")) {
            return "Block";
        }

        if (nombreMin.contains("varilla")) {
            return "Varilla";
        }

        return "Otro";
    }

    public int getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(int idMaterial) {
        this.idMaterial = idMaterial;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public float getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(float precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public int getCantidadDisponible() {
        return cantidadDisponible;
    }

    public void setCantidadDisponible(int cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
