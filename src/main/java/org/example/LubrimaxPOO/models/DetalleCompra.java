package org.example.LubrimaxPOO.models;
import javax.persistence.*;
import org.openxava.annotations.*;
import java.math.*;
import org.example.LubrimaxPOO.models.*;

@Embeddable
public class DetalleCompra
{
    @ManyToOne(fetch=FetchType.LAZY) private Producto producto;
    private int cantidad;
    @Stereotype("MONEY") private BigDecimal costo;

    @Stereotype("MONEY") @Depends("costo, cantidad")
    public BigDecimal getImporte() { return (costo == null) ? BigDecimal.ZERO : costo.multiply(new BigDecimal(cantidad)); }

    // Getters y Setters
    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public BigDecimal getCosto() { return costo; }
    public void setCosto(BigDecimal costo) { this.costo = costo; }
}
