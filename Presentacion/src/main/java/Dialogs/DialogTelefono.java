package Dialogs;

import Paneles.Navegador;
import Componentes.Button;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DialogTelefono extends JDialog {
    private final Navegador navegador;
    private final JTextField campoTelefono = new JTextField(10);

    public DialogTelefono(JFrame owner, Navegador navegador) {
        super(owner, "Buscar cliente", true);   // true = modal
        this.navegador = navegador;

        Color gris = new Color(119, 119, 119);
        Color textoOscuro = new Color(25, 25, 25);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel titulo = new JLabel("Buscar cliente");
        titulo.setFont(new Font("Arial", Font.PLAIN, 30));
        titulo.setForeground(textoOscuro);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(25, 0, 40, 0);
        add(titulo, gbc);

        JLabel lblTelefono = new JLabel("Teléfono");
        lblTelefono.setFont(new Font("Arial", Font.PLAIN, 26));
        lblTelefono.setForeground(textoOscuro);
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(0, 30, 40, 15);
        add(lblTelefono, gbc);

        campoTelefono.setFont(new Font("Arial", Font.PLAIN, 20));
        campoTelefono.setBorder(BorderFactory.createEmptyBorder(6, 8, 6, 8));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 40, 30);
        add(campoTelefono, gbc);

        // Botones
        Button btnRegresar = new Button("Regresar");
        Button btnConfirmar = new Button("Confirmar");

        Dimension tam = new Dimension(150, 40);
        btnRegresar.setPreferredSize(tam);
        btnConfirmar.setPreferredSize(tam);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        panelBotones.setOpaque(false);
        panelBotones.add(btnRegresar);
        panelBotones.add(btnConfirmar);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 25, 0);
        add(panelBotones, gbc);


        btnRegresar.addActionListener(e -> dispose());
        btnConfirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                navegador.mostrarPanelRegistrarPedido();
            }
        });

        setResizable(false);
        pack();
        setLocationRelativeTo(owner);

    }
}