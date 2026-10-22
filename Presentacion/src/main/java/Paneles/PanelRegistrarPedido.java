package Paneles;


import control.Navegador;
import Componentes.Button;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Stack;

public class PanelRegistrarPedido extends JPanel {
    private final Navegador navegador;
    private final Color gris = new Color(119, 119, 119);
    private final Color oscuro = new Color(34, 34, 34);

    private JTable tablaCatalogo;
    private DefaultTableModel modeloCatalogo;
    private JTable tablaPropuesta;
    private DefaultTableModel modeloPropuesta;
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

        JPanel contFiltro = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        contFiltro.setOpaque(false);
        contFiltro.add(cmbFiltro);

        String[] columnas = {"Producto", "Descripcion", "Seleccionar"};

        modeloCatalogo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        modeloCatalogo.addRow(new Object[]{"cemento", "bulto de cemento marca cemex"});
        modeloCatalogo.addRow(new Object[]{"pega marmol", "saquito de pega marmol marca niasa"});
        modeloCatalogo.addRow(new Object[]{"tambo de electricidad", "tambo de electricidad electrica"});
        tablaCatalogo = new JTable(modeloCatalogo);
        tablaCatalogo.setRowHeight(60);

        String[] columnasPropuesta = {"Producto", "Cantidad", "Eliminar"};

        modeloPropuesta = new DefaultTableModel(columnasPropuesta, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        tablaPropuesta = new JTable(modeloPropuesta);
        tablaPropuesta.setRowHeight(60);
        //Nota: podria hacer para que cada que seleccione un producto se agregue de uno en uno pero no es como quisiera

        modeloPropuesta.addRow(new Object[]{"pedro","",""});

        JPanel izquierda = new JPanel(new BorderLayout(0, 10));
        izquierda.setOpaque(false);
        izquierda.add(contFiltro, BorderLayout.NORTH);
        izquierda.add(new JScrollPane(tablaCatalogo), BorderLayout.CENTER);

        // Centro derecha: carrito + subtotal
        JLabel tituloCarrito = new JLabel("Carrito", SwingConstants.CENTER);
        tituloCarrito.setFont(new Font("Arial", Font.BOLD, 17));

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
        derecha.add(new JScrollPane(tablaPropuesta), BorderLayout.CENTER);
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

        tablaCatalogo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = tablaCatalogo.rowAtPoint(e.getPoint());
                int columna = tablaCatalogo.columnAtPoint(e.getPoint());

                if (fila != -1 && columna == 3) { // solo la columna 2
                    Object valor = tablaCatalogo.getValueAt(fila, columna);
                    System.out.println("Clic en columna 2, valor: " + valor + "columna" + columna);
                }
            }
        });
    }
}
