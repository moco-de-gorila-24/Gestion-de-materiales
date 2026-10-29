package control;

import Dialogs.*;
import FramePrincipal.FramePrincipal;
import Paneles.*;
import almacenDatos.Almacen;
import dtos.DetallePedidoDTO;
import dtos.MaterialDTO;
import utilerias.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import almacenDatos.Almacen;
import dtos.DetallePropuestaDTO;
import dtos.PropuestaMaterialesDTO;

public class Navegador {
    FramePrincipal framePrincipal;
    PanelMenuPricipal panelMenuPricipal;
    PanelRegistrarCliente panelRegistrarCliente;
    DialogRegistroDireccion dialogRegistroDireccion;
    DialogTelefono dialogTelefono;
    ordenEntrega ordenEntrega;
    DialogPedidoConfirmado pedidoConfirmado;
    PanelRegistrarPedido panelRegistrarPedido;
    PanelDetalleProyecto panelDetalleProyecto;
    VerificarDisponibilidad verificarDisponibilidad;
    CotizacionSuministro cotizacion;
    DialogSeleccionTipoEntrega dialogTipoEntrega;
    PanelCarrito panelCarrito;
    PanelProducto panelProducto;
    PanelPaquete panelPaquete;
    private Almacen almacen;
    DetallePropuestaDTO propuesta;
    private JPanel contenedorCarrito;
    
    public void cambiarPanel(JPanel panel) {
        framePrincipal.setContentPane(panel);
        framePrincipal.revalidate();
        framePrincipal.repaint();
    }

    public void iniciarSistema() {
        if(almacen == null) {
            almacen = new Almacen();
        }
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
        if(dialogRegistroDireccion == null){
            dialogRegistroDireccion = new DialogRegistroDireccion(framePrincipal, this);
        }
        dialogRegistroDireccion.setVisible(true);
        return dialogRegistroDireccion.getSeleccionada();
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
        panelRegistrarPedido = new PanelRegistrarPedido(this);
        cambiarPanel(panelRegistrarPedido);
    }

    public void mostrarPanelDetalleProyecto() {
        if(panelDetalleProyecto == null){
            panelDetalleProyecto = new PanelDetalleProyecto(this);
        }
        cambiarPanel(panelDetalleProyecto);
    }

    public void mostrarVerificarDisponibilidad(){
        verificarDisponibilidad = new VerificarDisponibilidad(this);
        cambiarPanel(verificarDisponibilidad);
    }
    
    public void mostrarCotizacionSuministro() {
        if(cotizacion == null){
            cotizacion = new CotizacionSuministro(this);
        }
        cambiarPanel(cotizacion);
    }

    public JPanel mostrarProductos(){
        if(panelProducto == null){
            panelProducto = new PanelProducto(this);
        }
        cambiarPanel(panelProducto);
        return panelProducto;
    }

    public void vincularCarrito(JPanel destino) {
        this.contenedorCarrito = destino;
        refrescarCarrito();
    }

    public void cargarProductos(JPanel destino) {
        destino.removeAll();
        for (MaterialDTO m : Almacen.obtenerMateriales()) {
            PanelProducto tarjeta = new PanelProducto(this);
            tarjeta.setMaterial(m);   // la tarjeta guarda su producto
            tarjeta.setMaximumSize(new Dimension(Integer.MAX_VALUE,
                    tarjeta.getPreferredSize().height));
            destino.add(tarjeta);
            destino.add(Box.createVerticalStrut(8));
        }
        destino.revalidate();
        destino.repaint();
    }

    public void agregarCarrito(MaterialDTO m) {
        if (m == null) {
            return;
        }
        agregarAlCarrito(m, 1);
    }

    private void refrescarCarrito() {
        if (contenedorCarrito == null) {
            return;
        }
        
        contenedorCarrito.removeAll();
        PropuestaMaterialesDTO propuesta = almacen.obtenerPropuesta();
        
        for (DetallePropuestaDTO detalle : propuesta.getDetalles()) {
            MaterialDTO m = detalle.getMaterial();
            PanelCarrito tarjeta = new PanelCarrito(this);
            
            tarjeta.setDatos(m.getNombre(), m.getPrecioUnitario(),
                    m.getUnidadMedida(), detalle.getCantidad());
            
            tarjeta.setMaximumSize(new Dimension(Integer.MAX_VALUE,
                    tarjeta.getPreferredSize().height));
            contenedorCarrito.add(tarjeta);
            contenedorCarrito.add(Box.createVerticalStrut(8));
        }
        contenedorCarrito.revalidate();
        contenedorCarrito.repaint();
    }

    public JPanel mostrarCarrito(){
        if(panelCarrito == null){
            panelCarrito = new PanelCarrito(this);
        }
        cambiarPanel(panelCarrito);
        return panelCarrito;
    }

    public JPanel mostrarPaquete(){
        if(panelPaquete == null){
            panelPaquete = new PanelPaquete(this);
        }
        cambiarPanel(panelPaquete);
        return panelPaquete;
    }
    
    public void mostrarDialogoFechas() {
        DialogFechasEntrega dialogFechas = new DialogFechasEntrega(framePrincipal, this);
        dialogFechas.setVisible(true);
    }

    public void setDatosCarrito(String nombre, double costo, String unidad, int cantidad){
        panelCarrito.setDatos(nombre, costo, unidad, cantidad);
    }
    
    public List<DetallePropuestaDTO> getDetallesCarrito() {
        return almacen.obtenerPropuesta().getDetalles();
    }
    
    public void agregarAlCarrito(MaterialDTO material, int cantidad) {
        if (almacen == null) {
            almacen = new Almacen();
        }
        almacen.crearDetallePropuesta(material, cantidad);
        refrescarCarrito();
    }
    
    public void eliminarDelCarrito(DetallePropuestaDTO detalle) {
        almacen.eliminarDetallePropuesta(detalle);
        refrescarCarrito();
    }
    
    public void limpiarCarrito() {
        almacen.crearPropuesta();
        refrescarCarrito();
    }
    
    public void mostrarSeleccionTipoEntrega() {
        if(dialogTipoEntrega == null){
           dialogTipoEntrega = new DialogSeleccionTipoEntrega(framePrincipal, this);
        }   
        dialogTipoEntrega.setVisible(true);
    }
    
    public DetallePropuestaDTO validarDisponibilidad() {
        return almacen.validarDisponibilidad();
    }
    
    public List<MaterialDTO> obtenerSustitutos(DetallePropuestaDTO detalle) {
        return almacen.obtenerMaterialesSustitutos(detalle);
    }
    
    public int obtenerCantidad(DetallePropuestaDTO detalle, MaterialDTO candidato) {
        return almacen.obtenerCantidadNecesaria(detalle, candidato);
    }
    
    public boolean sustituirMaterial(DetallePropuestaDTO detalle, MaterialDTO nuevo) {
        return almacen.sustituirMaterial(detalle, nuevo);
    }
}
