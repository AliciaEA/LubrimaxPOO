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
@View(members="fechaHora, cliente, empleado; detalles { detalles }; totalVenta")
public class Venta
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID Numérico
    @Hidden
    private Integer id;

    @DefaultValueCalculator(CurrentLocalDateTimeCalculator.class)
    private LocalDateTime fechaHora;

    @ManyToOne(fetch=FetchType.LAZY)
    @ReferenceView("Simple")
    @Required
    private Cliente cliente;

    @ManyToOne(fetch=FetchType.LAZY)
    @Required
    private Empleado empleado;

    @OneToMany(mappedBy="venta", cascade=CascadeType.ALL, orphanRemoval=true)
    @ListProperties("producto.codigoBarras, producto.nombre, cantidad, precioUnitario, subtotal")
    private Collection<DetalleVenta> detalles;

    @Stereotype("MONEY")
    @ReadOnly
    public BigDecimal getTotalVenta() {
        BigDecimal total = BigDecimal.ZERO;
        if (detalles == null) return total;
        for (DetalleVenta d : detalles) {
            total = total.add(d.getSubtotal());
        }
        return total;
    }
}
