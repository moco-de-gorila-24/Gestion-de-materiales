/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package almacenDatos;

import dtos.ClienteDTO;
import dtos.CotizacionDTO;
import dtos.DetallePaqueteDTO;
import dtos.DetallePedidoDTO;
import dtos.DetallePropuestaDTO;
import dtos.DireccionDTO;
import dtos.MaterialDTO;
import dtos.PaqueteDTO;
import dtos.PedidoDTO;
import dtos.PropuestaMaterialesDTO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author cinca
 */
public class Almacen {
    
    private static PropuestaMaterialesDTO propuesta;
    
    public static List<MaterialDTO> obtenerMateriales() {
        List<MaterialDTO> materialesDto = new ArrayList<>();
        materialesDto.add(new MaterialDTO(1, "Block de construccion", "cualquiera", 12f, "pieza", 500, "Block"));
        materialesDto.add(new MaterialDTO(2, "Varilla metalica corrugada 3/8", "cualquiera", 20f, "m", 300, "Varilla"));
        materialesDto.add(new MaterialDTO(3, "Bulto de cemento 25kg", "Cemex", 50f, "bulto", 9, "Cemento"));
        materialesDto.add(new MaterialDTO(4, "Bulto de cemento 25kg", "PANAM", 48f, "bulto", 40, "Cemento"));
        return materialesDto;
    }
    
    public static List<PaqueteDTO> obtenerPaquetes() {
        List<DetallePaqueteDTO> detallespaqueteDto = new ArrayList<>();
        // o sea que la barda usa 50 blocks de construccion y 10 bultos de cemento
        detallespaqueteDto.add(new DetallePaqueteDTO(obtenerMateriales().get(0), 50));
        detallespaqueteDto.add(new DetallePaqueteDTO(obtenerMateriales().get(2), 10));
        
        List<PaqueteDTO> paquetesDto = new ArrayList<>();
        paquetesDto.add(new PaqueteDTO(1, "Barda de block 10m", 10, "m", "Barda", detallespaqueteDto));
        return paquetesDto;
    }
    
    public static ClienteDTO obtenerCliente() {
        return new ClienteDTO(1, "Michelle", "Castillo", "Vizcarra", "1234567890");
    }

    public static boolean GuardarCliente(ClienteDTO cliente){
        List<ClienteDTO> clientes = new ArrayList<>();
        if(clientes.add(cliente)){
            return true;
        }
        return false;
    }
    
    public static PedidoDTO obtenerPedido() {
        return new PedidoDTO(3, "A domicilio", new Date(), "esperando confirmacion", 1376f, obtenerCliente());
    }
    
    public static PropuestaMaterialesDTO obtenerPropuestaMock() {
        
        List<DetallePropuestaDTO> detallesPropuestaDto = new ArrayList<>();
        
        detallesPropuestaDto.add(new DetallePropuestaDTO(obtenerMateriales().get(0), 50, 12f));
        detallesPropuestaDto.add(new DetallePropuestaDTO(obtenerMateriales().get(2), 10, 50f));
        return new PropuestaMaterialesDTO(1100f, 60, detallesPropuestaDto);
    }
    
    public static PropuestaMaterialesDTO obtenerPropuesta() {
        if(propuesta == null) {
            return crearPropuesta();
        }
        return propuesta;
    }
    
    public static CotizacionDTO obtnerCotizacion() {
        return new CotizacionDTO(1100F, 1376F, new Date(), 176f, 100f, obtenerPropuesta());
    }
    
    public static List<DireccionDTO> obtenerDirecciones() {
        List<DireccionDTO> direcciones = new ArrayList<>();
        direcciones.add(new DireccionDTO("Mar amarillo", "Prados del Tepeyac", 123, 54678));
        return direcciones;
    }
    
    public static PropuestaMaterialesDTO crearPropuesta() {
        propuesta = new PropuestaMaterialesDTO(0f, 0, new ArrayList<>());
        return propuesta;
    }
    
    public void crearDetallePropuesta(MaterialDTO material, int cantidad) {
        if (material == null || cantidad <= 0) {
            return;
        }
        
        if(propuesta == null) {
            crearPropuesta();
        }
        
        List<DetallePropuestaDTO> detalles = propuesta.getDetalles();

        for (int i = 0; i < detalles.size(); i++) {
            DetallePropuestaDTO detalle = detalles.get(i);

            if (detalle.getMaterial().getIdMaterial() == material.getIdMaterial()) {
                
                int nuevaCantidad = detalle.getCantidad() + cantidad;
                detalle.setCantidad(nuevaCantidad);

                float subtotal = propuesta.getSubtotal()+ cantidad * detalle.getPrecioUnitario();
                propuesta.setSubtotal(subtotal);
                propuesta.setCantidadTotalProductos(propuesta.getCantidadTotalProductos() + cantidad);
                return;
            }
        }

        detalles.add(new DetallePropuestaDTO(material, cantidad, material.getPrecioUnitario()));
        
        float subtotal = propuesta.getSubtotal()+ cantidad * material.getPrecioUnitario();
        propuesta.setSubtotal(subtotal);
        propuesta.setCantidadTotalProductos(propuesta.getCantidadTotalProductos() + cantidad);
    }
    
