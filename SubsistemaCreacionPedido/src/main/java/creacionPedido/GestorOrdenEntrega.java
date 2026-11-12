/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package creacionPedido;

import dtos.DireccionDTO;
import interfaces.IOrdenEntregaBO;
import java.util.Date;
import objetosNegocio.OrdenEntregaBO;

/**
 *
 * @author cinca
 */
public class GestorOrdenEntrega {
    
    private Date fechaEntrega;
    private Date fechaLimite;
    private String tipoEntrega;
    private DireccionDTO direccionActual;
    private IOrdenEntregaBO ordenEntregaBO;
    
    public GestorOrdenEntrega() {
        ordenEntregaBO = new OrdenEntregaBO();
    }

    public void limpiarDatos() {
        tipoEntrega = null;
        fechaEntrega = null;
        fechaLimite = null;
        direccionActual = null;
    }
    
}
