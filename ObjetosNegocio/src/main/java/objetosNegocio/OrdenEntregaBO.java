/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetosNegocio;

import almacen.Almacen;
import dtos.DireccionDTO;
import dtos.OrdenEntregaDTO;
import dtos.PedidoDTO;
import interfaces.IOrdenEntregaBO;
import java.util.Date;
import java.util.List;

/**
 * Objeto negocio que usa AlmacenTemporal temporalmente.
 * @author cinca
 */
public class OrdenEntregaBO implements IOrdenEntregaBO {

    @Override
    public List<DireccionDTO> consultarDirecciones() {
        return Almacen.obtenerDirecciones();
    }    

    @Override
    public boolean guardarDireccion(DireccionDTO direccion) {
        return Almacen.guardarDireccion(direccion);
    }

    @Override
    public OrdenEntregaDTO guardarOrdenEntrega(String tipoEntrega, Date fecha, DireccionDTO direccion, PedidoDTO pedido) {
        return Almacen.crearOrdenEntrega(tipoEntrega, fecha, direccion, pedido);
    }
    
    
    
  
}
