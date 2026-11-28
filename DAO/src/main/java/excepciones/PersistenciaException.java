/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones;

/**
 *
 * @author cinca
 */
public class PersistenciaException extends RuntimeException {
    
    /**
    *
    * Constructor vacio
    * 
    */
    public PersistenciaException() {
        
    }
    
    /**
    * Constructor que inicializa la clase padre al valor de su parametro.
    * @param mensaje mensaje.
    */
    public PersistenciaException(String mensaje) {
        super(mensaje);
    }    
}
