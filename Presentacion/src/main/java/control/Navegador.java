package control;

import Dialogs.*;
import FramePrincipal.FramePrincipal;
import Paneles.*;
import almacenDatos.Almacen;
import dtos.DetallePedidoDTO;
import dtos.MaterialDTO;
import utilerias.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

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

    private final List<DetallePedidoDTO> detallesCarrito = new ArrayList<>();
    
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
        if(verificarDisponibilidad == null){
            verificarDisponibilidad = new VerificarDisponibilidad(this);
        }
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
        cargarProductos();
        return panelProducto;
    }

    public void cargarProductos(){
        Almacen almacen = new Almacen();
        int productos = almacen.obtenerMateriales().toArray().length;
        List<MaterialDTO> materiales = almacen.obtenerMateriales();

        for (int i = 0; i < productos; i++) {
            panelProducto.setDatos(materiales.getFirst().getNombre(),
                    materiales.get(i).getPrecioUnitario(),
                    materiales.get(i).getUnidadMedida(),
                    materiales.get(i).getCantidadDisponible());

        }
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
    
    public List<DetallePedidoDTO> getDetallesCarrito() {
        return detallesCarrito;
    }
    
    public void agregarAlCarrito(MaterialDTO material, int cantidad) {
        if (material == null || cantidad <= 0) {
            return;
        }

        for (int i = 0; i < detallesCarrito.size(); i++) {
            DetallePedidoDTO detalle = detallesCarrito.get(i);

            if (detalle.getMaterial().getIdMaterial() == material.getIdMaterial()) {

                int nuevaCantidad = detalle.getCantidad() + cantidad;
                detalle.setCantidad(nuevaCantidad);
                detalle.setImporte(nuevaCantidad * detalle.getPrecioUnitario());

                return;
            }
        }

        detallesCarrito.add(new DetallePedidoDTO(material, cantidad, material.getPrecioUnitario(), material.getPrecioUnitario() * cantidad));
    }
    
    public void eliminarDelCarrito(DetallePedidoDTO detalle) {
        detallesCarrito.remove(detalle);
    }
    
    public void limpiarCarrito() {
        detallesCarrito.clear();
    }
    
    public void mostrarSeleccionTipoEntrega() {
        if(dialogTipoEntrega == null){
           dialogTipoEntrega = new DialogSeleccionTipoEntrega(framePrincipal, this);
        }   
        dialogTipoEntrega.setVisible(true);
    }
    
}
