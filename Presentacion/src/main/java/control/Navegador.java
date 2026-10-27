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
    private JPanel contenedorCarrito;
    private  Map<MaterialDTO, Integer> carrito = new LinkedHashMap<>();

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
        carrito.merge(m, 1, Integer::sum);
        agregarAlCarrito(m, 1);

        refrescarCarrito();
    }

    private void refrescarCarrito() {
        if (contenedorCarrito == null) return;
        contenedorCarrito.removeAll();
        for (Map.Entry<MaterialDTO, Integer> e : carrito.entrySet()) {
            MaterialDTO m = e.getKey();
            PanelCarrito tarjeta = new PanelCarrito(this);
            tarjeta.setDatos(m.getNombre(), m.getPrecioUnitario(),
                    m.getUnidadMedida(), e.getValue());
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
        carrito.clear();
        detallesCarrito.clear();
        refrescarCarrito();
    }
    
    public void mostrarSeleccionTipoEntrega() {
        if(dialogTipoEntrega == null){
           dialogTipoEntrega = new DialogSeleccionTipoEntrega(framePrincipal, this);
        }   
        dialogTipoEntrega.setVisible(true);
    }
    
}
