package org.example.LubrimaxPOO.services;

import org.example.LubrimaxPOO.model.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDateTime;

public class VentaService
{
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Venta saveVenta(Venta venta) {
        venta.setFechaHora(LocalDateTime.now());
        em.persist(venta);

        if (venta.getDetalles() != null) {
            for (DetalleVenta d : venta.getDetalles()) {
                d.setVenta(venta);
                Producto p = em.find(Producto.class, d.getProducto().getId());
                if (p == null) throw new IllegalArgumentException("Producto no encontrado: " + d.getProducto().getId());

                int current = p.getStockActual() == null ? 0 : p.getStockActual();
                int newStock = current - d.getCantidad();
                if (newStock < 0) throw new IllegalStateException("Stock insuficiente para venta");
                p.setStockActual(newStock);
                em.merge(p);

                em.persist(d);

                Movimiento m = new Movimiento();
                m.setProducto(p);
                m.setTipo(MovimientoTipo.SALIDA);
                m.setCantidad(d.getCantidad());
                m.setReferencia("Venta id:" + venta.getId());
                m.setFechaMovimiento(LocalDateTime.now());
                em.persist(m);
            }
        }
        return venta;
    }
}

