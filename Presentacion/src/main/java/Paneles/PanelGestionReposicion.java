/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Paneles;

import control.Navegador;
import dtos.DetalleReposicionDTO;
import dtos.SolicitudReposicionDTO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

/**
 *
 * @author Jorge
 */
public class PanelGestionReposicion extends JPanel {

    private final Navegador navegador;
    private final Color grisFondo = new Color(115, 115, 115);
    private final Color negro = new Color(30, 30, 30);
    private final JPanel filas = new JPanel();

    public PanelGestionReposicion(Navegador navegador) {
        this.navegador = navegador;
        setLayout(new BorderLayout());
        crearPantalla();
    }

    private void crearPantalla() {
        JPanel principal = new JPanel(new BorderLayout(15, 20));
        principal.setBackground(grisFondo);
        principal.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        JLabel titulo = new JLabel("Gestión de Reposición de Inventario", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        titulo.setForeground(Color.WHITE);

        JLabel subtitulo = new JLabel("Materiales que se encuentran por debajo de su stock mínimo.", SwingConstants.CENTER);
        subtitulo.setFont(new Font("Arial", Font.PLAIN, 15));
        subtitulo.setForeground(Color.WHITE);

        JPanel encabezado = new JPanel(new BorderLayout(5, 5));
        encabezado.setOpaque(false);
        encabezado.add(titulo, BorderLayout.NORTH);
        encabezado.add(subtitulo, BorderLayout.SOUTH);

        filas.setLayout(new BoxLayout(filas, BoxLayout.Y_AXIS));
        filas.setBackground(Color.WHITE);
        filas.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        actualizarMateriales();

        JScrollPane scroll = new JScrollPane(filas);
        scroll.setPreferredSize(new Dimension(950, 350));
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        JButton botonRegresar = crearBoton("Regresar", negro);
        JButton botonGenerar = crearBoton("Generar solicitud", new Color(55, 110, 65));
        JButton botonSolicitudes = crearBoton("Ver solicitudes", new Color(30, 105, 180));

        JPanel inferior = new JPanel(new BorderLayout());
        inferior.setOpaque(false);

        JPanel izquierda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        izquierda.setOpaque(false);
        izquierda.add(botonRegresar);

        JPanel derecha = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        derecha.setOpaque(false);
        derecha.add(botonSolicitudes);
        derecha.add(botonGenerar);

        inferior.add(izquierda, BorderLayout.WEST);
        inferior.add(derecha, BorderLayout.EAST);

        principal.add(encabezado, BorderLayout.NORTH);
        principal.add(scroll, BorderLayout.CENTER);
        principal.add(inferior, BorderLayout.SOUTH);

        add(principal, BorderLayout.CENTER);

        botonRegresar.addActionListener(e -> {
            navegador.mostrarPanelMenuPrincipal();
        });

        botonGenerar.addActionListener(e -> generarSolicitud());
        botonSolicitudes.addActionListener(e -> mostrarSolicitudes());
    }

    private void actualizarMateriales() {
        filas.removeAll();
        List<DetalleReposicionDTO> detalles = navegador.consultarMaterialesInsuficientes();

        if (detalles == null || detalles.isEmpty()) {
            JLabel vacio = new JLabel("No hay materiales que necesiten reposición.", SwingConstants.CENTER);
            vacio.setAlignmentX(CENTER_ALIGNMENT);
            vacio.setBorder(BorderFactory.createEmptyBorder(25, 10, 25, 10));
            filas.add(vacio);
        } else {
            for (DetalleReposicionDTO detalle : detalles) {

                String nombre = detalle.getMaterial().getNombre();
                String marca = detalle.getMaterial().getMarca();

                JPanel tarjeta = new JPanel(new BorderLayout(5, 5));
                tarjeta.setBackground(Color.WHITE);
                tarjeta.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(160, 160, 160)), BorderFactory.createEmptyBorder(12, 12, 12, 12)));
                tarjeta.setAlignmentX(LEFT_ALIGNMENT);
                tarjeta.setMaximumSize(new Dimension(Integer.MAX_VALUE, 110));

                JLabel nombreMaterial = new JLabel(nombre + " - " + marca);
                nombreMaterial.setFont(new Font("Arial", Font.BOLD, 16));

                JLabel informacion = new JLabel("Existencia actual: " + detalle.getCantidadActual() + " | Stock mínimo: " + detalle.getStockMinimo() + " | Cantidad necesaria: " + detalle.getCantidadSolicitada());

                tarjeta.add(nombreMaterial, BorderLayout.NORTH);
                tarjeta.add(informacion, BorderLayout.CENTER);

                filas.add(tarjeta);
                filas.add(Box.createVerticalStrut(10));
            }
        }

        filas.revalidate();
        filas.repaint();
    }

    private void generarSolicitud() {
        List<DetalleReposicionDTO> detalles = navegador.consultarMaterialesInsuficientes();

        if (detalles == null || detalles.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay materiales nuevos para solicitar. Los materiales que requieren reposición ya tienen una solicitud pendiente.");
            return;
        }

        int opcion = JOptionPane.showConfirmDialog(this, "Se generará una solicitud para " + detalles.size() + " material(es). ¿Deseas continuar?", "Confirmar solicitud", JOptionPane.YES_NO_OPTION);

        if (opcion != JOptionPane.YES_OPTION) {
            return;
        }

        SolicitudReposicionDTO solicitud = navegador.generarSolicitudReposicion();

        if (solicitud == null) {
            JOptionPane.showMessageDialog(this, "No fue posible generar la solicitud.");
            return;
        }

        JOptionPane.showMessageDialog(this, "Solicitud generada correctamente.\nNúmero de solicitud: " + solicitud.getIdSolicitud() + "\nEstado: " + solicitud.getEstado());
        actualizarMateriales();
    }

    private void mostrarSolicitudes() {
        List<SolicitudReposicionDTO> solicitudes = navegador.consultarSolicitudesReposicion();

        if (solicitudes == null || solicitudes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todavía no hay solicitudes de reposición.");
            return;
        }

        JTextArea area = new JTextArea(18, 55);
        area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.PLAIN, 12));

        for (SolicitudReposicionDTO solicitud : solicitudes) {

            area.append("SOLICITUD No. " + solicitud.getIdSolicitud() + "\n");
            area.append("Fecha: " + solicitud.getFechaSolicitud() + "\n");
            area.append("Estado: " + solicitud.getEstado() + "\n");

            for (DetalleReposicionDTO detalle : solicitud.getDetalles()) {
                area.append("  Material: " + detalle.getMaterial().getNombre() + " - " + detalle.getMaterial().getMarca() + "\n");
                area.append("  Cantidad solicitada: " + detalle.getCantidadSolicitada() + "\n");
            }

            area.append("----------------------------------------\n");
        }

        area.setCaretPosition(0);
        JOptionPane.showMessageDialog(this, new JScrollPane(area), "Solicitudes de reposición", JOptionPane.INFORMATION_MESSAGE);
    }

    private JButton crearBoton(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Arial", Font.BOLD, 13));
        boton.setFocusPainted(false);
        boton.setPreferredSize(new Dimension(165, 38));

        return boton;
    }
}
