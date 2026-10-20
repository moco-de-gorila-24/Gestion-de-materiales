package Paneles;

import Dialogs.DialogPedidoConfirmado;
import Dialogs.DialogRegistroDireccion;
import Dialogs.DialogTelefono;
import FramePrincipal.FramePrincipal;
import Paneles.ordenEntrega;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Navegador {
    FramePrincipal framePrincipal;
    PanelMenuPricipal panelMenuPricipal;
    PanelRegistrarCliente panelRegistrarCliente;
    DialogTelefono dialogTelefono;
    ordenEntrega ordenEntrega;
    DialogPedidoConfirmado pedidoConfirmado;
    PanelRegistrarPedido panelRegistrarPedido;
    PanelDetalleProyecto panelDetalleProyecto;

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

    public void mostrarPanelRegistrarCliente(){
        if(panelRegistrarCliente == null){
            panelRegistrarCliente = new PanelRegistrarCliente(this);
        }
        cambiarPanel(panelRegistrarCliente);
    }

    public void mostrarDialogoBuscarCliente() {
        if(dialogTelefono == null){
            dialogTelefono = new DialogTelefono(framePrincipal, this);
        }
        dialogTelefono.setVisible(true);
    }

    public String mostrarFormularioDireccion() {
        DialogRegistroDireccion dialog = new DialogRegistroDireccion(framePrincipal, this);
        dialog.setVisible(true);
        return dialog.getSeleccionada();
    }
    
    public void mostrarOrdenEntrega() {
        if(ordenEntrega == null) {
            ordenEntrega = new ordenEntrega(this);
        }
        cambiarPanel(ordenEntrega);
    }
    
    public void mostrarDialogoPedidoConfirmado() {
        if(pedidoConfirmado == null) {
            pedidoConfirmado = new DialogPedidoConfirmado(framePrincipal, this);
        }   
        pedidoConfirmado.setVisible(true);    
    }

    public void mostrarPanelRegistrarPedido(){
        if(panelRegistrarPedido == null){
            panelRegistrarPedido = new PanelRegistrarPedido(this);
        }
        cambiarPanel(panelRegistrarPedido);
    }

    public void mostrarPanelDetalleProyecto() {
        if(panelDetalleProyecto == null){
            panelDetalleProyecto = new PanelDetalleProyecto(this);
        }
        cambiarPanel(panelDetalleProyecto);
    }
}
