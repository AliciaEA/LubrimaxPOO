package org.example.LubrimaxPOO.services;

import org.example.LubrimaxPOO.model.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDateTime;

public class CompraService
{
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Compra saveCompra(Compra compra)
    {
        // La fecha se fija aquí; el stock se actualiza en DetalleCompra via callbacks JPA
        compra.setFechaCompra(LocalDateTime.now());
        em.persist(compra);

        if (compra.getDetalles() != null)
        {
            for (DetalleCompra d : compra.getDetalles())
            {
                // Asociamos el detalle con la compra; el callback @PrePersist en DetalleCompra
                // se encargará de actualizar el stock del producto
                d.setCompra(compra);

                Producto p = em.find(Producto.class, d.getProducto().getId());
                if (p == null) throw new IllegalArgumentException("Producto no encontrado: " + d.getProducto().getId());

                em.persist(d);

                // Registro del movimiento de entrada asociado a la compra
                Movimiento m = new Movimiento();
                m.setProducto(p);
                m.setTipo(MovimientoTipo.ENTRADA);
                m.setCantidad(d.getCantidad());
                m.setReferencia("Compra id:" + compra.getId());
                m.setFechaMovimiento(LocalDateTime.now());
                em.persist(m);
            }
        }
        return compra;
    }
}
