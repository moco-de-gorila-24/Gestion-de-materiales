package Paneles;

import FramePrincipal.FramePrincipal;

import javax.swing.*;

public class Navegador {
    FramePrincipal framePrincipal;
    PanelMenuPricipal panelMenuPricipal;

    public void cambiarPanel(JPanel panel) {
        framePrincipal.setContentPane(panel);
        framePrincipal.revalidate();
        framePrincipal.repaint();
    }

    public void iniciarSistema() {
        if (framePrincipal == null) {
            framePrincipal = new FramePrincipal(this);
        }
        framePrincipal.setVisible(true);
        mostrarPanelMenuPrincipal();
    }

    public void mostrarPanelMenuPrincipal() {
        if (panelMenuPricipal == null) {
            panelMenuPricipal = new PanelMenuPricipal(this);
        }
        cambiarPanel(panelMenuPricipal);
    }
}
