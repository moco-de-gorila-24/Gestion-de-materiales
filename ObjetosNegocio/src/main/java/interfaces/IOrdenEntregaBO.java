/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import dtos.DireccionDTO;
import dtos.OrdenEntregaDTO;
import dtos.PedidoDTO;
import java.util.Date;
import java.util.List;

/**
 * Interfaz/Contrato de OrdenEntregaBO.
 * @author cinca
 */
public interface IOrdenEntregaBO {
    
    List<DireccionDTO> consultarDirecciones(int idCliente);
    
    boolean guardarDireccion(int idCliente, DireccionDTO direccion);
    
    OrdenEntregaDTO guardarOrdenEntrega(String tipoEntrega, Date fecha, DireccionDTO direccion, PedidoDTO pedido);
}
