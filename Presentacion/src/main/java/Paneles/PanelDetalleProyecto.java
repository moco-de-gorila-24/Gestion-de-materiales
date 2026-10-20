package Paneles;

import Componentes.Button;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelDetalleProyecto extends JPanel{
    private final Navegador navegador;
    private final Color gris = new Color(119, 119, 119);
    private final Color oscuro = new Color(34, 34, 34);

    // Componentes para llenarlos después
    private JLabel lblImagen = new JLabel();
    private JLabel lblNombre = new JLabel();
    private JLabel lblDescripcion = new JLabel();   // admite varias líneas con <html>...<br>...</html>
    private JLabel lblLongitud = new JLabel("Longitud (m)");
    private JTextField txtLongitud = new JTextField(8);
    private Button btnCancelar = new Button("Cancelar");
    private JButton btnAgregar = new Button("Agregar");

    public PanelDetalleProyecto(Navegador navegador) {
        this.navegador = navegador;
        setLayout(new BorderLayout(10, 10));
        setBackground(gris);
        setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        setPreferredSize(new Dimension(650, 300));   // útil si lo muestras dentro de un JDialog con pack()

        // Imagen (recuadro blanco)
        lblImagen.setOpaque(true);
        lblImagen.setBackground(Color.WHITE);
        lblImagen.setPreferredSize(new Dimension(80, 80));
        lblImagen.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel contImagen = new JPanel(new BorderLayout());
        contImagen.setOpaque(false);
        contImagen.add(lblImagen, BorderLayout.NORTH);

        // Texto: nombre + descripción
        lblNombre.setFont(new Font("Arial", Font.BOLD, 14));
        lblNombre.setForeground(Color.WHITE);
        lblDescripcion.setFont(new Font("Arial", Font.PLAIN, 13));
        lblDescripcion.setForeground(Color.WHITE);

        JPanel textos = new JPanel();
        textos.setLayout(new BoxLayout(textos, BoxLayout.Y_AXIS));
        textos.setOpaque(false);
        lblNombre.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblDescripcion.setAlignmentX(Component.LEFT_ALIGNMENT);
        textos.add(lblNombre);
        textos.add(lblDescripcion);

        // Longitud
        lblLongitud.setForeground(Color.WHITE);

        JPanel filaLongitud = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        filaLongitud.setOpaque(false);
        filaLongitud.add(lblLongitud);
        filaLongitud.add(txtLongitud);

        JPanel contLongitud = new JPanel(new BorderLayout());
        contLongitud.setOpaque(false);
        contLongitud.add(filaLongitud, BorderLayout.NORTH);

        // Caja oscura
        JPanel caja = new JPanel(new BorderLayout(15, 0));
        caja.setBackground(oscuro);
        caja.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        caja.add(contImagen, BorderLayout.WEST);
        caja.add(textos, BorderLayout.CENTER);
        caja.add(contLongitud, BorderLayout.EAST);

        // La caja se centra en el panel
        JPanel centro = new JPanel(new GridBagLayout());
        centro.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(0, 30, 0, 30);
        centro.add(caja, gbc);

        // Abajo: Cancelar / Agregar
        JPanel sur = new JPanel(new BorderLayout());
        sur.setOpaque(false);
        sur.add(btnCancelar, BorderLayout.WEST);
        sur.add(btnAgregar, BorderLayout.EAST);

        add(centro, BorderLayout.CENTER);
        add(sur, BorderLayout.SOUTH);

        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int opcion = JOptionPane.showConfirmDialog(null, "Desea cancelar el pedido?");

                if(opcion == 0){
                    navegador.mostrarPanelMenuPrincipal();
                }
            }
        });
    }
}
