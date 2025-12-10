package org.example.LubrimaxPOO.models;
import javax.persistence.*;
import org.openxava.annotations.*;
import org.openxava.calculators.CurrentLocalDateCalculator;
import org.openxava.model.*;
import java.util.*;
import java.math.*;
import java.time.*;
import org.example.LubrimaxPOO.calculators.*;

@Entity
@View(members = "Encabezado { fecha, empleado, procesada; proveedor } Detalles { detalles } Totales { total }")
public class Compra extends Identifiable
{
    @DefaultValueCalculator(CurrentLocalDateCalculator.class)
    private LocalDate fecha;


    @ManyToOne(fetch = FetchType.LAZY)
    @DescriptionsList
    private Proveedor proveedor;

    @ManyToOne(fetch = FetchType.LAZY)
    @ReadOnly
    @DefaultValueCalculator(CurrentUserEmpleadoCalculator.class)
    private Empleado empleado;

    @ElementCollection
    @ListProperties("producto.nombre, cantidad, costo, importe")
    private Collection<DetalleCompra> detalles;

    @ReadOnly
    private boolean procesada;

    @Stereotype("MONEY")
    @Depends("detalles.cantidad, detalles.costo")
    public BigDecimal getTotal()
    {
        BigDecimal t = BigDecimal.ZERO;
        if (detalles != null) {for (DetalleCompra d : detalles) t = t.add(d.getImporte());}
        return t;
    }

    // Getters y Setters
    public LocalDate getFecha() {return fecha;}

    public void setFecha(LocalDate fecha) {this.fecha = fecha;}

    public Proveedor getProveedor() {return proveedor;}

    public void setProveedor(Proveedor proveedor) {this.proveedor = proveedor;}

    public Empleado getEmpleado() {return empleado;}

    public void setEmpleado(Empleado empleado) {this.empleado = empleado;}

    public Collection<DetalleCompra> getDetalles() {return detalles;}

    public void setDetalles(Collection<DetalleCompra> detalles) {this.detalles = detalles;}

    public boolean isProcesada() {return procesada;}

    public void setProcesada(boolean procesada) {this.procesada = procesada;}
}
