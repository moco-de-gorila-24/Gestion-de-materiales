package control;

import Dialogs.*;
import FramePrincipal.FramePrincipal;
import Paneles.*;
import dtos.MaterialDTO;
import utilerias.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import almacen.Almacen;
import static almacen.Almacen.obtenerMateriales;
import dtos.ClienteDTO;
import dtos.CotizacionDTO;
import dtos.DetallePedidoDTO;
import dtos.DetallePropuestaDTO;
import dtos.DireccionDTO;
import dtos.PedidoDTO;
import dtos.PropuestaMaterialesDTO;
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
    DetallePropuestaDTO propuesta;
    private JPanel contenedorCarrito;
    private Date fechaEntrega;
    private Date fechaLimite;
    
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

    public String mostrarFormularioDireccion() {
        if(dialogRegistroDireccion == null){
            dialogRegistroDireccion = new DialogRegistroDireccion(framePrincipal, this);
        }
        dialogRegistroDireccion.setVisible(true);
        return dialogRegistroDireccion.getSeleccionada();
    }
    
    public void mostrarOrdenEntrega() {
        if (almacen == null) {
            almacen = new Almacen();
        }

        ClienteDTO cliente = almacen.obtenerClienteActual();
        DireccionDTO direccion = almacen.obtenerDireccionActual();
        String tipoEntrega = almacen.obtenerTipoEntrega();
        CotizacionDTO cotizacion = almacen.obtenerCotizacion();
        PropuestaMaterialesDTO propuesta = almacen.obtenerPropuesta();

        ordenEntrega = new ordenEntrega(this);
        ordenEntrega.cargarDatos(cliente, direccion, "Pendiente de confirmacion", propuesta != null ? propuesta.getDetalles() : null, tipoEntrega);

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

    public void cambiarCantidad(DetallePropuestaDTO detalle, int cantidad) {
        PropuestaMaterialesDTO propuesta = almacen.obtenerPropuesta();

        if (propuesta == null || detalle == null) {
            return;
        }

        if (!propuesta.getDetalles().contains(detalle)) {
            return;
        }

        int nueva = detalle.getCantidad() + cantidad;

        if (nueva <= 0) {
            eliminarDelCarrito(detalle);
            return;
        }

        detalle.setCantidad(nueva);

        propuesta.setSubtotal(propuesta.getSubtotal() + cantidad * detalle.getPrecioUnitario()
        );

        propuesta.setCantidadTotalProductos(propuesta.getCantidadTotalProductos() + cantidad
        );
        
        refrescarCarrito();
   }


    private void refrescarCarrito() {
        if (contenedorCarrito == null || almacen == null) {
            return;
        }

        contenedorCarrito.removeAll();

        PropuestaMaterialesDTO propuesta = almacen.obtenerPropuesta();

        if (propuesta != null) {
            for (int i = 0; i < propuesta.getDetalles().size(); i++) {
                DetallePropuestaDTO detalle = propuesta.getDetalles().get(i);
                MaterialDTO material = detalle.getMaterial();

                PanelCarrito tarjeta = new PanelCarrito(this);
                tarjeta.setDetalle(detalle);
                tarjeta.setDatos(material.getNombre(), material.getPrecioUnitario(), material.getUnidadMedida(),detalle.getCantidad());
                tarjeta.setMaximumSize(new Dimension(Integer.MAX_VALUE, tarjeta.getPreferredSize().height));

                contenedorCarrito.add(tarjeta);
                contenedorCarrito.add(Box.createVerticalStrut(8));
            }
        }

        if (panelRegistrarPedido != null) {
            panelRegistrarPedido.actualizarSubtotal(propuesta != null ? propuesta.getSubtotal() : 0);
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
                almacen.obtenerTipoEntrega(), programarFechaEstimadaEntrega(), programarFechaEstimadaLimiteRecoger());
        dialogFechas.setVisible(true);
    }

    public void setDatosCarrito(String nombre, double costo, String unidad, int cantidad){
        panelCarrito.setDatos(nombre, costo, unidad, cantidad);
    }
    
    public List<DetallePropuestaDTO> getDetallesCarrito() {
        if (almacen == null) {
            almacen = new Almacen();
        }

        PropuestaMaterialesDTO propuesta = almacen.obtenerPropuesta();

        if (propuesta == null) {
            return new ArrayList<>();
        }

        return propuesta.getDetalles();
    }
    
    public float obtenerSubtotal() {
        if (almacen == null) {
            almacen = new Almacen();
        }

        PropuestaMaterialesDTO propuesta = almacen.obtenerPropuesta();

        if (propuesta == null) {
            return 0;
        }

        return propuesta.getSubtotal();
    }
    
    public void agregarAlCarrito(MaterialDTO material, int cantidad) {
        if (almacen == null) {
            almacen = new Almacen();
        }
        if (material == null || cantidad <= 0) {
            return;
        }

        PropuestaMaterialesDTO propuesta = almacen.obtenerPropuesta();

        if (propuesta == null) {
            return;
        }

        List<DetallePropuestaDTO> detalles = propuesta.getDetalles();

        for (int i = 0; i < detalles.size(); i++) {
            DetallePropuestaDTO detalle = detalles.get(i);

            if (detalle.getMaterial().getIdMaterial() == material.getIdMaterial()) {
                detalle.setCantidad(detalle.getCantidad() + cantidad);

                propuesta.setSubtotal(propuesta.getSubtotal() + cantidad * detalle.getPrecioUnitario());

                propuesta.setCantidadTotalProductos(propuesta.getCantidadTotalProductos() + cantidad);
                refrescarCarrito();
                return;
            }
        }

        detalles.add(new DetallePropuestaDTO(material, cantidad, material.getPrecioUnitario()));

        propuesta.setSubtotal(propuesta.getSubtotal() + cantidad * material.getPrecioUnitario());

        propuesta.setCantidadTotalProductos(propuesta.getCantidadTotalProductos() + cantidad);
    
        refrescarCarrito();
    }
    
    public void eliminarDelCarrito(DetallePropuestaDTO detalle) {
        PropuestaMaterialesDTO propuesta = almacen.obtenerPropuesta();
        if(propuesta == null || detalle == null || !propuesta.getDetalles().contains(detalle)) {
            return;
        }
        
        boolean eliminado = almacen.eliminarDetallePropuesta(detalle);
        
        if(eliminado) {
            propuesta.setSubtotal(propuesta.getSubtotal() - detalle.getCantidad() * detalle.getPrecioUnitario());
            propuesta.setCantidadTotalProductos(propuesta.getCantidadTotalProductos() - detalle.getCantidad());
 
            refrescarCarrito();
        }
    }
    
    public void limpiarPropuestaActual() {
        if (almacen == null) {
            almacen = new Almacen();
        }

        PropuestaMaterialesDTO propuesta = almacen.obtenerPropuesta();

        if (propuesta == null) {
            return;
        }

        propuesta.setDetalles(new ArrayList<>());
        propuesta.setSubtotal(0f);
        propuesta.setCantidadTotalProductos(0);
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
        PropuestaMaterialesDTO propuesta = almacen.obtenerPropuesta();

        if (propuesta == null) {
            return null;
        }

        List<DetallePropuestaDTO> detalles = propuesta.getDetalles();

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

        PropuestaMaterialesDTO propuesta = almacen.obtenerPropuesta();

        if (propuesta == null || detalle == null || detalle.getMaterial() == null) {
            return sustitutos;
        }

        List<DetallePropuestaDTO> detalles = propuesta.getDetalles();
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
        PropuestaMaterialesDTO propuesta = almacen.obtenerPropuesta();

        if (propuesta == null || detalle == null || candidato == null) {
            return 0;
        }

        return calcularCantidadTotal(propuesta.getDetalles(), detalle, candidato);
    
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
        PropuestaMaterialesDTO propuesta = almacen.obtenerPropuesta();

        if (propuesta == null || detalle == null || nuevo == null || detalle.getMaterial() == null) {
             return false;
        }

         List<DetallePropuestaDTO> detalles = propuesta.getDetalles();

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

             propuesta.setSubtotal(propuesta.getSubtotal() - subtotalAnteriorDetalle - subtotalAnteriorExistente + subtotalNuevo);

         } else {
             detalle.setMaterial(nuevo);
             detalle.setPrecioUnitario(nuevo.getPrecioUnitario());
             float subtotalNuevo = detalle.getCantidad() * nuevo.getPrecioUnitario();

             propuesta.setSubtotal(propuesta.getSubtotal() - subtotalAnteriorDetalle + subtotalNuevo);
         }

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
        if (almacen == null) {
            almacen = new Almacen();
        }

        boolean registrado = Almacen.GuardarCliente(cliente);

        if (registrado) {
            almacen.seleccionarCliente(cliente);
        }

        return registrado;
    }
    
    public void seleccionarCliente(String telefono) {
        if (almacen == null) {
            almacen = new Almacen();
        }

        ClienteDTO cliente = Almacen.buscarCliente(telefono);

        if (cliente != null) {
            almacen.seleccionarCliente(cliente);
            refrescarCarrito();
        }
    }
    
    public void cancelarPropuestaActual() {
        if (almacen == null) {
            almacen = new Almacen();
        }

        almacen.cancelarPropuestaActual();
    }
    
    public void establecerTipoEntrega(String tipo) {
        almacen.establecerTipoEntrega(tipo);
    }
    
    public void mostrarCotizacionSuministro() {
        CotizacionDTO cotizacion = almacen.obtenerCotizacion();

        this.cotizacion = new CotizacionSuministro(this);
        this.cotizacion.cargarDatos(cotizacion, almacen.obtenerTipoEntrega());

        cambiarPanel(this.cotizacion);
    }
    
    public CotizacionDTO actualizarConfirmacionMaterial(int fila, boolean aceptado) {
        PropuestaMaterialesDTO propuesta = almacen.obtenerPropuesta();
        
        if(fila<0 || fila >= propuesta.getDetalles().size() || propuesta == null) {
            return null;
        }
        if(!aceptado) {
            DetallePropuestaDTO detalle = propuesta.getDetalles().get(fila);
            eliminarDelCarrito(detalle);        
        }
        
        return almacen.obtenerCotizacion();
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
        PropuestaMaterialesDTO propuesta = almacen.obtenerPropuesta();
        CotizacionDTO cotizacion = almacen.obtenerCotizacion();
        
        if(propuesta == null || propuesta.getDetalles().isEmpty()) {
            return;
        }       
        if(cotizacion == null) {
            return;
        }
        List<DetallePedidoDTO> detalles = new ArrayList<>();
        
        for (int i = 0; i < propuesta.getDetalles().size(); i++) {
            DetallePropuestaDTO detalle = propuesta.getDetalles().get(i);
           
            detalles.add(new DetallePedidoDTO(detalle.getMaterial(), detalle.getCantidad(), 
            detalle.getPrecioUnitario(), detalle.getCantidad() * detalle.getPrecioUnitario()));
        }
        
        PedidoDTO pedido = new PedidoDTO(0, almacen.obtenerTipoEntrega(), new Date(), "CONFIRMADO",
                           cotizacion.getTotal(), propuesta.getCliente(), detalles);
        
        PedidoDTO pedidoAlmacenado = almacen.guardarPedido(pedido);
        if (pedidoAlmacenado == null) {
            return;
        }
        if("DOMICILIO".equals(almacen.obtenerTipoEntrega())) {
            almacen.crearOrdenEntrega(almacen.obtenerTipoEntrega(), programarFechaEstimadaEntrega(), almacen.obtenerDireccionActual(), pedidoAlmacenado);
        }
        
        mostrarDialogoPedidoConfirmado(pedidoAlmacenado);
    }
}
