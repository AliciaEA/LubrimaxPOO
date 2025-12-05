package org.example.LubrimaxPOO.services;

import org.example.LubrimaxPOO.model.Movimiento;
import org.example.LubrimaxPOO.model.MovimientoTipo;
import org.example.LubrimaxPOO.model.Producto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDateTime;

public class MovimientoService
{

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Movimiento addManualMovimiento(Long productoId, MovimientoTipo tipo, Integer cantidad, String referencia) {
        Producto producto = em.find(Producto.class, productoId);
        if (producto == null) throw new IllegalArgumentException("Producto no encontrado: " + productoId);

        int adjustment = cantidad;
        if (tipo == MovimientoTipo.SALIDA) adjustment = -Math.abs(cantidad);
        else if (tipo == MovimientoTipo.ENTRADA) adjustment = Math.abs(cantidad);

        int current = producto.getStockActual() == null ? 0 : producto.getStockActual();
        int newStock = current + adjustment;
        if (newStock < 0) throw new IllegalStateException("Stock insuficiente para ajuste");
        producto.setStockActual(newStock);
        em.merge(producto);

        Movimiento m = new Movimiento();
        m.setProducto(producto);
        m.setTipo(tipo);
        m.setCantidad(cantidad);
        m.setReferencia(referencia);
        m.setFechaMovimiento(LocalDateTime.now());
        em.persist(m);
        return m;
    }
}
