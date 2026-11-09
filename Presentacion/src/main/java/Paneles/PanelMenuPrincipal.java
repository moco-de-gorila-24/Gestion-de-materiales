package Paneles;

import control.Navegador;
import Componentes.Button;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelMenuPrincipal extends JPanel {
    private final Navegador navegador;

    public PanelMenuPrincipal(Navegador navegador){
        this.navegador = navegador;

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JPanel panelNorte = new JPanel(new BorderLayout());
        JPanel panelCentro = new JPanel(new BorderLayout());

        Button button = new Button("Registrar Pedido");
        button.setPreferredSize(new Dimension(200,45));
        
        Button botonReposicion = new Button("Gestionar Reposición");
        botonReposicion.setPreferredSize(new Dimension(200, 45));
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBotones.setOpaque(false);
        panelBotones.add(button);
        panelBotones.add(botonReposicion);

        JLabel labelTitulo = new JLabel("Menu Principal", SwingConstants.CENTER);
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 45));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 0, 30, 0);
        add(labelTitulo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;

        add(panelBotones, gbc);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                navegador.mostrarDialogoBuscarCliente();
            }
        });
        
        botonReposicion.addActionListener(e -> {
            navegador.mostrarPanelGestionReposicion();
        });
    }

}