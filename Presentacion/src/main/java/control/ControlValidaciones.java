package control;

import almacenDatos.Almacen;
import dtos.ClienteDTO;

public class ControlValidaciones {

    public static boolean validarTextoVacio(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
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

    public static boolean clienteExiste(String telefono) {
        if (telefono == null) {
            return false;
        }

        return Almacen.buscarCliente(telefono) != null;
    }

    public static boolean agregarCliente(ClienteDTO cliente) {
        return Almacen.GuardarCliente(cliente);
    }
}
