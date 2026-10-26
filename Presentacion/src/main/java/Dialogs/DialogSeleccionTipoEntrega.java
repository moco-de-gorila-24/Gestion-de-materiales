/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dialogs;

import control.Navegador;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
/**
 *
 * @author cinca
 */
public class DialogSeleccionTipoEntrega extends JDialog {

    private static final Color AZUL    = Color.decode("#1B2430");
    private static final Color NARANJA = Color.decode("#C2410C");

    private final Navegador navegador;
    private String seleccion = null;   // "DOMICILIO", "TIENDA" o null si cierra la ventana

    public DialogSeleccionTipoEntrega(JFrame owner, Navegador navegador) {
        super(owner, "Tipo de entrega", true);
        this.navegador = navegador;

        JLabel titulo = new JLabel("Tipo de entrega", SwingConstants.CENTER);
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 18f));

        JButton btnDomicilio = crearBoton("A domicilio", NARANJA);
        JButton btnTienda    = crearBoton("Recoger en tienda", AZUL);

        btnDomicilio.addActionListener(e -> elegir("DOMICILIO"));
        btnTienda.addActionListener(e -> elegir("TIENDA"));

        JPanel botones = new JPanel(new GridLayout(1, 2, 14, 0));
        botones.setOpaque(false);
        botones.add(btnDomicilio);
        botones.add(btnTienda);

        JPanel contenido = new JPanel(new BorderLayout(0, 18));
        contenido.setBackground(Color.decode("#F4F5F7"));
        contenido.setBorder(new EmptyBorder(24, 24, 24, 24));
        contenido.add(titulo, BorderLayout.NORTH);
        contenido.add(botones, BorderLayout.CENTER);

        setContentPane(contenido);
        setSize(440, 200);
        setResizable(false);
        setLocationRelativeTo(owner);
    }

    private JButton crearBoton(String texto, Color fondo) {
        JButton b = new JButton(texto);
        b.setBackground(fondo);
        b.setForeground(Color.WHITE);
        b.setFont(b.getFont().deriveFont(Font.BOLD, 14f));
        b.setOpaque(true);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        return b;
    }

    private void elegir(String tipo) {
        seleccion = tipo;
        dispose();
        navegador.mostrarCotizacionSuministro();
    }

    public String getSeleccion() {
        return seleccion;
    }
}
