/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package creacionPedido;

import dtos.CotizacionDTO;
import dtos.PropuestaMaterialesDTO;
import excepciones.CreacionPedidoException;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Gestor encargado de generar la cotizacion a partir del tipo de entrega
 * establecido por el usuario y sus productos seleccionados, al igual que 
 * estimar las fechas de entrega/recoger para contribuir a la decision final
 * del cliente.
 * 
 * @author cinca
 */
public class GestorCotizacion {
    
    private String tipoEntrega;
    private Date fechaEntrega;
    private Date fechaLimite;
    private CotizacionDTO cotizacionActual;
    
    /**
     * Metodo encargado de establecer el tipo de entrega seleccionado por el 
     * usuario.
     */
    public void establecerTipoEntrega(String tipo) {
        tipoEntrega = tipo;
    }
    /**
     * Metodo encargado para obtener el tipo de entrega seleccionado por el 
     * usuario.
     */
    public String obtenerTipoEntrega() {
        return tipoEntrega;
    }
    
    /**
     * Metodo encargado de generar la cotizacion a partir de la propuesta cargada 
     * actual y el tipo de entrega seleccionado para la decision del costo de 
     * envio.
     * 
     * @param propuestaActual la propuesta actual cargada con los productos seleccionados.
     * @param tipoEntrega tipo de entrega seleccionada (a domicilio o a recoger en tienda)
     * @return la nueva cotizacion generada.
     */
    public CotizacionDTO generarCotizacion(PropuestaMaterialesDTO propuestaActual, String tipoEntrega) throws CreacionPedidoException {
        if (propuestaActual == null) {
            throw new CreacionPedidoException("No hay una propuesta actual valida para cotizar.");
        }
       
        if (tipoEntrega == null || !"DOMICILIO".equals(tipoEntrega) && !"TIENDA".equals(tipoEntrega)) {
            throw new CreacionPedidoException("No hay un tipo de entrega valido.");
        }

        float subtotal = calcularSubtotal(propuestaActual);
        float impuesto = calcularImpuesto(subtotal);
        float costoEnvio = calcularCostoEnvio(tipoEntrega);
                
        if (fechaEntrega == null) {
            throw new CreacionPedidoException("La fecha de entrega no pudo ser calculada correctamente.");
        }
  
        return cotizacionActual = new CotizacionDTO(subtotal, calcularTotal(subtotal, impuesto, costoEnvio), fechaEntrega, fechaLimite, 
                impuesto, costoEnvio, propuestaActual, tipoEntrega);
    }  
    
    /**
     * Metodo encargado de regresar la cotizacion generada actual.
     * 
     * @return la cotizacion actual generada.
     */
    public CotizacionDTO obtenerCotizacionActual() {
        return cotizacionActual;
    }
    /**
     * Metodo encargado de obtener el subtotal de la propuesta actual y devolverla
     * para hacer los demas calculos.
     * 
     * @return el subtotal de la propuesta actual.
     */
    private float calcularSubtotal(PropuestaMaterialesDTO propuestaActual) {
        if (propuestaActual == null) {
            throw new CreacionPedidoException("No hay una propuesta actual valida para cotizar.");
        }
        
        if (propuestaActual.getSubtotal() < 0) {
            throw new CreacionPedidoException("El subtotal no debe ser menor a 0.");
        }  
        return propuestaActual.getSubtotal();
    }
    
    /**
     * Metodo encargado de calcular el impuesto con el valor de 0.16 multiplicado
     * por el subtotal de la propuesta actual.
     * 
     * @return el valor del impuesto multiplicado por el subtotal de la 
     * propuesta actual.
     */
    private float calcularImpuesto(float subtotal) {
       if (subtotal < 0) {
            throw new CreacionPedidoException("El subtotal no debe ser menor a 0.");
        }  
        return subtotal * 0.16f;
    }
    
    /**
     * Metodo encargado de calcular el costo de envio a partir de la seleccion
     * de tipo de entrega del usuario. Por lo tanto, se definen las fechas al inicio
     * como null para despues de una modificacion actualizar las fechas.
     * 
     * @return el costo de envio segun la opcion seleccionada.
     */
    private float calcularCostoEnvio(String tipoEntrega) {
        float costoEnvio;
        fechaEntrega = null;
        fechaLimite = null;
        
        if("DOMICILIO".equals(tipoEntrega)) {
            fechaEntrega = calcularFechaEstimadaEntrega();
            fechaLimite = null;
            return costoEnvio = 100f;
        }
        fechaEntrega = calcularFechaEstimadaEntrega();
        fechaLimite = calcularFechaLimiteRecoger();
        return costoEnvio = 0f;
    }
    
    /**
     * Metodo encargado de calcular una fecha aproximada de entrega en tienda
     * con la diferencia de 5 dias a la fecha actual.
     * 
     * @return fecha de entrega aproximada.
     */
    public Date calcularFechaEstimadaEntrega() {
        GregorianCalendar fecha = new GregorianCalendar();
        fecha.add(GregorianCalendar.DAY_OF_MONTH, 5);
        fechaEntrega = fecha.getTime(); 
        return fechaEntrega;
    }
    
    /**
     * Metodo encargado de calcular una fecha aproximada de entrega a domicilio
     * con la diferencia de 10 dias a la fecha actual.
     * 
     * @return fecha de entrega aproximada a domicilio.
     */
    public Date calcularFechaLimiteRecoger() {
        GregorianCalendar fecha = new GregorianCalendar();
        fecha.add(GregorianCalendar.DAY_OF_MONTH, 10);
        fechaLimite = fecha.getTime();
        return fechaLimite;
    }
    /**
     * Metodo encargado de calcular la suma del subtotal, impuestos y el costo de
     * envio.
     * 
     * @return el total de la cotizacion generada con los productos cargados de
     * la propuesta y la seleccion del tipo de entrega del usuario.
     */
    private float calcularTotal(float subtotal, float impuesto, float costoEnvio) {
        if (subtotal < 0 || impuesto < 0 || costoEnvio < 0) {
            throw new CreacionPedidoException("Los importes no deben ser menores a 0.");
        }
        
        return subtotal + impuesto + costoEnvio;
    }
        
}
