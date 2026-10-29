package Paneles;

import control.ControlValidaciones;
import control.Navegador;
import Componentes.Button;
import dtos.ClienteDTO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelRegistrarCliente extends JPanel {
    private final Navegador navegador;
    ControlValidaciones controlValidaciones;

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

        buttonRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!controlValidaciones.validarTextoVacio(fieldNombre.getText()) ||
                    !controlValidaciones.validarTextoVacio(fieldApellidoPaterno.getText()) ||
                    !controlValidaciones.validarTextoVacio(fieldApellidoMaterno.getText()) ||
                    !controlValidaciones.validarTextoVacio(fieldTelefono.getText())){
                    JOptionPane.showMessageDialog(null, "No pueden haber espacios vacios");
                }
                else if (!controlValidaciones.validarSoloTexto(fieldNombre.getText()) ||
                    !controlValidaciones.validarSoloTexto(fieldApellidoPaterno.getText()) ||
                    !controlValidaciones.validarSoloTexto(fieldApellidoMaterno.getText())){
                    JOptionPane.showMessageDialog(null, "Debe ingresar unicamente texto");
                }
                else if(!controlValidaciones.validarTelefono(fieldTelefono.getText())){
                    JOptionPane.showMessageDialog(null, "Debe ingresar un numero de 10 digitos");
                }
                else{
                    JOptionPane.showMessageDialog(null, "Cliente registrado con exito");
                    ClienteDTO clienteDTO = new ClienteDTO(fieldNombre.getText(), fieldApellidoPaterno.getText(), fieldApellidoMaterno.getText(), fieldTelefono.getText());
                    if(controlValidaciones.agregarCliente(clienteDTO)){
                        JOptionPane.showMessageDialog(null, "cliente agregado con exito");
                        navegador.mostrarPanelRegistrarPedido();
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "No se pudo agregar al cliente");
                    }
                }

            }
        });

        buttonRegresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                navegador.mostrarPanelMenuPrincipal();
            }
        });
        setVisible(true);
    }
}
