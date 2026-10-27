package control;

import javax.swing.*;

public class ControlValidacion {

    public ControlValidacion(){

    }

    public static boolean validarEspacioVacio(JTextField textField){
        if(textField.getText().isEmpty() || textField.getText().equals(null)){
            return false;
        }
        return true;
    }

    public static boolean validarTelefono(String telefono){
        if(!telefono.matches("^\\d{10}$")){
            return false;
        }
        return true;
    }

    public static boolean validarTexto(String texto){
        if(!texto.matches("^[a-zA-Z]+$")){
            return false;
        }
        return true;
    }



    
}
