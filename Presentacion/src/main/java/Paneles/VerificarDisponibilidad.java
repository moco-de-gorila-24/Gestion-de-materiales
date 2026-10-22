/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Paneles;

import control.Navegador;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

/**
 *
 * @author jorge
 */
public class VerificarDisponibilidad extends JPanel {
    private final Navegador navegador;
    private Color grisFondo = new Color(115, 115, 115);
    private Color grisClaro = new Color(225, 225, 225);
    private Color negro = new Color(30, 30, 30);

    public VerificarDisponibilidad(Navegador navegador) {
       this.navegador = navegador;
        setLayout(new BorderLayout());
        crearPantalla();
    }

    private void crearPantalla() {
        JPanel principal = new JPanel(new BorderLayout(15, 20));
        principal.setBackground(grisFondo);
        principal.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        // Titulo de la pantalla
        JLabel titulo = new JLabel(
                "F3 - Verificar Disponibilidad",
                SwingConstants.CENTER
        );
        titulo.setFont(new Font("Arial", Font.BOLD, 30));
        titulo.setForeground(Color.WHITE);

        JLabel subtitulo = new JLabel(
                "Pantalla para verificar disponibilidad",
                SwingConstants.CENTER
        );
        subtitulo.setFont(new Font("Arial", Font.PLAIN, 18));
        subtitulo.setForeground(Color.WHITE);

        JPanel encabezado = new JPanel(new GridLayout(2, 1, 5, 5));
        encabezado.setOpaque(false);
        encabezado.add(titulo);
        encabezado.add(subtitulo);

        // Panel que contiene la tabla de materiales
        JPanel tabla = new JPanel(new GridLayout(3, 1, 0, 0));
        tabla.setBackground(Color.WHITE);
        tabla.setBorder(BorderFactory.createLineBorder(new Color(80, 80, 80), 2));

        // Encabezados de las columnas
        JPanel encabezadosTabla = new JPanel(new GridLayout(1, 8));
        encabezadosTabla.setBackground(Color.WHITE);

        String[] columnas = {
            "Materiales",
            "Cantidad solicitada",
            "Unidad",
            "Disponible",
            "Estado",
            "Sustitución",
            "Acción",
            "Eliminar"
        };

        for (int i = 0; i < columnas.length; i++) {
            JLabel etiqueta = new JLabel(columnas[i], SwingConstants.CENTER);
            etiqueta.setFont(new Font("Arial", Font.BOLD, 14));
            etiqueta.setBorder(BorderFactory.createLineBorder(new Color(210, 210, 210)));
            encabezadosTabla.add(etiqueta);
        }

        tabla.add(encabezadosTabla);

        // Primera fila: bloques
        JPanel filaBloques = crearFila("Bloque de construcción", "50", "Kg", "500", true, new String[]{"---"});

        // Segunda fila: cemento CEMEX
        JPanel filaCemento = crearFila("Cemento CEMEX", "10", "Kg", "9", false, new String[]{"Cemento PANAM"});

        tabla.add(filaBloques);
        tabla.add(filaCemento);

        // Panel central para mantener la tabla en la pantalla
        JPanel centro = new JPanel(new BorderLayout());
        centro.setBackground(grisFondo);
        
        JScrollPane scrollTabla = new JScrollPane(tabla);

        scrollTabla.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollTabla.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        centro.add(scrollTabla, BorderLayout.CENTER);

        // Botones inferiores
        JButton botonRegresar = crearBoton("Regresar", negro);
        JButton botonSiguiente = crearBoton("Siguiente", grisFondo);

        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.setOpaque(false);

        JPanel panelRegresar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelRegresar.setOpaque(false);
        panelRegresar.add(botonRegresar);

        JPanel panelSiguiente = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelSiguiente.setOpaque(false);
        panelSiguiente.add(botonSiguiente);

        panelInferior.add(panelRegresar, BorderLayout.WEST);
        panelInferior.add(panelSiguiente, BorderLayout.EAST);

        principal.add(encabezado, BorderLayout.NORTH);
        principal.add(centro, BorderLayout.CENTER);
        principal.add(panelInferior, BorderLayout.SOUTH);

        botonRegresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                navegador.mostrarPanelRegistrarPedido();
            }
        });
        
        botonSiguiente.addActionListener(e -> {
            navegador.mostrarOrdenEntrega();
        });
        
        add(principal, BorderLayout.CENTER);

    }

    private JPanel crearFila(
            String material,
            String cantidad,
            String unidad,
            String disponible,
            boolean disponibleCompleto,
            String[] sustitutos) {

        JPanel fila = new JPanel(new GridLayout(1, 8));
        fila.setBackground(Color.WHITE);

        // Nombre del material
        JLabel nombre = new JLabel(
                "<html><center>" + material + "</center></html>",
                SwingConstants.CENTER
        );
        nombre.setFont(new Font("Arial", Font.BOLD, 13));
        fila.add(nombre);

        // Cantidad solicitada
        JLabel cantidadLabel = new JLabel(cantidad,SwingConstants.CENTER);
        cantidadLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        fila.add(cantidadLabel);

        // Unidad de medida
        JLabel unidadLabel = new JLabel(unidad,SwingConstants.CENTER);
        unidadLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        fila.add(unidadLabel);

        // Cantidad disponible
        JLabel disponibleLabel = new JLabel(disponible,SwingConstants.CENTER);
        disponibleLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        fila.add(disponibleLabel);

        // Estado del material
        JLabel estado = new JLabel(disponibleCompleto ? "Disponible" : "Insuficiente",SwingConstants.CENTER);
        estado.setFont(new Font("Arial", Font.PLAIN, 13));

        if (disponibleCompleto) {
            estado.setForeground(new Color(35, 145, 65));
        } else {
            estado.setForeground(new Color(205, 45, 30));
        }

        fila.add(estado);

        // Selector de sustitución
        JComboBox<String> selector = new JComboBox<>(sustitutos);
        fila.add(selector);

        // Botón para sustituir
        JButton sustituir = crearBoton("Sustituir material",new Color(90, 90, 90));

        sustituir.setFont(new Font("Arial", Font.BOLD, 11));
        sustituir.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,"La sustitución se implementará posteriormente.");
        });

        fila.add(sustituir);

        // Botón para eliminar
        JButton eliminar = crearBoton("Eliminar", negro);
        eliminar.setFont(new Font("Arial", Font.BOLD, 12));

        eliminar.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,"La eliminación se implementará posteriormente.");
        });

        fila.add(eliminar);

        // Separación visual de las filas
        fila.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(210, 210, 210)));

        return fila;
    }

    private JButton crearBoton(String texto, Color fondo) {
        JButton boton = new JButton(texto);
        boton.setBackground(fondo);
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Arial", Font.BOLD, 16));
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        boton.setOpaque(true);

        return boton;
    }

}
