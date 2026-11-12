/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package creacionPedido;

import dtos.CotizacionDTO;
import dtos.PropuestaMaterialesDTO;
import java.util.Date;
import objetosNegocio.CotizacionBO;

/**
 *
 * @author cinca
 */
public class GestorCotizacion {
    private CotizacionBO cotizacionBO;
    
    public GestorCotizacion() {
        cotizacionBO = new CotizacionBO();
    }
    
    public CotizacionDTO generarCotizacion(PropuestaMaterialesDTO propuestaActual, String tipoEntrega) {
        if (propuestaActual == null) {
            return null;
        }

        float subtotal = propuestaActual.getSubtotal();
        float impuesto = subtotal * 0.16f;
        float costoEnvio = 0f;
        
        if("DOMICILIO".equals(tipoEntrega)) {
           costoEnvio = 100f;
        }
        
        float total = subtotal + impuesto + costoEnvio;

        return new CotizacionDTO(subtotal, total, new Date(), impuesto, costoEnvio, propuestaActual);
    }   
}
