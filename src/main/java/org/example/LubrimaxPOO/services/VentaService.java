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
        // La fecha se fija aquí; el stock se actualiza en DetalleVenta via callbacks JPA
        venta.setFechaHora(LocalDateTime.now());
        em.persist(venta);

        if (venta.getDetalles() != null) {
            for (DetalleVenta d : venta.getDetalles()) {
                // Asociamos el detalle con la venta; el callback @PrePersist en DetalleVenta
                // se encargará de validar y actualizar el stock del producto
                d.setVenta(venta);

                Producto p = em.find(Producto.class, d.getProducto().getId());
                if (p == null) throw new IllegalArgumentException("Producto no encontrado: " + d.getProducto().getId());

                em.persist(d);

                // Registro del movimiento de salida asociado a la venta
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
