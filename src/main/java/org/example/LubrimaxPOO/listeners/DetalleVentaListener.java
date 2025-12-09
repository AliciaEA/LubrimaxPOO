package org.example.LubrimaxPOO.listeners;

import org.example.LubrimaxPOO.model.*;
import org.openxava.jpa.XPersistence;

import javax.persistence.*;
import java.time.LocalDateTime;


public class DetalleVentaListener {

    @PostPersist
    public void crearMovimientoInventario(DetalleVenta detalle) {
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
            movimiento.setTipo(MovimientoTipo.SALIDA);
            movimiento.setCantidad(detalle.getCantidad());

            String referencia = "Venta";
            if (detalle.getVenta() != null) {
                if (detalle.getVenta().getId() != null) {
                    referencia = "Venta id:" + detalle.getVenta().getId();
                } else {
                    em.flush();
                    if (detalle.getVenta().getId() != null) {
                        referencia = "Venta id:" + detalle.getVenta().getId();
                    }
                }
            }
            movimiento.setReferencia(referencia);
            movimiento.setFechaMovimiento(LocalDateTime.now());

            em.persist(movimiento);
            em.flush();
            
        } catch (Exception e) {
            System.err.println("Error al crear movimiento de inventario en DetalleVenta: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

