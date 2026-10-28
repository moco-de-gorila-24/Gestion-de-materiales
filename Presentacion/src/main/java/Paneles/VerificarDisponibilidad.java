/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Paneles;

import almacenDatos.Almacen;
import control.Navegador;
import dtos.DetallePedidoDTO;
import dtos.MaterialDTO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

/**
 *
 * @author jorge
 */
public class VerificarDisponibilidad extends JPanel {

    private final Navegador navegador;
    private final Color grisFondo = new Color(115, 115, 115);
    private final Color negro = new Color(30, 30, 30);
    private final JPanel filas = new JPanel();

    public VerificarDisponibilidad(Navegador navegador) {
        this.navegador = navegador;

        setLayout(new BorderLayout());
        crearPantalla();
    }

    private void crearPantalla() {
        JPanel principal = new JPanel(new BorderLayout(15, 20));
        principal.setBackground(grisFondo);
        principal.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        JLabel titulo = new JLabel("Verificar Disponibilidad", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 30));
        titulo.setForeground(Color.WHITE);

        JLabel subtitulo = new JLabel("Revisa las existencias y sustituye materiales si es necesario.", SwingConstants.CENTER);
        subtitulo.setFont(new Font("Arial", Font.PLAIN, 16));
        subtitulo.setForeground(Color.WHITE);

        JPanel encabezado = new JPanel(new GridLayout(2, 1, 5, 5));
        encabezado.setOpaque(false);
        encabezado.add(titulo);
        encabezado.add(subtitulo);

        JPanel tabla = new JPanel(new BorderLayout());
        tabla.setBackground(Color.WHITE);
        tabla.setBorder(
                BorderFactory.createLineBorder(new Color(80, 80, 80), 2)
        );

        JPanel encabezados = new JPanel(new GridLayout(1, 7));

        String[] columnas = {
            "Material",
            "Solicitada",
            "Unidad",
            "Disponible",
            "Estado",
            "Sustitución",
            "Acción"
        };

        for (int i = 0; i < columnas.length; i++) {
            JLabel etiqueta = new JLabel(columnas[i], SwingConstants.CENTER);
            etiqueta.setFont(new Font("Arial", Font.BOLD, 13));
            etiqueta.setBorder(BorderFactory.createLineBorder(new Color(210, 210, 210)));
            encabezados.add(etiqueta);
        }

        filas.setLayout(new GridLayout(0, 7));
        filas.setBackground(Color.WHITE);

        tabla.add(encabezados, BorderLayout.NORTH);
        tabla.add(filas, BorderLayout.CENTER);

        actualizarFilas();

        JScrollPane scrollTabla = new JScrollPane(tabla);

        // Limita el alto de la tabla.
        scrollTabla.setPreferredSize(new Dimension(1000, 180));

        // La tabla no se estira por toda la pantalla.
        JPanel centro = new JPanel(new BorderLayout());
        centro.setBackground(grisFondo);
        centro.add(scrollTabla, BorderLayout.NORTH);

        JButton botonRegresar = crearBoton("Regresar", negro);
        JButton botonSiguiente = crearBoton("Siguiente", grisFondo);

        JPanel inferior = new JPanel(new BorderLayout());
        inferior.setOpaque(false);

        JPanel izquierda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        izquierda.setOpaque(false);
        izquierda.add(botonRegresar);

        JPanel derecha = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        derecha.setOpaque(false);
        derecha.add(botonSiguiente);

        inferior.add(izquierda, BorderLayout.WEST);
        inferior.add(derecha, BorderLayout.EAST);

        principal.add(encabezado, BorderLayout.NORTH);
        principal.add(centro, BorderLayout.CENTER);
        principal.add(inferior, BorderLayout.SOUTH);

        add(principal, BorderLayout.CENTER);

        botonRegresar.addActionListener(e -> {
            navegador.mostrarPanelRegistrarPedido();
        });

