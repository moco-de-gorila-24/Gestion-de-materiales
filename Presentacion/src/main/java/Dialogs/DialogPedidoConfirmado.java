/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dialogs;

import control.Navegador;
import Componentes.Button;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author cinca
 */
public class DialogPedidoConfirmado extends JDialog {
    private final Navegador navegador;

    public DialogPedidoConfirmado(JFrame owner, Navegador navegador) {
        super(owner, "Pedido confirmado", true);
        this.navegador = navegador;

        Color gris = new Color(119, 119, 119);
        Color textoOscuro = new Color(25, 25, 25);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel titulo = new JLabel("¡Pedido confirmado!");
        titulo.setFont(new Font("Arial", Font.PLAIN, 30));
        titulo.setForeground(textoOscuro);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(25, 20, 30, 20);
        add(titulo, gbc);

        JLabel lblEstado = new JLabel("Estado del pedido: Confirmado");
        lblEstado.setFont(new Font("Arial", Font.PLAIN, 20));
        lblEstado.setForeground(textoOscuro);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 20, 20, 20);
        add(lblEstado, gbc);

        JLabel lblReservas = new JLabel("Reservas: Confirmadas");
        lblReservas.setFont(new Font("Arial", Font.PLAIN, 20));
        lblReservas.setForeground(textoOscuro);

        gbc.gridy = 2;
        gbc.insets = new Insets(0, 20, 20, 20);
        add(lblReservas, gbc);

        JLabel lblTotal = new JLabel("Total a pagar: $0.00");
        lblTotal.setFont(new Font("Arial", Font.PLAIN, 20));
        lblTotal.setForeground(textoOscuro);

        gbc.gridy = 3;
        gbc.insets = new Insets(0, 20, 30, 20);
        add(lblTotal, gbc);

        Button btnVerOrdenes = new Button("Ver órdenes de entrega");
        btnVerOrdenes.setPreferredSize(new Dimension(230, 40));

        gbc.gridy = 4;
        gbc.insets = new Insets(0, 20, 25, 20);
        add(btnVerOrdenes, gbc);

        btnVerOrdenes.addActionListener(e -> {
            dispose();
            // Aquí se conectara con el Navegador para mostrar las órdenes de entrega.
        });

        setResizable(false);
        pack();
        setLocationRelativeTo(owner);
    }
}
