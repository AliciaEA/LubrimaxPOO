package org.example.LubrimaxPOO.model;
import java.math.*;
import javax.persistence.*;
import javax.validation.*;
import org.openxava.annotations.*;
import lombok.*;

@Entity
@Getter @Setter
public class DetalleVenta
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID Numérico
    @Hidden
    private Integer id;

    @ManyToOne
    private Venta venta;

    @ManyToOne(fetch=FetchType.LAZY)
    @ReferenceView("Simple")
    @Required
    private Producto producto;

    @Required
    private int cantidad;

    @Stereotype("MONEY")
    private BigDecimal precioUnitario;

    @Stereotype("MONEY")
    @Depends("cantidad, precioUnitario")
    public BigDecimal getSubtotal() {
        if (precioUnitario == null) return BigDecimal.ZERO;
        return precioUnitario.multiply(new BigDecimal(cantidad));
    }

    // --- LÓGICA STOCK ---
    @PrePersist
    public void validarStock() {
        if (producto != null && producto.getStockActual() < this.cantidad) {
            throw new ValidationException("Stock insuficiente. Disponible: " + producto.getStockActual());
        }
    }

    @PostPersist
    public void onCreate() {
        if (producto != null) producto.disminuirStock(cantidad);
    }

    @PostRemove
    public void onDelete() {
        if (producto != null) producto.aumentarStock(cantidad);
    }
}
