package Paneles;

import Dialogs.DialogTelefono;
import FramePrincipal.FramePrincipal;

import javax.swing.*;

public class Navegador {
    FramePrincipal framePrincipal;
    PanelMenuPricipal panelMenuPricipal;
    DialogTelefono dialogTelefono;

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

    public void mostrarDialogoBuscarCliente() {
        if(dialogTelefono == null){
            dialogTelefono = new DialogTelefono(framePrincipal, this);
        }
        dialogTelefono.setVisible(true);
    }
}
