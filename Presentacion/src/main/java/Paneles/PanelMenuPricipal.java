package Paneles;

import control.Navegador;
import Componentes.Button;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelMenuPricipal extends JPanel {
    private final Navegador navegador;

    public PanelMenuPricipal(Navegador navegador){
        this.navegador = navegador;

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JPanel panelNorte = new JPanel(new BorderLayout());
        JPanel panelCentro = new JPanel(new BorderLayout());

        Button button = new Button("Registrar Pedido");
        button.setPreferredSize(new Dimension(200,45));

        JLabel labelTitulo = new JLabel("Menu Principal", SwingConstants.CENTER);
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 45));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 0, 30, 0);
        add(labelTitulo, gbc);

        gbc.gridy = 1;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(0, 0, 0, 0);
        add(button, gbc);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                navegador.mostrarDialogoBuscarCliente();
            }
        });
    }

}