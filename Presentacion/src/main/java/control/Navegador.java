package control;

import Dialogs.*;
import FramePrincipal.FramePrincipal;
import Paneles.*;
import dtos.*;
import utilerias.*;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import almacen.Almacen;
import creacionPedido.CrearPedidoFachada;
import creacionPedido.ISubsistemaCrearPedido;
import excepciones.CreacionPedidoException;
import gestionInventario.GestionInventarioFachada;
import gestionInventario.ISubsistemaInventario;
import java.util.ArrayList;

public class Navegador {
    FramePrincipal framePrincipal;
    PanelMenuPrincipal panelMenuPricipal;
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
    PanelGestionReposicion panelGestionReposicion;
    private Almacen almacen;
    private JPanel contenedorCarrito;
    private ClienteDTO clienteActual;
    private PropuestaMaterialesDTO propuestaActual;
    private String tipoEntrega;
    private DireccionDTO direccionActual;
    
    private final ISubsistemaInventario subsistemaInventario = new GestionInventarioFachada();
    private final ISubsistemaCrearPedido subsistemaCrearPedido = new CrearPedidoFachada();
    
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
            panelMenuPricipal = new PanelMenuPrincipal(this);
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
        if (dialogTelefono == null) {
            dialogTelefono = new DialogTelefono(framePrincipal, this);
        }

