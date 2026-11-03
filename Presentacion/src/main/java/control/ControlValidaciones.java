package control;


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

    public static Integer cambiarAEntero(String texto) {
        if(!validarTextoVacio(texto)) {
            return null;
        }
        
        try {
            return Integer.parseInt(texto.trim());          
        } catch (NumberFormatException e){
            return null;
        }
    }

}
