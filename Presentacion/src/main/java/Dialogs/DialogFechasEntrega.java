/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dialogs;

import Paneles.CotizacionSuministro;
import control.Navegador;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 *
 * @author cinca
 */
public class DialogFechasEntrega extends JDialog {
    
    private final Navegador navegador;
    
    private final Color AZUL    = Color.decode("#1B2430");
    private final Color NARANJA = Color.decode("#C2410C");
    private final Color BORDE   = Color.decode("#DFE3E9");
    private final Color GRIS    = Color.decode("#5B6675");

    public DialogFechasEntrega(JFrame owner, Navegador navegador) {
        super(owner, "Fechas", true);
        this.navegador = navegador;
        setUndecorated(true);
        
        String entrega = ";";
        String recoger = "-";
        String limite = "-";
        
        JPanel raiz = new JPanel(new BorderLayout());
        raiz.setBorder(new LineBorder(AZUL, 1));
        raiz.setBackground(Color.WHITE);

        JLabel titulo = new JLabel("Fechas de entrega y recolección");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 14f));
        JPanel enc = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 14));
        enc.setBackground(AZUL);
        enc.add(titulo);

        JPanel cuerpo = new JPanel();
        cuerpo.setLayout(new BoxLayout(cuerpo, BoxLayout.Y_AXIS));
        cuerpo.setBackground(Color.WHITE);
        cuerpo.setBorder(new EmptyBorder(18, 20, 18, 20));
        cuerpo.add(tarjeta("Domicilio", "Tus productos estarán listos el " + entrega, null));
        cuerpo.add(Box.createVerticalStrut(12));
        cuerpo.add(tarjeta("Recolección", "Puedes recoger a partir de: " + recoger,
                               "Fecha límite: " + limite));

        JButton btn = new JButton("Cerrar");
        btn.setBackground(NARANJA);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setPreferredSize(new Dimension(110, 40));
        btn.addActionListener(e -> dispose());
       JPanel pie = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 12));
        pie.setBackground(Color.WHITE);
        pie.add(btn);

        raiz.add(enc, BorderLayout.NORTH);
        raiz.add(cuerpo, BorderLayout.CENTER);
        raiz.add(pie, BorderLayout.SOUTH);
        setContentPane(raiz);
        setSize(440, 300);
        setLocationRelativeTo(owner);
    }

        private JPanel tarjeta(String etiqueta, String linea1, String linea2) {
            JPanel p = new JPanel();
            p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
            p.setBackground(Color.WHITE);
            p.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(BORDE, 1, true), new EmptyBorder(10, 14, 10, 14)));
            JLabel l0 = new JLabel(etiqueta);
            l0.setForeground(GRIS);
            l0.setFont(l0.getFont().deriveFont(11f));
            p.add(l0);
            p.add(new JLabel(linea1));
            if (linea2 != null) {
                JLabel l2 = new JLabel(linea2);
                l2.setForeground(GRIS);
                p.add(l2);
            }
            p.setAlignmentX(Component.LEFT_ALIGNMENT);
            return p;
        }

}
