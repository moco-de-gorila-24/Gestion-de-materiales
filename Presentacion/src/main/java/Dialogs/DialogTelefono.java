package Dialogs;

import control.ControlValidaciones;
import control.Navegador;
import Componentes.Button;
import dtos.ClienteDTO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class DialogTelefono extends JDialog {
    private final Navegador navegador;
    private ControlValidaciones controlValidaciones;
    private final JTextField campoTelefono = new JTextField(10);
    private final JLabel lblEstadoCliente = new JLabel("Ingrese un teléfono de 10 dígitos.");
    private final JLabel lblNombreCliente = new JLabel(" ");
    private final JLabel lblTelefonoCliente = new JLabel(" ");

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

        JPanel panelDatosCliente = new JPanel(new GridLayout(3, 1, 5, 5));
        panelDatosCliente.setBackground(Color.WHITE);
        panelDatosCliente.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(gris), BorderFactory.createEmptyBorder(10, 15, 10, 15)));

        lblEstadoCliente.setFont(new Font("Arial", Font.PLAIN, 14));
        lblNombreCliente.setFont(new Font("Arial", Font.BOLD, 16));
        lblTelefonoCliente.setFont(new Font("Arial", Font.PLAIN, 14));

        panelDatosCliente.add(lblEstadoCliente);
        panelDatosCliente.add(lblNombreCliente);
        panelDatosCliente.add(lblTelefonoCliente);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 30, 25, 30);

        add(panelDatosCliente, gbc);

        // Botones
        Button btnRegresar = new Button("Regresar");
        Button btnRegistrarCliente = new Button("Registrar");
        Button btnConfirmar = new Button("Confirmar");

        Dimension tam = new Dimension(130, 40);
        btnRegresar.setPreferredSize(tam);
        btnRegistrarCliente.setPreferredSize(tam);
        btnConfirmar.setPreferredSize(tam);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        panelBotones.setOpaque(false);
        panelBotones.add(btnRegresar);
        panelBotones.add(btnRegistrarCliente);
        panelBotones.add(btnConfirmar);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 25, 0);
        add(panelBotones, gbc);

        campoTelefono.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                actualizarDatosCliente();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                actualizarDatosCliente();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                actualizarDatosCliente();
            }
        });
        
        btnRegresar.addActionListener(e -> dispose());
        btnConfirmar.addActionListener(e -> {

            String telefono = campoTelefono.getText().trim();

            if (!navegador.validarTextoVacio(telefono)) {
                JOptionPane.showMessageDialog(this, "El campo no puede quedar vacio");
            } else if (!navegador.validarTelefono(telefono)) {
                JOptionPane.showMessageDialog(this, "El telefono debe ser un numero de 10 digitos");
            } else if (!navegador.clienteExiste(telefono)) {
                JOptionPane.showMessageDialog(this, "El numero proporcionado no existe");
            } else {
                navegador.seleccionarCliente(telefono);

                campoTelefono.setText("");
                dispose();

                navegador.mostrarPanelRegistrarPedido();
            }
        });


        btnRegistrarCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                navegador.mostrarPanelRegistrarCliente();
            }
        });

        setResizable(false);
        pack();
        setLocationRelativeTo(owner);

    }
    
    private void actualizarDatosCliente() {
        String telefono = campoTelefono.getText().trim();
        lblNombreCliente.setText(" ");
        lblTelefonoCliente.setText(" ");

        if (telefono.isEmpty()) {
            lblEstadoCliente.setText("Ingrese un teléfono de 10 dígitos.");
            return;
        }

        if (telefono.length() < 10) {
            lblEstadoCliente.setText("Continúe ingresando el teléfono...");
            return;
        }

        if (!navegador.validarTelefono(telefono)) {
            lblEstadoCliente.setText("El teléfono debe tener 10 dígitos.");
            return;
        }

        ClienteDTO cliente = navegador.buscarClientePorTelefono(telefono);

        if (cliente == null) {
            lblEstadoCliente.setText("No se encontró ningún cliente con ese teléfono.");
            return;
        }

        lblEstadoCliente.setText("Cliente encontrado:");
        lblNombreCliente.setText("Nombre: " + cliente.getNombre() + " " + cliente.getApellidoPa() + " " + cliente.getApellidoMa());
        lblTelefonoCliente.setText("Teléfono: " + cliente.getTelefono());
    }
    
    public void establecerTelefono(String telefono) {
        campoTelefono.setText(telefono);
    }
}