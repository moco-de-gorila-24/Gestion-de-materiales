package control;

import Dialogs.*;
import FramePrincipal.FramePrincipal;
import Paneles.*;
import dtos.*;
import utilerias.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import almacen.Almacen;
import static almacen.Almacen.obtenerMateriales;
import gestionInventario.GestionInventarioFachada;
import gestionInventario.ISubsistemaInventario;
import java.util.Date;
import java.util.GregorianCalendar;

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
    private Almacen almacen;
    private JPanel contenedorCarrito;
    private Date fechaEntrega;
    private Date fechaLimite;
    private ClienteDTO clienteActual;
    private PropuestaMaterialesDTO propuestaActual;
    private String tipoEntrega;
    private DireccionDTO direccionActual;
    
    private final ISubsistemaInventario subsistemaInventario = new GestionInventarioFachada();
    
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
        if(dialogTelefono == null){
            dialogTelefono = new DialogTelefono(framePrincipal, this);
        }
        dialogTelefono.setVisible(true);
    }

    public DireccionDTO mostrarFormularioDireccion() {
        if(dialogRegistroDireccion == null){
            dialogRegistroDireccion = new DialogRegistroDireccion(framePrincipal, this);
        }
        dialogRegistroDireccion.setVisible(true);
        return direccionActual;
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
        for (PaqueteDTO p : Almacen.obtenerPaquetes()) {
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

    public void cambiarCantidad(DetallePropuestaDTO detalle, int cantidadCambiar, int cantidadOriginal) {

        if (propuestaActual == null || detalle == null) {
            return;
        }

        if (!propuestaActual.getDetalles().contains(detalle)) {
            return;
        }

        int nueva = cantidadOriginal + cantidadCambiar;

        if (nueva <= 0) {
            eliminarDelCarrito(detalle);
            return;
        }

        detalle.setCantidad(nueva);
        recalcularPropuesta();
        refrescarCarrito();
   }


    private void refrescarCarrito() {
        if (contenedorCarrito == null) {
            return;
        }

        contenedorCarrito.removeAll();

        if (propuestaActual != null) {
            for (int i = 0; i < propuestaActual.getDetalles().size(); i++) {
                DetallePropuestaDTO detalle = propuestaActual.getDetalles().get(i);
                MaterialDTO material = detalle.getMaterial();

                PanelCarrito tarjeta = new PanelCarrito(this);
                tarjeta.setDetalle(detalle);
                tarjeta.setDatos(material.getNombre(), material.getPrecioUnitario(), material.getUnidadMedida(),detalle.getCantidad(), material.getMarca());
                tarjeta.setMaximumSize(new Dimension(Integer.MAX_VALUE, tarjeta.getPreferredSize().height));

                contenedorCarrito.add(tarjeta);
                contenedorCarrito.add(Box.createVerticalStrut(8));
            }
        }

        if (panelRegistrarPedido != null) {
            panelRegistrarPedido.actualizarSubtotal(propuestaActual != null ? propuestaActual.getSubtotal() : 0);
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
                tipoEntrega, programarFechaEstimadaEntrega(), programarFechaEstimadaLimiteRecoger());
        dialogFechas.setVisible(true);
    }

    public void setDatosCarrito(String nombre, double costo, String unidad, int cantidad, String marca){
        panelCarrito.setDatos(nombre, costo, unidad, cantidad, marca);
    }
    
    public List<DetallePropuestaDTO> getDetallesCarrito() {
        if (almacen == null) {
            almacen = new Almacen();
        }

        if (propuestaActual == null) {
            return new ArrayList<>();
        }

        return propuestaActual.getDetalles();
    }
    
    public float obtenerSubtotal() {
        if (propuestaActual == null) {
            return 0;
        }

        return propuestaActual.getSubtotal();
    }
    
    public void cancelarVenta() {
        if (propuestaActual != null) {
            propuestaActual.setEstado("CANCELADA");
        }

        clienteActual = null;
        propuestaActual = null;
        tipoEntrega = null;
        fechaEntrega = null;
        fechaLimite = null;
        direccionActual = null;
        
        refrescarCarrito();
    }
    
    
    public void agregarAlCarrito(MaterialDTO material, int cantidad) {
        if (almacen == null) {
            almacen = new Almacen();
        }
        if (material == null || cantidad <= 0) {
            return;
        }
        
        if (propuestaActual == null) {
            return;
        }

        List<DetallePropuestaDTO> detalles = propuestaActual.getDetalles();

        for (int i = 0; i < detalles.size(); i++) {
            DetallePropuestaDTO detalle = detalles.get(i);

            if (detalle.getMaterial().getIdMaterial() == material.getIdMaterial()) {
                detalle.setCantidad(detalle.getCantidad() + cantidad);
                recalcularPropuesta();
                refrescarCarrito();
                return;
            }
        }

        detalles.add(new DetallePropuestaDTO(material, cantidad, material.getPrecioUnitario()));
        recalcularPropuesta();   
        refrescarCarrito();
    }
    
    public void eliminarDelCarrito(DetallePropuestaDTO detalle) {
        if(propuestaActual == null || detalle == null || !propuestaActual.getDetalles().contains(detalle)) {
            return;
        }
        
        boolean eliminado = almacen.eliminarDetallePropuesta(propuestaActual, detalle);
        
        if(eliminado) {
            recalcularPropuesta();
            refrescarCarrito();
        }
    }
    
    public void limpiarPropuestaActual() {
        if (almacen == null) {
            almacen = new Almacen();
        }

        if (propuestaActual == null) {
            return;
        }

        propuestaActual.setDetalles(new ArrayList<>());
        recalcularPropuesta();
        refrescarCarrito();
    }
    
    public void limpiarCarrito() {
        limpiarPropuestaActual();
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
        List<MaterialDTO> sustitutos = new ArrayList<>();

        if (propuestaActual == null || detalle == null || detalle.getMaterial() == null) {
            return sustitutos;
        }

        List<DetallePropuestaDTO> detalles = propuestaActual.getDetalles();
        List<MaterialDTO> materialesAlmacen = obtenerMateriales();
        MaterialDTO material = detalle.getMaterial();

        for (int i = 0; i < materialesAlmacen.size(); i++) {
            MaterialDTO candidato = materialesAlmacen.get(i);

            boolean mismoTipo = candidato.getTipo() != null && material.getTipo() != null && candidato.getTipo().equalsIgnoreCase(material.getTipo());
            boolean diferente = candidato.getIdMaterial() != material.getIdMaterial();
            int cantidadNecesaria = calcularCantidadTotal(detalles, detalle, candidato);
            boolean stockSuficiente = candidato.getCantidadDisponible() >= cantidadNecesaria;

            if (mismoTipo && diferente && stockSuficiente) {
                sustitutos.add(candidato);
            }
        }

        return sustitutos;
    }

    
    public int obtenerCantidadNecesaria(DetallePropuestaDTO detalle, MaterialDTO candidato) {

        if (propuestaActual == null || detalle == null || candidato == null) {
            return 0;
        }

        return calcularCantidadTotal(propuestaActual.getDetalles(), detalle, candidato);
    
    }
    
    private int calcularCantidadTotal(List<DetallePropuestaDTO> detalles, DetallePropuestaDTO detalleActual, MaterialDTO candidato) {

        if (detalles == null || detalleActual == null || candidato == null) {
            return 0;
        }

        int cantidadTotal = detalleActual.getCantidad();

        for (int i = 0; i < detalles.size(); i++) {
            DetallePropuestaDTO otro = detalles.get(i);

            if (otro != detalleActual && otro.getMaterial() != null && otro.getMaterial().getIdMaterial() == candidato.getIdMaterial()) {
                cantidadTotal += otro.getCantidad();
            }
        }

        return cantidadTotal;
    }
    
    public boolean sustituirMaterial(DetallePropuestaDTO detalle, MaterialDTO nuevo) {

        if (propuestaActual == null || detalle == null || nuevo == null || detalle.getMaterial() == null) {
             return false;
        }

        List<DetallePropuestaDTO> detalles = propuestaActual.getDetalles();

        if (!detalles.contains(detalle)) {
            return false;
        }

        int cantidadNecesaria = calcularCantidadTotal(detalles, detalle, nuevo);

        if (nuevo.getCantidadDisponible() < cantidadNecesaria) {
            return false;
        }

        DetallePropuestaDTO existente = null;

        for (int i = 0; i < detalles.size(); i++) {
            DetallePropuestaDTO otro = detalles.get(i);

            if (otro != detalle && otro.getMaterial() != null && otro.getMaterial().getIdMaterial() == nuevo.getIdMaterial()) {
                existente = otro;
                break;
            }
        }

        float subtotalAnteriorDetalle = detalle.getCantidad() * detalle.getPrecioUnitario();

        if (existente != null) {
            float subtotalAnteriorExistente = existente.getCantidad() * existente.getPrecioUnitario();
            int cantidadTotal = existente.getCantidad() + detalle.getCantidad();
            float subtotalNuevo = cantidadTotal * nuevo.getPrecioUnitario();

            existente.setCantidad(cantidadTotal);
            existente.setMaterial(nuevo);
            existente.setPrecioUnitario(nuevo.getPrecioUnitario());

            detalles.remove(detalle);

        } else {
            detalle.setMaterial(nuevo);
            detalle.setPrecioUnitario(nuevo.getPrecioUnitario()); 
        }
         
        recalcularPropuesta();
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

        boolean registrado = Almacen.GuardarCliente(cliente);

        if (registrado) {
            iniciarVenta(cliente.getTelefono());
        }

        return registrado;
    }
    
    public void seleccionarCliente(String telefono) {
        iniciarVenta(telefono);
    }
    
    public void establecerTipoEntrega(String tipo) {
        tipoEntrega = tipo;
    }
    
    public void mostrarCotizacionSuministro() {
        this.cotizacion = new CotizacionSuministro(this);
        this.cotizacion.cargarDatos(generarCotizacion(), tipoEntrega);

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
        
        return generarCotizacion();
    }
           
    public Date programarFechaEstimadaEntrega() {
        if(fechaEntrega == null) {
            GregorianCalendar fecha = new GregorianCalendar();
            fecha.add(GregorianCalendar.DAY_OF_MONTH, 5);
            fechaEntrega = fecha.getTime();
        }
        return fechaEntrega;
    }
    
    public Date programarFechaEstimadaLimiteRecoger() {
        if(fechaLimite == null) {
            GregorianCalendar fecha = new GregorianCalendar();
            fecha.add(GregorianCalendar.DAY_OF_MONTH, 10);
            fechaLimite = fecha.getTime();
        }
        return fechaLimite;
    }
    
    public void confirmarPedido() {
        if(propuestaActual == null || propuestaActual.getDetalles().isEmpty()) {
            return;
        }   
        
        if("DOMICILIO".equals(tipoEntrega) && direccionActual == null) {
            return;
        }
        CotizacionDTO cotizacion = generarCotizacion();
        
        if(cotizacion == null) {
            return;
        }
        List<DetallePedidoDTO> detalles = new ArrayList<>();
        
        for (int i = 0; i < propuestaActual.getDetalles().size(); i++) {
            DetallePropuestaDTO detalle = propuestaActual.getDetalles().get(i);
           
            detalles.add(new DetallePedidoDTO(detalle.getMaterial(), detalle.getCantidad(), 
            detalle.getPrecioUnitario(), detalle.getCantidad() * detalle.getPrecioUnitario()));
        }
        
        PedidoDTO pedido = new PedidoDTO(0, tipoEntrega, new Date(), "CONFIRMADO",
                           cotizacion.getTotal(), propuestaActual.getCliente(), detalles);
        
        PedidoDTO pedidoAlmacenado = almacen.guardarPedido(pedido);
        if (pedidoAlmacenado == null) {
            return;
        }

        
        if("DOMICILIO".equals(tipoEntrega)) {
            almacen.crearOrdenEntrega(tipoEntrega, programarFechaEstimadaEntrega(), direccionActual, pedidoAlmacenado);
        }
        
        mostrarDialogoPedidoConfirmado(pedidoAlmacenado);
    }
    
    public CotizacionDTO generarCotizacion() {
        if (propuestaActual == null) {
            return null;
        }

        float subtotal = propuestaActual.getSubtotal();
        float impuesto = subtotal * 0.16f;
        float costoEnvio = 0f;
        
        if("DOMICILIO".equals(tipoEntrega)) {
           costoEnvio = 100f;
        }
        
        float total = subtotal + impuesto + costoEnvio;

        return new CotizacionDTO(subtotal, total, new Date(), impuesto, costoEnvio, propuestaActual);
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
        refrescarCarrito();
        return propuestaActual != null;        
    }
    
    private void recalcularPropuesta() {
        if(propuestaActual == null) {
            return;
        }
        
        float subtot = 0;
        int cantidadTot = 0;
        for(int i= 0; i < propuestaActual.getDetalles().size(); i++) {
            DetallePropuestaDTO detalle = propuestaActual.getDetalles().get(i);
            subtot += detalle.getCantidad() * detalle.getPrecioUnitario();
            cantidadTot += detalle.getCantidad();
        }
        
        propuestaActual.setSubtotal(subtot);
        propuestaActual.setCantidadTotalProductos(cantidadTot);
    }
    
    public List<DireccionDTO> obtenerDirecciones() {
        return Almacen.obtenerDirecciones();
    }
    
    public void seleccionarDireccion(DireccionDTO direccion) {
        direccionActual = direccion;
    }
    
    public DireccionDTO obtenerDireccionSeleccionada() {
        return direccionActual;
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
        return almacen.guardarDireccion(direccion);
    }
}
