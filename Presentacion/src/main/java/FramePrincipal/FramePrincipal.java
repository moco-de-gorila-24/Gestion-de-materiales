package FramePrincipal;

import control.Navegador;

import javax.swing.*;
import java.awt.*;

public class FramePrincipal extends JFrame {
    public final Navegador navegador;

    public FramePrincipal(Navegador navegador) {
        this.navegador = navegador;
        mostrar();
    }

    public void mostrar(){
        setSize(1080,720);
        setTitle("Pantalla de Eleccion");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


    }
}
