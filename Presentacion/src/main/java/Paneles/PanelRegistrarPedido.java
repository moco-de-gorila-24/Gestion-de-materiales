package Paneles;


import Componentes.Button;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelRegistrarPedido extends JPanel {
    private final Navegador navegador;
    private final Color gris = new Color(119, 119, 119);
    private final Color oscuro = new Color(34, 34, 34);

    private JTextField txtBuscar = new JTextField();
    private Button btnBuscar = new Button("Buscar");
    private JComboBox<String> cmbFiltro = new JComboBox<>(new String[]{"filtrar"});
    private JPanel panelProductos = new JPanel();
    private JPanel panelCarrito = new JPanel();
    private JLabel lblSubtotal = new JLabel("$0.00");
    private Button btnCancelar = new Button("Cancelar");
    private Button btnSiguiente = new Button("Siguiente");

    public PanelRegistrarPedido(Navegador navegador) {
        this.navegador = navegador;
        setLayout(new BorderLayout(10, 10));
        setBackground(gris);
        setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        // Arriba: título + buscador
        JLabel titulo = new JLabel("Registrar Pedido", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));

        JPanel filaBuscar = new JPanel(new BorderLayout(10, 0));
        filaBuscar.setOpaque(false);
        filaBuscar.add(new JLabel("Buscar:"), BorderLayout.WEST);
        filaBuscar.add(txtBuscar, BorderLayout.CENTER);
        filaBuscar.add(btnBuscar, BorderLayout.EAST);

        JPanel norte = new JPanel(new BorderLayout(0, 10));
        norte.setOpaque(false);
        norte.add(titulo, BorderLayout.NORTH);
        norte.add(filaBuscar, BorderLayout.CENTER);

        // Centro izquierda: filtro + lista de productos
        panelProductos.setLayout(new BoxLayout(panelProductos, BoxLayout.Y_AXIS));
        panelProductos.setBackground(oscuro);

        JPanel contFiltro = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        contFiltro.setOpaque(false);
        contFiltro.add(cmbFiltro);

        JPanel izquierda = new JPanel(new BorderLayout(0, 10));
        izquierda.setOpaque(false);
        izquierda.add(contFiltro, BorderLayout.NORTH);
        izquierda.add(new JScrollPane(panelProductos), BorderLayout.CENTER);

        // Centro derecha: carrito + subtotal
        JLabel tituloCarrito = new JLabel("Carrito", SwingConstants.CENTER);
        tituloCarrito.setFont(new Font("Arial", Font.BOLD, 17));

        panelCarrito.setLayout(new BoxLayout(panelCarrito, BoxLayout.Y_AXIS));
        panelCarrito.setBackground(oscuro);

        JPanel cajaSubtotal = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 8));
        cajaSubtotal.setBackground(Color.WHITE);
        cajaSubtotal.add(new JLabel("subtotal:"));
        cajaSubtotal.add(lblSubtotal);

        JPanel pieCarrito = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        pieCarrito.setOpaque(false);
        pieCarrito.add(cajaSubtotal);

        JPanel derecha = new JPanel(new BorderLayout(0, 10));
        derecha.setOpaque(false);
        derecha.add(tituloCarrito, BorderLayout.NORTH);
        derecha.add(new JScrollPane(panelCarrito), BorderLayout.CENTER);
        derecha.add(pieCarrito, BorderLayout.SOUTH);

        JPanel centro = new JPanel(new GridLayout(1, 2, 20, 0));
        centro.setOpaque(false);
        centro.add(izquierda);
        centro.add(derecha);

        // Abajo: Cancelar / Siguiente
        JPanel sur = new JPanel(new BorderLayout());
        sur.setOpaque(false);
        sur.add(btnCancelar, BorderLayout.WEST);
        sur.add(btnSiguiente, BorderLayout.EAST);

        add(norte, BorderLayout.NORTH);
        add(centro, BorderLayout.CENTER);
        add(sur, BorderLayout.SOUTH);
        setVisible(true);

        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int opcion = JOptionPane.showConfirmDialog(null, "Desea cancelar el pedido?");

                if(opcion == 0){
                    navegador.mostrarPanelMenuPrincipal();
                }
            }
        });

        btnSiguiente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                navegador.mostrarVerificarDisponibilidad();
            }
        });
    }
}
