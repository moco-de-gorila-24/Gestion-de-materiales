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
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
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

        JLabel subtitulo = new JLabel("Revisa las existencias y sustituye o elimina materiales.", SwingConstants.CENTER);
        subtitulo.setFont(new Font("Arial", Font.PLAIN, 16));
        subtitulo.setForeground(Color.WHITE);

        JPanel encabezado = new JPanel(new GridLayout(2, 1, 5, 5));
        encabezado.setOpaque(false);
        encabezado.add(titulo);
        encabezado.add(subtitulo);

        // Cada material se muestra de manera vertical.
        filas.setLayout(new BoxLayout(filas, BoxLayout.Y_AXIS));
        filas.setBackground(Color.WHITE);
        filas.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        actualizarFilas();

        JScrollPane scrollTabla = new JScrollPane(filas);
        scrollTabla.setPreferredSize(new Dimension(950, 300));
        scrollTabla.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollTabla.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollTabla.getVerticalScrollBar().setUnitIncrement(16);

        // La tabla no sobrepasa el ancho disponible.
        JPanel centro = new JPanel(new BorderLayout());
        centro.setBackground(grisFondo);
        centro.add(scrollTabla, BorderLayout.CENTER);

        JButton botonRegresar = crearBoton("Regresar", negro);
        JButton botonSiguiente = crearBoton("Siguiente", new Color(55, 110, 65));

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

            for (int i = 0; i < detalles.size(); i++) {
                DetallePedidoDTO detalle = detalles.get(i);
                MaterialDTO material = detalle.getMaterial();

                if (material == null || detalle.getCantidad() > material.getCantidadDisponible()) {

                    String mensaje;

                    if (material == null) {
                        mensaje = "Hay un material sin seleccionar.";
                    } else {
                        mensaje = "No hay existencias suficientes de " + material.getNombre()
                                + " - " + material.getMarca()
                                + ".\nSolicitado: " + detalle.getCantidad()
                                + "\nDisponible: " + material.getCantidadDisponible();
                    }

                    JOptionPane.showMessageDialog(this, mensaje + "\nSustituye o elimina el material antes de continuar.");
                    return;
                }
            }

            navegador.mostrarSeleccionTipoEntrega();
        });
    }

    private void actualizarFilas() {
        filas.removeAll();
        filas.setLayout(new BoxLayout(filas, BoxLayout.Y_AXIS));

        List<DetallePedidoDTO> detalles = navegador.getDetallesCarrito();

        List<MaterialDTO> materialesAlmacen = Almacen.obtenerMateriales();

        if (detalles.isEmpty()) {
            JLabel vacio = new JLabel("No hay materiales en el carrito. Regresa y agrega materiales.", SwingConstants.CENTER);
            vacio.setBorder(BorderFactory.createEmptyBorder(25, 10, 25, 10));
            vacio.setAlignmentX(CENTER_ALIGNMENT);
            filas.add(vacio);
            filas.revalidate();
            filas.repaint();
            return;
        }

        for (int i = 0; i < detalles.size(); i++) {
            DetallePedidoDTO detalle = detalles.get(i);
            MaterialDTO material = detalle.getMaterial();

            if (material == null) {
                continue;
            }

            int disponible = material.getCantidadDisponible();
            boolean suficiente = detalle.getCantidad() <= disponible;

            // Tarjeta del material.
            JPanel tarjeta = new JPanel(new BorderLayout(8, 10));
            tarjeta.setBackground(Color.WHITE);
            tarjeta.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(160, 160, 160), 1), BorderFactory.createEmptyBorder(12, 12, 12, 12)));
            tarjeta.setAlignmentX(LEFT_ALIGNMENT);
            tarjeta.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));

            // Nombre y estado.
            JLabel nombre = new JLabel(material.getNombre() + " (" + material.getMarca() + ")");
            nombre.setFont(new Font("Arial", Font.BOLD, 16));

            JLabel estado = new JLabel(suficiente ? "Disponible" : "Insuficiente");
            estado.setFont(new Font("Arial", Font.BOLD, 13));
            estado.setForeground(suficiente ? new Color(25, 135, 55) : new Color(200, 40, 35));

            JPanel encabezadoTarjeta = new JPanel(new BorderLayout(10, 0));
            encabezadoTarjeta.setOpaque(false);
            encabezadoTarjeta.add(nombre, BorderLayout.CENTER);
            encabezadoTarjeta.add(estado, BorderLayout.EAST);

            JLabel informacion = new JLabel("Solicitada: " + detalle.getCantidad() + "   |   Unidad: " + material.getUnidadMedida() + "   |   Disponible: " + disponible);
            informacion.setFont(new Font("Arial", Font.PLAIN, 13));

            JPanel datos = new JPanel(new BorderLayout(0, 6));
            datos.setOpaque(false);
            datos.add(encabezadoTarjeta, BorderLayout.NORTH);
            datos.add(informacion, BorderLayout.CENTER);

            // Selector de materiales sustitutos.
            JComboBox<MaterialDTO> selector = new JComboBox<>();
            List<MaterialDTO> sustitutos = new java.util.ArrayList<>();

            for (int j = 0; j < materialesAlmacen.size(); j++) {
                MaterialDTO candidato = materialesAlmacen.get(j);

                boolean mismoTipo = candidato.getTipo() != null && material.getTipo() != null && candidato.getTipo().equalsIgnoreCase(material.getTipo());

                boolean diferente = candidato.getIdMaterial() != material.getIdMaterial();

                int cantidadNecesaria = calcularCantidadTotal(detalles, detalle, candidato);

                boolean stockSuficiente = candidato.getCantidadDisponible() >= cantidadNecesaria;

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

                etiqueta.setOpaque(true);
                etiqueta.setBackground(isSelected ? list.getSelectionBackground() : Color.WHITE);
                etiqueta.setForeground(isSelected ? list.getSelectionForeground() : Color.BLACK);

                return etiqueta;
            });

            selector.setEnabled(!sustitutos.isEmpty());
            selector.setPreferredSize(new Dimension(260, 32));

            JButton sustituir = crearBoton("Sustituir", new Color(30, 105, 180));
            sustituir.setEnabled(!sustitutos.isEmpty());

            JButton eliminar = crearBoton("Eliminar", new Color(180, 45, 45));

            // Controles debajo de la información.
            JPanel controles = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
            controles.setOpaque(false);
            controles.add(new JLabel("Sustituto:"));
            controles.add(selector);
            controles.add(sustituir);
            controles.add(eliminar);

            tarjeta.add(datos, BorderLayout.NORTH);
            tarjeta.add(controles, BorderLayout.SOUTH);

            filas.add(tarjeta);
            filas.add(Box.createVerticalStrut(10));

            sustituir.addActionListener(e -> {
                MaterialDTO nuevo = (MaterialDTO) selector.getSelectedItem();

                if (nuevo == null) {
                    JOptionPane.showMessageDialog(this, "Selecciona un material sustituto.");
                    return;
                }

                int cantidadNecesaria = calcularCantidadTotal(detalles, detalle, nuevo);

                if (nuevo.getCantidadDisponible() < cantidadNecesaria) {
                    JOptionPane.showMessageDialog(this,
                            "No hay existencias suficientes del sustituto."
                            + "\nCantidad necesaria: " + cantidadNecesaria
                            + "\nDisponible: " + nuevo.getCantidadDisponible());
                    return;
                }

                DetallePedidoDTO existente = null;

                for (int k = 0; k < detalles.size(); k++) {
                    DetallePedidoDTO otro = detalles.get(k);

                    if (otro != detalle && otro.getMaterial() != null && otro.getMaterial().getIdMaterial() == nuevo.getIdMaterial()) {
                        existente = otro;
                        break;
                    }
                }

                if (existente != null) {
                    int cantidadTotal = existente.getCantidad() + detalle.getCantidad();

                    existente.setCantidad(cantidadTotal);
                    existente.setImporte(cantidadTotal * existente.getPrecioUnitario());

                    detalles.remove(detalle);
                } else {
                    detalle.setMaterial(nuevo);
                    detalle.setPrecioUnitario(nuevo.getPrecioUnitario());
                    detalle.setImporte(nuevo.getPrecioUnitario() * detalle.getCantidad());
                }

                removeAll();
                crearPantalla();
                revalidate();
                repaint();
            });

            eliminar.addActionListener(e -> {
                int opcion = JOptionPane.showConfirmDialog(this,
                        "¿Deseas eliminar " + material.getNombre() 
                        + " (" + material.getMarca() + ") del carrito?",
                        "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

                if (opcion == JOptionPane.YES_OPTION) {
                    navegador.eliminarDelCarrito(detalle);

                    removeAll();
                    crearPantalla();
                    revalidate();
                    repaint();
                }
            });
        }

        filas.revalidate();
        filas.repaint();
    }

    private int calcularCantidadTotal(List<DetallePedidoDTO> detalles, DetallePedidoDTO detalleActual, MaterialDTO candidato) {

        int cantidadTotal = detalleActual.getCantidad();

        for (int i = 0; i < detalles.size(); i++) {
            DetallePedidoDTO otro = detalles.get(i);

            if (otro != detalleActual && otro.getMaterial() != null && otro.getMaterial().getIdMaterial() == candidato.getIdMaterial()) {
                cantidadTotal += otro.getCantidad();
            }
        }

        return cantidadTotal;
    }

    private JButton crearBoton(String texto, Color fondo) {
        JButton boton = new JButton(texto);

        boton.setBackground(fondo);
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Arial", Font.BOLD, 13));
        boton.setFocusPainted(false);
        boton.setOpaque(true);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Borde visible del botón.
        boton.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(fondo.darker(), 2), BorderFactory.createEmptyBorder(6, 10, 6, 10)));

        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (boton.isEnabled()) {
                    boton.setBackground(fondo.brighter());
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                boton.setBackground(fondo);
            }
        });

        return boton;
    }
}