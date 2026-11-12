/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones;

/**
 *
 * @author cinca
 */
public class CreacionPedidoException extends RuntimeException {
    
    /**
    *
    * Constructor vacio
    * 
    */
    public CreacionPedidoException() {
        
    }
    
    /**
    * Constructor que inicializa la clase padre al valor de su parametro.
    * @param mensaje mensaje.
    */
    public CreacionPedidoException(String mensaje) {
        super(mensaje);
    }
}
