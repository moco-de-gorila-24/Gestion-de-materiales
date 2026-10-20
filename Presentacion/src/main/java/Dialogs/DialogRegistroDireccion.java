/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dialogs;

import javax.swing.JDialog;
import Componentes.Button;
import Paneles.Navegador;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cinca
 * 
 * Dialogo para consultar y registrar direcciones
 */
public class DialogRegistroDireccion extends JDialog {
    private static final String VISTA_LISTA = "lista";
    private static final String VISTA_NUEVA = "nueva";

    private final Navegador navegador;
    private final CardLayout cards = new CardLayout();
    private final JPanel contenedor = new JPanel(cards);

    private final JPanel panelLista = new JPanel();
    private final List<String> direcciones = new ArrayList<>(); // temporal
    private String seleccionada = null;

    private final JTextField campoCalle = new JTextField(15);
    private final JTextField campoColonia = new JTextField(15);
    private final JTextField campoNumero = new JTextField(15);
    private final JTextField campoCodigoPostal = new JTextField(15);

    public DialogRegistroDireccion(JFrame owner, Navegador navegador) {
        super(owner, "Direcciones", true);
        this.navegador = navegador;

        contenedor.add(crearVistaLista(), VISTA_LISTA);
        contenedor.add(crearVistaNueva(), VISTA_NUEVA);
        add(contenedor);

        setPreferredSize(new Dimension(720, 520));
        setResizable(false);
        pack();
        setLocationRelativeTo(owner);

        refrescarLista();
        cards.show(contenedor, VISTA_LISTA);
    }

    // dIRECCIONES REGISTRADAS
    private JPanel crearVistaLista() {
         JPanel vista = new JPanel(new GridBagLayout());
         GridBagConstraints gbc = new GridBagConstraints();
         Color textoOscuro = new Color(25, 25, 25);

         JLabel titulo = new JLabel("Direcciones registradas");
         titulo.setFont(new Font("Arial", Font.BOLD, 30));
         titulo.setForeground(textoOscuro);
         gbc.gridx = 0;
         gbc.gridy = 0;
         gbc.weightx = 1.0;
         gbc.insets = new Insets(25, 0, 15, 0);
         vista.add(titulo, gbc);

         Button btnAgregar = new Button("+ agregar dirección nueva");
         btnAgregar.setPreferredSize(new Dimension(230, 25));
         JPanel panelAgregar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
         panelAgregar.setOpaque(false);
         panelAgregar.add(btnAgregar);
         gbc.gridy = 1;
         gbc.fill = GridBagConstraints.HORIZONTAL;
         gbc.insets = new Insets(0, 30, 10, 30);
         vista.add(panelAgregar, gbc);

         panelLista.setLayout(new BoxLayout(panelLista, BoxLayout.Y_AXIS));
         JScrollPane scroll = new JScrollPane(panelLista);
         scroll.setPreferredSize(new Dimension(650, 220));
         gbc.gridy = 2;
         gbc.weighty = 1.0;
         gbc.fill = GridBagConstraints.BOTH;
         gbc.insets = new Insets(0, 30, 20, 30);
         vista.add(scroll, gbc);

         Button btnRegresar = new Button("Regresar");
         btnRegresar.setPreferredSize(new Dimension(150, 40));
         gbc.gridy = 3;
         gbc.weighty = 0;
         gbc.fill = GridBagConstraints.NONE;
         gbc.anchor = GridBagConstraints.WEST;
         gbc.insets = new Insets(0, 30, 25, 0);
         vista.add(btnRegresar, gbc);

         btnRegresar.addActionListener(e -> dispose());
         btnAgregar.addActionListener(e -> {
             limpiarCampos();
             cards.show(contenedor, VISTA_NUEVA);
         });
         return vista;
    }

    // AGREGAR NUEVA DIRECCION
    private JPanel crearVistaNueva() {
        JPanel vista = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        Color textoOscuro = new Color(25, 25, 25);

        JLabel titulo = new JLabel("Agregar nueva dirección");
        titulo.setFont(new Font("Arial", Font.PLAIN, 30));
        titulo.setForeground(textoOscuro);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(25, 0, 30, 0);
        vista.add(titulo, gbc);

        agregarFila(vista, "Calle", campoCalle, 1, gbc, textoOscuro);
        agregarFila(vista, "Colonia", campoColonia, 2, gbc, textoOscuro);
        agregarFila(vista, "Número", campoNumero, 3, gbc, textoOscuro);
        agregarFila(vista, "Código postal", campoCodigoPostal, 4, gbc, textoOscuro);

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
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 0, 25, 0);
        vista.add(panelBotones, gbc);

        // vuelve a la lista solo con lo guardado hasta el momento
        btnRegresar.addActionListener(e -> cards.show(contenedor, VISTA_LISTA));
        // Cguarda y vuelve a la lista con la nueva dirección
        btnConfirmar.addActionListener(e -> confirmar());
        return vista;
    }

    private void agregarFila(JPanel vista, String texto, JTextField campo, int fila, GridBagConstraints gbc, Color color) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("Arial", Font.PLAIN, 26));
        lbl.setForeground(color);
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(0, 30, 20, 15);
        vista.add(lbl, gbc);

        campo.setFont(new Font("Arial", Font.PLAIN, 20));
        campo.setBorder(BorderFactory.createEmptyBorder(6, 8, 6, 8));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 20, 30);
        vista.add(campo, gbc);
    }

    // cambiar pantalla
    private void confirmar() {
        // por mientras
        direcciones.add(campoCalle.getText().trim() + ", " + campoNumero.getText().trim() + ", "
                + campoColonia.getText().trim() + ", "
                + campoCodigoPostal.getText().trim());
        refrescarLista();
        cards.show(contenedor, VISTA_LISTA);
    }

    private void limpiarCampos() {
        campoCalle.setText("");
        campoColonia.setText("");
        campoNumero.setText("");
        campoCodigoPostal.setText("");
    }

    private void refrescarLista() {
        panelLista.removeAll();
        for (String d : direcciones) {
            panelLista.add(crearFila(d));
            panelLista.add(Box.createVerticalStrut(8));
        }
        panelLista.revalidate();
        panelLista.repaint();
    }

    private JPanel crearFila(String direccion) {
        JPanel fila = new JPanel(new BorderLayout(10, 0));
        fila.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(119, 119, 119)),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        JLabel lbl = new JLabel("<html><b>Dirección:</b> " + direccion + "</html>");
        lbl.setFont(new Font("Arial", Font.PLAIN, 16));

        Button btnSeleccionar = new Button("Seleccionar");
        btnSeleccionar.setPreferredSize(new Dimension(120, 30));
        btnSeleccionar.addActionListener(e -> {
            seleccionada = direccion;
            dispose();
        });

        fila.add(lbl, BorderLayout.CENTER);
        fila.add(btnSeleccionar, BorderLayout.EAST);
        return fila;
    }

    public String getSeleccionada() {
        return seleccionada;
    }
}