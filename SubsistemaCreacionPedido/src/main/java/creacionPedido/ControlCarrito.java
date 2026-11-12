/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package creacionPedido;


import dtos.DetallePropuestaDTO;
import dtos.MaterialDTO;
import dtos.PropuestaMaterialesDTO;
import excepciones.CreacionPedidoException;
import interfaces.IMaterialBO;
import interfaces.IPropuestaMaterialesBO;
import java.util.ArrayList;
import java.util.List;
import objetosNegocio.MaterialBO;
import objetosNegocio.PropuestaMaterialesBO;

/**
 * Controlador del carrito (propuesta). Se comunica con las BO.
 * 
 * @author cinca
 */
public class ControlCarrito {
    private IPropuestaMaterialesBO propuestaBO;
    private IMaterialBO materialBO;
    private PropuestaMaterialesDTO propuestaActual;
    
    public ControlCarrito() {
        propuestaBO = new PropuestaMaterialesBO();
        materialBO = new MaterialBO();
    }   

    /**
     * Se asigna la propuesta activa de la venta para que se puedan realizar las 
     * operaciones de agregar, modificar o eliminar detalles.
     * 
     * @param propuesta la propuesta activa.
     */
    public void iniciarCarrito(PropuestaMaterialesDTO propuesta) {
        propuestaActual = propuesta;
    }
    
    public PropuestaMaterialesDTO obtenerPropuestaActual() {
        return propuestaActual;
    }
    
    /**
     * Metodo que sirve para finalizar la propuesta despues de cnofirmar el pedido.
     * Para limpiarla, apunta a null.
     * 
     */
    public void finalizarPropuestaConfirmada() throws CreacionPedidoException {
        if(propuestaActual == null) {
            throw new CreacionPedidoException("la propuesta esta vacia.");
        }       
        propuestaActual.setEstado("CONFIRMADA");
        
        if(!propuestaBO.actualizarPropuesta(propuestaActual)) {
            propuestaActual.setEstado("PENDIENTE");
            throw new CreacionPedidoException("No se pudo actualizar el estado de la propuesta");
        }
        propuestaActual = null;
    }

    /**
     * Metodo encargado de realizar la operacion de agregar un producto al 
     * carrito.
     * Si el material ya esta en la propuesta activa, se incrementa su cantidad (primer caso),
     * de lo contrario se crea un nuevo detalle y se recalculan los totales y
     * se actualiza la propuesta mediante el BO correspondiente.
     * 
     * @param material material de la lista de materiales.
     * @param cantidad cantidad de unidades del material que se añadiran.
     */
    public void AgregarAlCarrito(MaterialDTO material, int cantidad) {
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
                propuestaBO.actualizarPropuesta(propuestaActual);
                return;
            }
        }

        detalles.add(new DetallePropuestaDTO(material, cantidad, material.getPrecioUnitario()));
        recalcularPropuesta();   
        propuestaBO.actualizarPropuesta(propuestaActual);
    }

     /**
     * Recalcula el subtotal y la cantidad de productos de la propuesta activa.
     * 
     */
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
    
    public boolean eliminarDelCarrito(DetallePropuestaDTO detalle) {
         if (propuestaActual == null || detalle == null) {
            return false;
        }
         
        if(!propuestaActual.getDetalles().remove(detalle)) {
            return false;
        }
        recalcularPropuesta();
        propuestaBO.actualizarPropuesta(propuestaActual);
        return true;
    }
    
    public void limpiarPropuestaActual() {
        if (propuestaActual == null) {
            return;
        }

        propuestaActual.setDetalles(new ArrayList<>());
        recalcularPropuesta();
        propuestaBO.actualizarPropuesta(propuestaActual);
    }
    
    public void cancelarVenta() {
        if (propuestaActual != null || !"PENDIENTE".equals(propuestaActual.getEstado())) {
            return;
        }
        propuestaActual.setEstado("CANCELADA");
        if(propuestaBO.actualizarPropuesta(propuestaActual)) {
            propuestaActual = null;
        }
    }
    
    public List<DetallePropuestaDTO> getDetallesCarrito() {
        if (propuestaActual == null) {
            return new ArrayList<>();
        }

        return propuestaActual.getDetalles();
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
        return propuestaBO.actualizarPropuesta(propuestaActual);
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

    public List<MaterialDTO> obtenerMaterialesSustitutos(DetallePropuestaDTO detalle) {
        List<MaterialDTO> sustitutos = new ArrayList<>();

        if (propuestaActual == null || detalle == null || detalle.getMaterial() == null) {
            return sustitutos;
        }

        List<DetallePropuestaDTO> detalles = propuestaActual.getDetalles();
        List<MaterialDTO> materialesAlmacen = materialBO.consultarMateriales();
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
    
    public float obtenerSubtotal() {
        if (propuestaActual == null) {
            return 0;
        }

        return propuestaActual.getSubtotal();
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
        propuestaBO.actualizarPropuesta(propuestaActual);
   }

}