    public DetallePropuestaDTO validarDisponibilidad() {
       List<DetallePropuestaDTO> detalles = obtenerPropuesta().getDetalles();
    
        for (int i = 0; i < detalles.size(); i++) {
            DetallePropuestaDTO detalle = detalles.get(i);
            MaterialDTO material = detalle.getMaterial();

            if (material == null || detalle.getCantidad() > material.getCantidadDisponible()) {
                return detalle;
            }
        }
        
        return null;
    }
    
    public List<MaterialDTO> obtenerMaterialesSustitutos(DetallePropuestaDTO detalle) {
        List<MaterialDTO> sustitutos = new ArrayList<>();

        if (detalle == null || detalle.getMaterial() == null) {
            return sustitutos;
        }

        List<DetallePropuestaDTO> detalles = obtenerPropuesta().getDetalles();
        List<MaterialDTO> materialesAlmacen = obtenerMateriales();
        MaterialDTO material = detalle.getMaterial();

        for (int i = 0; i < materialesAlmacen.size(); i++) {
            MaterialDTO candidato = materialesAlmacen.get(i);

            boolean mismoTipo = candidato.getTipo() != null && material.getTipo() != null && candidato.getTipo().equalsIgnoreCase(material.getTipo());
            boolean diferente = candidato.getIdMaterial() != material.getIdMaterial();
            int cantidadNecesaria = calcularCantidadTotal(detalles, detalle, candidato);
            boolean stockSuficiente = candidato.getCantidadDisponible() >= cantidadNecesaria;

            if (mismoTipo && diferente && stockSuficiente) {
                sustitutos.add(candidato);
            }
        }

        return sustitutos;
    }

    private int calcularCantidadTotal(List<DetallePropuestaDTO> detalles, DetallePropuestaDTO detalleActual, MaterialDTO candidato) {

        if (detalles == null || detalleActual == null || candidato == null) {
            return 0;
        }

        int cantidadTotal = detalleActual.getCantidad();

        for (int i = 0; i < detalles.size(); i++) {
            DetallePropuestaDTO otro = detalles.get(i);

            if (otro != detalleActual && otro.getMaterial() != null && otro.getMaterial().getIdMaterial() == candidato.getIdMaterial()) {
                cantidadTotal += otro.getCantidad();
            }
        }

        return cantidadTotal;
    }
    
    public boolean sustituirMaterial(DetallePropuestaDTO detalle, MaterialDTO nuevo) {

        if (propuesta == null || detalle == null || nuevo == null || detalle.getMaterial() == null) {
            return false;
        }

        List<DetallePropuestaDTO> detalles = propuesta.getDetalles();

        if (!detalles.contains(detalle)) {
            return false;
        }

        int cantidadNecesaria = calcularCantidadTotal(detalles, detalle, nuevo);

        if (nuevo.getCantidadDisponible() < cantidadNecesaria) {
            return false;
        }

        DetallePropuestaDTO existente = null;

        for (int i = 0; i < detalles.size(); i++) {
            DetallePropuestaDTO otro = detalles.get(i);

            if (otro != detalle && otro.getMaterial() != null && otro.getMaterial().getIdMaterial() == nuevo.getIdMaterial()) {
                existente = otro;
                break;
            }
        }

        float subtotalAnteriorDetalle = detalle.getCantidad() * detalle.getPrecioUnitario();

        if (existente != null) {

            // Ya existe el material en el carrito.
            float subtotalAnteriorExistente = existente.getCantidad() * existente.getPrecioUnitario();
            int cantidadTotal = existente.getCantidad() + detalle.getCantidad();
            float subtotalNuevo = cantidadTotal * nuevo.getPrecioUnitario();

            existente.setCantidad(cantidadTotal);
            existente.setMaterial(nuevo);
            existente.setPrecioUnitario(nuevo.getPrecioUnitario());
            detalles.remove(detalle);

            propuesta.setSubtotal(propuesta.getSubtotal() - subtotalAnteriorDetalle - subtotalAnteriorExistente + subtotalNuevo);

        } else {

            // No existe el material en el carrito.
            detalle.setMaterial(nuevo);
            detalle.setPrecioUnitario(nuevo.getPrecioUnitario());
            float subtotalNuevo = detalle.getCantidad() * nuevo.getPrecioUnitario();

            propuesta.setSubtotal(propuesta.getSubtotal() - subtotalAnteriorDetalle + subtotalNuevo);
        }

        return true;
    }

    public int obtenerCantidadNecesaria(DetallePropuestaDTO detalle, MaterialDTO candidato) {

        if (propuesta == null || detalle == null || candidato == null) {
            return 0;
        }

        return calcularCantidadTotal(propuesta.getDetalles(), detalle, candidato);
    }

    public void eliminarDetallePropuesta(DetallePropuestaDTO detalle) {
        if(propuesta == null || detalle == null) {
            return;
        }
        
        if(propuesta.getDetalles().remove(detalle)) {
            propuesta.setSubtotal(propuesta.getSubtotal() - detalle.getCantidad() * detalle.getPrecioUnitario());
            propuesta.setCantidadTotalProductos(propuesta.getCantidadTotalProductos() - detalle.getCantidad());            
        }
    }
}
