package org.example.LubrimaxPOO.listeners;

import org.example.LubrimaxPOO.model.*;
import org.openxava.jpa.XPersistence;

import javax.persistence.*;
import java.time.LocalDateTime;


public class DetalleCompraListener {

    @PostPersist
    public void crearMovimientoInventario(DetalleCompra detalle) {
        if (detalle == null || detalle.getProducto() == null || detalle.getCantidad() == null) {
            return;
        }

        try {
            EntityManager em = XPersistence.getManager();

            Producto producto = em.find(Producto.class, detalle.getProducto().getId());
            if (producto == null) {
                return;
            }

            Movimiento movimiento = new Movimiento();
            movimiento.setProducto(producto);
            movimiento.setTipo(MovimientoTipo.ENTRADA);
            movimiento.setCantidad(detalle.getCantidad());

            String referencia = "Compra";
            if (detalle.getCompra() != null) {

                if (detalle.getCompra().getId() != null) {
                    referencia = "Compra id:" + detalle.getCompra().getId();
                } else {

                    em.flush();
                    if (detalle.getCompra().getId() != null) {
                        referencia = "Compra id:" + detalle.getCompra().getId();
                    }
                }
            }
            movimiento.setReferencia(referencia);
            movimiento.setFechaMovimiento(LocalDateTime.now());

            em.persist(movimiento);
            em.flush();
            
        } catch (Exception e) {
            System.err.println("Error al crear movimiento de inventario en DetalleCompra: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

