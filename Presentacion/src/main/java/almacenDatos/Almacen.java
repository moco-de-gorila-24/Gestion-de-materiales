/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package almacenDatos;

import dtos.ClienteDTO;
import dtos.CotizacionDTO;
import dtos.DetallePaqueteDTO;
import dtos.DetallePropuestaDTO;
import dtos.DireccionDTO;
import dtos.MaterialDTO;
import dtos.PaqueteDTO;
import dtos.PedidoDTO;
import dtos.PropuestaMaterialesDTO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author cinca
 */
public class Almacen {
    public static List<MaterialDTO> obtenerMateriales() {
        List<MaterialDTO> materialesDto = new ArrayList<>();
        materialesDto.add(new MaterialDTO(1, "Block de construccion", "cualquiera", 12f, "pieza", 500));
        materialesDto.add(new MaterialDTO(2, "Varilla metalica corrugada 3/8", "cualquiera", 20f, "m", 300));
        materialesDto.add(new MaterialDTO(3, "Bulto de cemento 25kg", "Cemex", 50f, "kg", 20));
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
        return new ClienteDTO(1, "Michelle", "Castillo", "Vizcarra", "6444209828");
    }
    
    public static PedidoDTO obtenerPedido() {
        return new PedidoDTO(3, "A domicilio", new Date(), "esperando confirmacion", 1376f, obtenerCliente());
    }
    
    public static PropuestaMaterialesDTO obtenerPropuesta() {
        List<DetallePropuestaDTO> detallesPropuestaDto = new ArrayList<>();
        detallesPropuestaDto.add(new DetallePropuestaDTO(obtenerMateriales().get(0), 50, 12f));
        detallesPropuestaDto.add(new DetallePropuestaDTO(obtenerMateriales().get(2), 10, 50f));
        return new PropuestaMaterialesDTO(1100f, 60, detallesPropuestaDto);
    }
    
    public static CotizacionDTO obtnerCotizacion() {
        return new CotizacionDTO(1100F, 1376F, new Date(), 176f, 100f, obtenerPropuesta());
    }
    
    public static List<DireccionDTO> obtenerDirecciones() {
        List<DireccionDTO> direcciones = new ArrayList<>();
        direcciones.add(new DireccionDTO("Mar amarillo", "Prados del Tepeyac", 123, 54678));
        return direcciones;
    }
}