        dialogTelefono.setVisible(true);
    }

    public void mostrarDialogoBuscarCliente(String telefono) {
        if (dialogTelefono == null) {
            dialogTelefono = new DialogTelefono(framePrincipal, this);
        }

        dialogTelefono.establecerTelefono(telefono);
        dialogTelefono.setVisible(true);
    }

    public DireccionDTO mostrarFormularioDireccion() {
        if(dialogRegistroDireccion == null){
            dialogRegistroDireccion = new DialogRegistroDireccion(framePrincipal, this);
        }
        dialogRegistroDireccion.setVisible(true);
        return subsistemaCrearPedido.obtenerDireccionSeleccionada();
    }
    
    public void mostrarOrdenEntrega() {
        if (almacen == null) {
            almacen = new Almacen();
        }

        ordenEntrega = new ordenEntrega(this);
        ordenEntrega.cargarDatos(clienteActual, direccionActual, "Pendiente de confirmacion", propuestaActual != null ? propuestaActual.getDetalles() : null, tipoEntrega);

        cambiarPanel(ordenEntrega);
    }
    
    public void mostrarDialogoPedidoConfirmado(PedidoDTO pedido) {
        if(pedidoConfirmado == null) {
            pedidoConfirmado = new DialogPedidoConfirmado(framePrincipal, this, pedido);
        }   
        pedidoConfirmado.setVisible(true);    
    }
    
    public void mostrarPanelRegistrarPedido(){
        panelRegistrarPedido = new PanelRegistrarPedido(this);
        cambiarPanel(panelRegistrarPedido);
        panelRegistrarPedido.actualizarSubtotal(subsistemaCrearPedido.obtenerSubtotal());
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

    public void buscarProductos(JPanel panelDestino, String nombre){
        MaterialDTO materialEncontrado = subsistemaInventario.consultarMaterial(nombre);
        if(materialEncontrado == null){
            JOptionPane.showMessageDialog(null,"Producto no encontrado");
        }
        panelDestino.removeAll();
        PanelProducto productoEncontrado = new PanelProducto(this);
        productoEncontrado.setMaterial(materialEncontrado);
        panelDestino.add(productoEncontrado);
        panelDestino.revalidate();
        panelDestino.repaint();
    }

    public void cargarPaquetes(JPanel destino){
        destino.removeAll();
        for (PaqueteDTO p : subsistemaInventario.consultarPaquetes()) {
            PanelPaquete tarjeta = new PanelPaquete(this);
            tarjeta.setPaquete(p);
            tarjeta.setMaximumSize(new Dimension(Integer.MAX_VALUE,
                    tarjeta.getPreferredSize().height));
            destino.add(tarjeta);
            destino.add(Box.createVerticalStrut(8));
        }
        destino.revalidate();
        destino.repaint();
    }

    public void cargarProductos(JPanel destino) {
        destino.removeAll();
        for (MaterialDTO m : subsistemaInventario.consultarMaterial()) {
            PanelProducto tarjeta = new PanelProducto(this);
            tarjeta.setMaterial(m);
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

    public void agregarPaqueteCarrito(PaqueteDTO p) {
        if (p == null) {
            return;
        }
        subsistemaCrearPedido.agregarAlCarrito(p);
        refrescarCarrito();
    }

    public void cambiarCantidad(DetallePropuestaDTO detalle, int cantidadCambiar, int cantidadOriginal) {
        subsistemaCrearPedido.cambiarCantidad(detalle, cantidadCambiar, cantidadOriginal);
        refrescarCarrito();
   }


    private void refrescarCarrito() {
        if (contenedorCarrito == null) {
            return;
        }

        contenedorCarrito.removeAll();
        List<DetallePropuestaDTO> detalles = subsistemaCrearPedido.getDetallesCarrito();


        for (int i = 0; i < detalles.size(); i++) {
            DetallePropuestaDTO detalle = detalles.get(i);
            MaterialDTO material = detalle.getMaterial();

            PanelCarrito tarjeta = new PanelCarrito(this);
            tarjeta.setDetalle(detalle);
            tarjeta.setDatos(material.getNombre(), material.getPrecioUnitario(), material.getUnidadMedida(),detalle.getCantidad(), material.getMarca());
            tarjeta.setMaximumSize(new Dimension(Integer.MAX_VALUE, tarjeta.getPreferredSize().height));

            contenedorCarrito.add(tarjeta);
            contenedorCarrito.add(Box.createVerticalStrut(8));
        }
            
        if (panelRegistrarPedido != null) {
            panelRegistrarPedido.actualizarSubtotal(subsistemaCrearPedido.obtenerSubtotal());
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
        DialogFechasEntrega dialogFechas = new DialogFechasEntrega(framePrincipal, this, 
                subsistemaCrearPedido.obtenerTipoEntrega(), subsistemaCrearPedido.programarFechaEstimadaEntrega(), subsistemaCrearPedido.programarFechaEstimadaLimiteRecoger());
        dialogFechas.setVisible(true);
    }

    public void setDatosCarrito(String nombre, double costo, String unidad, int cantidad, String marca){
        panelCarrito.setDatos(nombre, costo, unidad, cantidad, marca);
    }
    
    public List<DetallePropuestaDTO> getDetallesCarrito() {
        return subsistemaCrearPedido.getDetallesCarrito();
    }
    
    public void cancelarVenta() {
        subsistemaCrearPedido.cancelarVenta();        
        refrescarCarrito();
    }
    
    
    public void agregarAlCarrito(MaterialDTO material, int cantidad) {
        if (material == null || cantidad <= 0) {
            return;
        }
        
        subsistemaCrearPedido.agregarAlCarrito(material, cantidad);
        refrescarCarrito();

    }
    
    public void eliminarDelCarrito(DetallePropuestaDTO detalle) {
            subsistemaCrearPedido.eliminarDelCarrito(detalle);
            refrescarCarrito();
    }
    
    public void mostrarSeleccionTipoEntrega() {
        if(dialogTipoEntrega == null){
           dialogTipoEntrega = new DialogSeleccionTipoEntrega(framePrincipal, this);
        }   
        dialogTipoEntrega.setVisible(true);
    }
    
    public DetallePropuestaDTO validarDisponibilidad() {
 
        if (propuestaActual == null) {
            return null;
        }

        List<DetallePropuestaDTO> detalles = propuestaActual.getDetalles();

        for (int i = 0; i < detalles.size(); i++) {
            DetallePropuestaDTO detalle = detalles.get(i);
            MaterialDTO material = detalle.getMaterial();

            if (material == null || detalle.getCantidad() > material.getCantidadDisponible()) {
                return detalle;
            }
        }

        return null;
    }
    
    public List<MaterialDTO> obtenerMaterialesSustitutos(DetallePropuestaDTO detalle) {
        return subsistemaCrearPedido.obtenerMaterialesSustitutos(detalle);
    }

    
    public int obtenerCantidadNecesaria(DetallePropuestaDTO detalle, MaterialDTO candidato) {
        return subsistemaCrearPedido.obtenerCantidadNecesaria(detalle, candidato);
    
    }
    
    public boolean sustituirMaterial(DetallePropuestaDTO detalle, MaterialDTO nuevo) {
        if(!subsistemaCrearPedido.sustituirMaterial(detalle, nuevo)) {
            return false;
        }
        refrescarCarrito();
        return true;
    }

    public boolean validarTextoVacio(String texto) {
        return ControlValidaciones.validarTextoVacio(texto);
    }

    public boolean validarTelefono(String telefono) {
        return ControlValidaciones.validarTelefono(telefono);
    }

    public boolean clienteExiste(String telefono) {
        return Almacen.buscarCliente(telefono) != null;
    }

    public ClienteDTO buscarClientePorTelefono(String telefono) {
        if (telefono == null || telefono.trim().isEmpty()) {
            return null;
        }

        return Almacen.buscarCliente(telefono.trim());
    }
    
    public boolean validarSoloTexto(String texto) {
        return ControlValidaciones.validarSoloTexto(texto);
    }
    
    public boolean agregarCliente(ClienteDTO cliente) {
        if (cliente == null) {
            return false;
        }
        
        if(!validarTextoVacio(cliente.getNombre()) || !validarTextoVacio(cliente.getApellidoPa()) || !validarTextoVacio(cliente.getApellidoMa()) || !validarTextoVacio(cliente.getTelefono())) {
            return false;
        }
        
        if(!validarSoloTexto(cliente.getNombre()) || !validarSoloTexto(cliente.getApellidoPa()) || !validarSoloTexto(cliente.getApellidoMa())) {
            return false;
        }
        
        if(!validarTelefono(cliente.getTelefono()) || clienteExiste(cliente.getTelefono())) {
            return false;
        }

        return Almacen.GuardarCliente(cliente);
    }
    
    public void seleccionarCliente(String telefono) {
        iniciarVenta(telefono);
    }
    
    public void establecerTipoEntrega(String tipo) {
        subsistemaCrearPedido.establecerTipoEntrega(tipo);
    }
    
    public void mostrarCotizacionSuministro() {
        this.cotizacion = new CotizacionSuministro(this);
        this.cotizacion.cargarDatos(subsistemaCrearPedido.generarCotizacion(),subsistemaCrearPedido.obtenerTipoEntrega());

        cambiarPanel(this.cotizacion);
    }
    
    public CotizacionDTO actualizarConfirmacionMaterial(int fila, boolean aceptado) {        
        if(propuestaActual == null || fila<0 || fila >= propuestaActual.getDetalles().size()) {
            return null;
        }
        if(!aceptado) {
            DetallePropuestaDTO detalle = propuestaActual.getDetalles().get(fila);
            eliminarDelCarrito(detalle);        
        }
        
        return subsistemaCrearPedido.generarCotizacion();
    }
           
    public void confirmarPedido() {
        try {
            PedidoDTO pedido= subsistemaCrearPedido.confirmarPedido();
            
            if(ordenEntrega != null) {
                ordenEntrega.marcarPedidoConfirmado();
            }
            refrescarCarrito();
            mostrarDialogoPedidoConfirmado(pedido);
            
        } catch (CreacionPedidoException e) {
            JOptionPane.showMessageDialog(framePrincipal, e.getMessage());
        }
    
    }
    
    public boolean iniciarVenta(String telefono) {
        clienteActual = almacen.buscarCliente(telefono);
       
        if (clienteActual == null) {
            propuestaActual = null;
            return false;
        }
        
        propuestaActual = almacen.buscarPropuestaPendiente(clienteActual);
        if(propuestaActual == null) {
            propuestaActual = almacen.guardarPropuesta(clienteActual);
        }
        subsistemaCrearPedido.iniciarCarrito(propuestaActual);
        refrescarCarrito();
        return propuestaActual != null;        
    }
    
    public List<DireccionDTO> obtenerDirecciones() {
        if(clienteActual == null) {
            return new ArrayList<>();
        }
        return subsistemaCrearPedido.obtenerDirecciones(clienteActual.getId());
    }
    
    public void seleccionarDireccion(DireccionDTO direccion) {
        subsistemaCrearPedido.seleccionarDireccion(direccion);
    }
    
    
    public boolean registrarDireccion(String calle, String colonia, String numeroTxt, String cpTxt) {
        if(!validarTextoVacio(calle) || !validarTextoVacio(colonia) || !validarTextoVacio(numeroTxt) || !validarTextoVacio(cpTxt)) {
            return false;
        }

        Integer numero = ControlValidaciones.cambiarAEntero(numeroTxt);
        Integer codigoP= ControlValidaciones.cambiarAEntero(cpTxt);
        
        if(numero == null || codigoP == null) {
            return false;
        }
        
        DireccionDTO direccion = new DireccionDTO(calle.trim(), colonia.trim(), numero, codigoP);
        return subsistemaCrearPedido.guardarDireccion(clienteActual.getId(), direccion);
    }
    
    public void mostrarPanelGestionReposicion() {
        panelGestionReposicion = new PanelGestionReposicion(this);
        cambiarPanel(panelGestionReposicion);
    }
    
    public List<DetalleReposicionDTO> consultarMaterialesInsuficientes() {
        return subsistemaInventario.consultarMaterialesInsuficientes();
    }

    public SolicitudReposicionDTO generarSolicitudReposicion() {
        return subsistemaInventario.generarSolicitudReposicion();
    }

    public List<SolicitudReposicionDTO> consultarSolicitudesReposicion() {
        return subsistemaInventario.consultarSolicitudesReposicion();
    }
}
