package org.example.LubrimaxPOO.model;
import java.time.*;
import java.math.*;
import java.util.*;
import javax.persistence.*;
import org.openxava.annotations.*;
import org.openxava.calculators.*;
import lombok.*;

@Entity
@Getter @Setter
@View(members="fechaCompra, proveedor; detalles { detalles }; totalCompra")
public class Compra
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID Numérico
    @Hidden
    private Integer id;

    @DefaultValueCalculator(CurrentLocalDateCalculator.class)
    private LocalDate fechaCompra;

    @ManyToOne(fetch = FetchType.LAZY)
    @DescriptionsList(descriptionProperties = "nombreEmpresa")
    private Proveedor proveedor;

    @OneToMany(mappedBy="compra", cascade=CascadeType.ALL, orphanRemoval=true)
    @ListProperties("producto.codigoBarras, producto.nombre, cantidad, precioUnitario, subtotal")
    private Collection<DetalleCompra> detalles;

    @Stereotype("MONEY")
    @ReadOnly
    public BigDecimal getTotalCompra() {
        BigDecimal total = BigDecimal.ZERO;
        if (detalles == null) return total;
        for (DetalleCompra d : detalles) {
            total = total.add(d.getSubtotal());
        }
        return total;
    }
}