        botonSiguiente.addActionListener(e -> {
            List<DetallePedidoDTO> detalles = navegador.getDetallesCarrito();

            if (detalles.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El carrito no contiene materiales.");
                return;
            }

            // Verifica todos los materiales antes de continuar.
            for (int i = 0; i < detalles.size(); i++) {
                DetallePedidoDTO detalle = detalles.get(i);
                MaterialDTO material = detalle.getMaterial();

                if (material == null || detalle.getCantidad() > material.getCantidadDisponible()) {

                    JOptionPane.showMessageDialog(this, "No hay existencias suficientes de "
                        + (material == null ? "uno de los materiales." : material.getNombre()
                        + " - " + material.getMarca()
                        + ". Solicitado: " + detalle.getCantidad()
                        + ", disponible: " + material.getCantidadDisponible())
                        + "\nSustituye el material antes de continuar."
                    );
                    return;
                }
            }

            navegador.mostrarSeleccionTipoEntrega();
        });
    }

    private void actualizarFilas() {
        filas.removeAll();

        List<DetallePedidoDTO> detalles = navegador.getDetallesCarrito();
        List<MaterialDTO> materialesAlmacen = Almacen.obtenerMateriales();

        if (detalles.isEmpty()) {
            filas.setLayout(new BorderLayout());

            JLabel vacio = new JLabel("No hay materiales en el carrito. Regresa y agrega materiales.", SwingConstants.CENTER);

            filas.add(vacio, BorderLayout.CENTER);
            filas.revalidate();
            filas.repaint();
            return;
        }

        filas.setLayout(new GridLayout(0, 7));

        for (int i = 0; i < detalles.size(); i++) {
            DetallePedidoDTO detalle = detalles.get(i);
            MaterialDTO material = detalle.getMaterial();

            int disponible = material.getCantidadDisponible();
            boolean suficiente = detalle.getCantidad() <= disponible;

            agregarCelda(filas, material.getNombre() + " (" + material.getMarca() + ")");
            agregarCelda(filas,String.valueOf(detalle.getCantidad()));
            agregarCelda(filas,material.getUnidadMedida());
            agregarCelda(filas,String.valueOf(disponible));

            JLabel estado = new JLabel(suficiente ? "Disponible" : "Insuficiente", SwingConstants.CENTER);

            estado.setForeground(suficiente ? new Color(35, 145, 65) : new Color(205, 45, 30));
            estado.setBorder(BorderFactory.createLineBorder(new Color(210, 210, 210)));

            filas.add(estado);

            JComboBox<MaterialDTO> selector = new JComboBox<>();

            List<MaterialDTO> sustitutos = new ArrayList<>();

            // Busca materiales del mismo tipo que cubran toda la cantidad solicitada.
            for (int j = 0; j < materialesAlmacen.size(); j++) {
                MaterialDTO candidato = materialesAlmacen.get(j);

                boolean mismoTipo = candidato.getTipo() != null && material.getTipo() != null && candidato.getTipo().equalsIgnoreCase(material.getTipo());
                boolean diferente = candidato.getIdMaterial() != material.getIdMaterial();
                boolean stockSuficiente = candidato.getCantidadDisponible() >= detalle.getCantidad();

                if (mismoTipo && diferente && stockSuficiente) {
                    sustitutos.add(candidato);
                    selector.addItem(candidato);
                }
            }

            selector.setRenderer((list, value, index, isSelected, cellHasFocus) -> {
                    JLabel etiqueta = new JLabel();

                    if (value != null) {
                        etiqueta.setText(value.getNombre() + " - " + value.getMarca() + " (stock: " + value.getCantidadDisponible() + ")");
                    }

                    if (isSelected) {
                        etiqueta.setOpaque(true);
                        etiqueta.setBackground(list.getSelectionBackground());
                        etiqueta.setForeground(list.getSelectionForeground());
                    }

                    return etiqueta;
                }
            );

            if (sustitutos.isEmpty()) {
                selector.addItem(null);
                selector.setEnabled(false);
            }

            selector.setBorder(BorderFactory.createLineBorder(new Color(210, 210, 210)));

            filas.add(selector);

            JButton sustituir = crearBoton("Sustituir", new Color(90, 90, 90));

            sustituir.setFont(new Font("Arial", Font.BOLD, 11));

            // Permite sustituir incluso si el material original
            // tiene suficiente existencia.
            sustituir.setEnabled(!sustitutos.isEmpty());

            sustituir.addActionListener(e -> {
                MaterialDTO nuevo = (MaterialDTO) selector.getSelectedItem();

                if (nuevo == null) {
                    JOptionPane.showMessageDialog(this, "Selecciona un material sustituto.");
                    return;
                }

                if (nuevo.getCantidadDisponible() < detalle.getCantidad()) {
                    JOptionPane.showMessageDialog(this, "El sustituto no tiene existencias suficientes.");
                    return;
                }

                detalle.setMaterial(nuevo);
                detalle.setPrecioUnitario(nuevo.getPrecioUnitario());
                detalle.setImporte(nuevo.getPrecioUnitario() * detalle.getCantidad());

                // Reconstruye el panel para mostrar el material sustituido.
                removeAll();
                crearPantalla();
                revalidate();
                repaint();
            });

            filas.add(sustituir);
        }

        filas.revalidate();
        filas.repaint();
    }

    private void agregarCelda(JPanel panel, String texto) {
        JLabel etiqueta = new JLabel(texto, SwingConstants.CENTER);
        etiqueta.setBorder(BorderFactory.createLineBorder(new Color(210, 210, 210)));
        panel.add(etiqueta);
    }

    private JButton crearBoton(String texto, Color fondo) {
        JButton boton = new JButton(texto);

        boton.setBackground(fondo);
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Arial", Font.BOLD, 14));
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12));
        boton.setOpaque(true);

        return boton;
    }
}