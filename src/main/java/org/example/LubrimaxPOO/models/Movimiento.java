package org.example.LubrimaxPOO.models;

import org.example.LubrimaxPOO.models.*;
import org.openxava.model.Identifiable;

import javax.persistence.Entity;
import javax.persistence.*;
import org.openxava.annotations.*;

import java.time.*;
@Entity
public class Movimiento extends Identifiable
{
    @ManyToOne(fetch = FetchType.LAZY)
    @DescriptionsList
    @Required
    private Producto producto;

    @Required
    private int cantidad;

    @Enumerated(EnumType.STRING)
    @Required
    private TipoMovimiento tipo;

    @ReadOnly
    private LocalDate fecha;

    @Transient
    private boolean stockYaActualizado = false;

    @PrePersist
    public void alGuardar()
    {
        if (fecha == null) fecha = LocalDate.now();
        // Lógica para ajustes manuales desde el módulo Movimientos
        if (!stockYaActualizado && producto != null)
        {
            if (tipo == TipoMovimiento.ENTRADA_AJUSTE)
            {
                producto.setStock(producto.getStock() + cantidad);
            } else if (tipo == TipoMovimiento.SALIDA_AJUSTE)
            {
                producto.setStock(producto.getStock() - cantidad);
            }
        }
    }

    // Getters y Setters Standard...
    public Producto getProducto() {return producto;}

    public void setProducto(Producto producto) {this.producto = producto;}

    public int getCantidad() {return cantidad;}

    public void setCantidad(int cantidad) {this.cantidad = cantidad;}

    public TipoMovimiento getTipo() {return tipo;}

    public void setTipo(TipoMovimiento tipo) {this.tipo = tipo;}

    public LocalDate getFecha() {return fecha;}

    public void setFecha(LocalDate fecha) {this.fecha = fecha;}

    public boolean isStockYaActualizado() {return stockYaActualizado;}

    public void setStockYaActualizado(boolean stockYaActualizado) {this.stockYaActualizado = stockYaActualizado;}
}
