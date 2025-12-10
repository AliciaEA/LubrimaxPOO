package org.example.LubrimaxPOO.models;
import javax.persistence.*;
import org.openxava.annotations.*;
import org.openxava.calculators.CurrentLocalDateCalculator;
import org.openxava.model.*;
import java.util.*;
import java.math.*;
import org.example.LubrimaxPOO.calculators.*;
import java.time.*;

import org.openxava.model.Identifiable;

@Entity
@Tab(name="Todas", properties="fecha, numero, cliente.nombre, total, procesada, empleado.nombre")
// OJO AL CAMBIO AQUI: ${empleado.usuario} ahora es texto simple
@Tab(name="MisVentas", properties="fecha, numero, cliente.nombre, total, procesada",
        baseCondition="${empleado.usuario} = '${user.name}'")
@View(members="Encabezado { numero, fecha, empleado, procesada; cliente, metodoPago } Detalles { detalles } Totales { total }")
public class Venta extends Identifiable
{
    @Column(length=10) private String numero;
    @DefaultValueCalculator(CurrentLocalDateCalculator.class) private LocalDate fecha;

    @ManyToOne(fetch=FetchType.LAZY) @DescriptionsList private Cliente cliente;
    @ManyToOne(fetch=FetchType.LAZY) @DescriptionsList private MetodoPago metodoPago;

    @ManyToOne(fetch=FetchType.LAZY)
    @ReadOnly // Se llena solo con el calculador
    @DefaultValueCalculator(CurrentUserEmpleadoCalculator.class)
    private Empleado empleado;

    @ElementCollection
    @ListProperties("producto.nombre, cantidad, precio, importe")
    private Collection<DetalleVenta> detalles;

    @ReadOnly private boolean procesada;

    @Stereotype("MONEY")
    @Depends("detalles.cantidad, detalles.costo")
    public BigDecimal getTotal() {
        BigDecimal t = BigDecimal.ZERO;
        if (detalles != null) {
            for (DetalleVenta d : detalles) t = t.add(d.getImporte());
        }
        return t;
    }
    // Getters y Setters...
    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public MetodoPago getMetodoPago() { return metodoPago; }
    public void setMetodoPago(MetodoPago metodoPago) { this.metodoPago = metodoPago; }
    public Empleado getEmpleado() { return empleado; }
    public void setEmpleado(Empleado empleado) { this.empleado = empleado; }
    public Collection<DetalleVenta> getDetalles() { return detalles; }
    public void setDetalles(Collection<DetalleVenta> detalles) { this.detalles = detalles; }
    public boolean isProcesada() { return procesada; }
    public void setProcesada(boolean procesada) { this.procesada = procesada; }
}
