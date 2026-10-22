package Paneles;

import control.Navegador;
import Componentes.Button;

import javax.swing.*;
import java.awt.*;

public class PanelRegistrarCliente extends JPanel {
    private final Navegador navegador;

    public PanelRegistrarCliente(Navegador navegador) {
        this.navegador = navegador;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();


        JLabel labelTitulo = new JLabel("Registrar Cliente", SwingConstants.CENTER);
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 45));

        JLabel labelNombres = new JLabel("Nombres:");
        labelNombres.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        add(labelNombres, gbc);

        JTextField fieldNombre = new JTextField(18);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        add(fieldNombre, gbc);

        JLabel labelApellidoPaterno = new JLabel("Apellido Paterno:");
        labelApellidoPaterno.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 10, 10, 10);
        add(labelApellidoPaterno, gbc);
        JTextField fieldApellidoPaterno = new JTextField(18);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 10, 10, 10);
        add(fieldApellidoPaterno, gbc);

        JLabel labelApellidoMaterno = new JLabel("Apellido Materno:");
        labelApellidoMaterno.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(10, 10, 10, 10);
        add(labelApellidoMaterno, gbc);
        JTextField fieldApellidoMaterno = new JTextField(18);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.insets = new Insets(10, 10, 10, 10);
        add(fieldApellidoMaterno, gbc);

        JLabel labelTelefono = new JLabel("Telefono:");
        labelTelefono.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(10, 10, 10, 10);
        add(labelTelefono, gbc);
        JTextField fieldTelefono = new JTextField(18);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.insets = new Insets(10, 10, 10, 10);
        add(fieldTelefono, gbc);

        Button buttonRegistrar = new Button("Registrar");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets = new Insets(10, 10, 10, 10);
        add(buttonRegistrar, gbc);

        Button buttonRegresar = new Button("Regresar");
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.insets = new Insets(10, 10, 10, 10);
        add(buttonRegresar, gbc);

        setVisible(true);
    }
}
