package org.example.LubrimaxPOO.model;
import java.math.*;
import javax.persistence.*;
import org.openxava.annotations.*;
import lombok.*;

@Entity
@Getter @Setter
public class DetalleCompra
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID Numérico
    @Hidden
    private Integer id;

    @ManyToOne
    private Compra compra;

    @ManyToOne(fetch=FetchType.LAZY)
    @ReferenceView("Simple")
    @Required
    private Producto producto;

    @Required
    private int cantidad;

    @Stereotype("MONEY")
    @Column(precision = 18, scale = 2)
    private BigDecimal precioUnitario;

    @Stereotype("MONEY")
    @Depends("cantidad, precioUnitario")
    public BigDecimal getSubtotal() {
        if (precioUnitario == null) return BigDecimal.ZERO;
        return precioUnitario.multiply(new BigDecimal(cantidad));
    }

    // --- LÓGICA STOCK ---
    @PostPersist
    public void onCreate() {
        if (producto != null) producto.aumentarStock(cantidad);
    }

    @PostRemove
    public void onDelete() {
        if (producto != null) producto.disminuirStock(cantidad);
    }
}
