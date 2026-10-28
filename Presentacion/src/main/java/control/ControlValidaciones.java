package control;

import almacenDatos.Almacen;
import dtos.ClienteDTO;

public class ControlValidaciones {

    public static boolean validarTextoVacio(String texto){
        if(texto.trim().isEmpty() || texto.trim() == null){
            return false;
        }
        return true;
    }

    public static boolean validarTelefono(String telefono){
        if(!telefono.trim().matches("\\d{10}")){
            return false;
        }
        return true;
    }

    public static boolean validarSoloTexto(String texto){
        if(!texto.trim().matches("[\\p{L} ]+")){
            return false;
        }
        return true;
    }

    public static boolean clienteExiste(String telefono){
        Almacen almacen = new Almacen();
        ClienteDTO clienteDTO = almacen.obtenerCliente();

        if(telefono.trim() == clienteDTO.getTelefono()){
            return true;
        }
        return false;
    }
}
