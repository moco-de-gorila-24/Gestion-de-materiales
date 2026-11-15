/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Paneles;

import control.Navegador;
import dtos.DetalleReposicionDTO;
import dtos.MaterialDTO;
import dtos.SolicitudReposicionDTO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
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
    private JComboBox<String> filtroCategoria;
    
    private List<DetalleReposicionDTO> materiales = new ArrayList<>();

    private final Map<Integer, JTextField> camposCantidad = new HashMap<>();
    private final Map<Integer, String> cantidadesIngresadas = new HashMap<>();
    private final Set<Integer> materialesPendientes = new HashSet<>();

    public PanelGestionReposicion(Navegador navegador) {
        this.navegador = navegador;
        setLayout(new BorderLayout());
        crearPantalla();
    }

    private void crearPantalla() {
        JPanel principal = new JPanel(new BorderLayout(15, 15));
        principal.setBackground(grisFondo);
        principal.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        JLabel titulo = new JLabel("Gestión de Reposición de Inventario", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        titulo.setForeground(Color.WHITE);

        JLabel subtitulo = new JLabel("Consulta los materiales y selecciona las cantidades que deseas reponer.", SwingConstants.CENTER);
        subtitulo.setFont(new Font("Arial", Font.PLAIN, 15));
        subtitulo.setForeground(Color.WHITE);

        JPanel encabezado = new JPanel(new BorderLayout(5, 5));
        encabezado.setOpaque(false);
        encabezado.add(titulo, BorderLayout.NORTH);
        encabezado.add(subtitulo, BorderLayout.SOUTH);

        filtroCategoria = new JComboBox<>(new String[]{"Todas", "Cemento", "Block", "Varilla", "Otro"});
        filtroCategoria.setPreferredSize(new Dimension(180, 30));
        filtroCategoria.addActionListener(e -> actualizarMateriales());

        JPanel panelFiltro = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelFiltro.setOpaque(false);

        JLabel etiquetaFiltro = new JLabel("Filtrar por categoría:");
        etiquetaFiltro.setFont(new Font("Arial", Font.BOLD, 14));
        etiquetaFiltro.setForeground(Color.WHITE);

        panelFiltro.add(etiquetaFiltro);
        panelFiltro.add(filtroCategoria);

        JPanel panelSuperior = new JPanel(new BorderLayout(5, 12));
        panelSuperior.setOpaque(false);

        panelSuperior.add(encabezado, BorderLayout.NORTH);
        panelSuperior.add(panelFiltro, BorderLayout.SOUTH);

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

        principal.add(panelSuperior, BorderLayout.NORTH);
        principal.add(scroll, BorderLayout.CENTER);
        principal.add(inferior, BorderLayout.SOUTH);

        add(principal, BorderLayout.CENTER);

        botonRegresar.addActionListener(e -> {
            navegador.mostrarPanelMenuPrincipal();
        });

        botonGenerar.addActionListener(e -> generarSolicitud());
        botonSolicitudes.addActionListener(e -> mostrarSolicitudes());
    }

    /**
     * Guarda las cantidades que el usuario escribió antes de actualizar o cambiar el filtro.
     */
    private void guardarCantidadesIngresadas() {
        for (Map.Entry<Integer, JTextField> entrada : camposCantidad.entrySet()) {
            cantidadesIngresadas.put(entrada.getKey(), entrada.getValue().getText().trim());
        }
    }

    /**
     * Consulta todos los materiales y actualiza la pantalla.
     */
    private void actualizarMateriales() {
        guardarCantidadesIngresadas();
        filas.removeAll();
        camposCantidad.clear();
        materiales = navegador.consultarTodosLosMateriales();
        cargarMaterialesPendientes();

        if (materiales == null || materiales.isEmpty()) {
            JLabel vacio = new JLabel("No hay materiales registrados en el inventario.", SwingConstants.CENTER);
            vacio.setAlignmentX(CENTER_ALIGNMENT);
            filas.add(vacio);
        } else {
            String categoriaSeleccionada = (String) filtroCategoria.getSelectedItem();
            int cantidadMostrada = 0;
            for (DetalleReposicionDTO detalle : materiales) {
                if (detalle == null || detalle.getMaterial() == null) {
                    continue;
                }

                MaterialDTO material = detalle.getMaterial();
                String categoria = material.getTipo();

                if (categoria == null || categoria.trim().isEmpty()) {
                    categoria = "Otro";
                }

                if (!"Todas".equals(categoriaSeleccionada) && !categoria.equalsIgnoreCase(categoriaSeleccionada)) {
                    continue;
                }

                crearTarjeta(detalle, categoria);
                cantidadMostrada++;
            }

            if (cantidadMostrada == 0) {
                JLabel vacio = new JLabel("No hay materiales en esta categoría.", SwingConstants.CENTER);
                vacio.setAlignmentX(CENTER_ALIGNMENT);
                filas.add(vacio);
            }
        }

        filas.revalidate();
        filas.repaint();
    }

    /**
     * Consulta cuáles materiales ya tienen solicitudes pendientes.
     */
    private void cargarMaterialesPendientes() {
        materialesPendientes.clear();
        List<SolicitudReposicionDTO> solicitudes = navegador.consultarSolicitudesReposicion();

        if (solicitudes == null) {
            return;
        }

        for (SolicitudReposicionDTO solicitud : solicitudes) {
            if (solicitud == null || !"PENDIENTE".equalsIgnoreCase(solicitud.getEstado()) || solicitud.getDetalles() == null) {
                continue;
            }

            for (DetalleReposicionDTO detalle : solicitud.getDetalles()) {
                if (detalle != null && detalle.getMaterial() != null) {
                    materialesPendientes.add(detalle.getMaterial().getIdMaterial());
                }
            }
        }
    }

    /**
     * Construye la tarjeta de un material.
     */
    private void crearTarjeta(DetalleReposicionDTO detalle, String categoria) {
        MaterialDTO material = detalle.getMaterial();
        int idMaterial = material.getIdMaterial();
        boolean pendiente = materialesPendientes.contains(idMaterial);
        
        JPanel tarjeta = new JPanel(new BorderLayout(10, 5));
        tarjeta.setBackground(Color.WHITE);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(160, 160, 160)), BorderFactory.createEmptyBorder(12, 12, 12, 12)));
        tarjeta.setAlignmentX(LEFT_ALIGNMENT);
        tarjeta.setMaximumSize(new Dimension(Integer.MAX_VALUE, 115));

        JLabel nombreMaterial = new JLabel(material.getNombre() + " - " + material.getMarca());
        nombreMaterial.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel informacion = new JLabel("Categoría: " + categoria + " | Existencia actual: " + detalle.getCantidadActual() + " | Stock mínimo: " + detalle.getStockMinimo());
        informacion.setFont(new Font("Arial", Font.PLAIN, 13));

        JPanel informacionMaterial = new JPanel(new GridLayout(2, 1, 3, 5));
        informacionMaterial.setOpaque(false);
        informacionMaterial.add(nombreMaterial);
        informacionMaterial.add(informacion);
        tarjeta.add(informacionMaterial, BorderLayout.CENTER);

        JPanel panelCantidad = new JPanel(new GridLayout(2, 1, 3, 5));
        panelCantidad.setOpaque(false);
        JLabel etiquetaCantidad = new JLabel("Cantidad a reponer:");
        JTextField campoCantidad = new JTextField(7);
        campoCantidad.setHorizontalAlignment(JTextField.CENTER);
        String cantidadGuardada = cantidadesIngresadas.get(idMaterial);

        if (cantidadGuardada != null) {
            campoCantidad.setText(cantidadGuardada);
        }

        if (pendiente) {
            campoCantidad.setEnabled(false);
            campoCantidad.setToolTipText("Este material ya tiene una solicitud pendiente.");

            JLabel estado = new JLabel("Solicitud pendiente");
            estado.setForeground(new Color(180, 100, 0));
            estado.setFont(new Font("Arial", Font.BOLD, 12));
            panelCantidad.add(estado);
        } else {
            panelCantidad.add(etiquetaCantidad);
        }

        panelCantidad.add(campoCantidad);
        camposCantidad.put(idMaterial, campoCantidad);
        tarjeta.add(panelCantidad, BorderLayout.EAST);

        filas.add(tarjeta);
        filas.add(Box.createVerticalStrut(10));
    }

    /**
     * Genera una solicitud con las cantidades ingresadas.
     */
    private void generarSolicitud() {
        guardarCantidadesIngresadas();
        List<DetalleReposicionDTO> detallesSeleccionados = new ArrayList<>();

        for (DetalleReposicionDTO detalle : materiales) {
            if (detalle == null || detalle.getMaterial() == null) {
                continue;
            }

            MaterialDTO material = detalle.getMaterial();
            int idMaterial = material.getIdMaterial();

            if (materialesPendientes.contains(idMaterial)) {
                continue;
            }

            String textoCantidad = cantidadesIngresadas.get(idMaterial);

            if (textoCantidad == null || textoCantidad.trim().isEmpty()) {
                continue;
            }

            int cantidad;

            try {
                cantidad = Integer.parseInt(textoCantidad.trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "La cantidad del material " + material.getNombre() + " debe ser un número entero.");
                return;
            }

            if (cantidad <= 0) {
                JOptionPane.showMessageDialog(this, "La cantidad de " + material.getNombre() + " debe ser mayor que cero.");
                return;
            }

            DetalleReposicionDTO nuevoDetalle = new DetalleReposicionDTO(material, detalle.getCantidadActual(), detalle.getStockMinimo(), cantidad);
            detallesSeleccionados.add(nuevoDetalle);
        }

        if (detallesSeleccionados.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingresa una cantidad mayor que cero en al menos un material disponible.");
            return;
        }

        int opcion = JOptionPane.showConfirmDialog(this, "Se generará una solicitud para " + detallesSeleccionados.size() + " material(es). ¿Deseas continuar?", "Confirmar solicitud", JOptionPane.YES_NO_OPTION);

        if (opcion != JOptionPane.YES_OPTION) {
            return;
        }

        SolicitudReposicionDTO solicitud = navegador.generarSolicitudReposicion(detallesSeleccionados);

        if (solicitud == null) {
            JOptionPane.showMessageDialog(this, "No fue posible generar la solicitud. Verifica que los materiales no tengan otra solicitud pendiente.");
            return;
        }

        JOptionPane.showMessageDialog(this, "Solicitud generada correctamente.\nNúmero de solicitud: " + solicitud.getIdSolicitud() + "\nEstado: " + solicitud.getEstado());
        cantidadesIngresadas.clear();
        actualizarMateriales();
    }

    /**
     * Muestra las solicitudes registradas.
     */
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