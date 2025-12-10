package org.example.LubrimaxPOO.models;
import javax.persistence.*;
import org.openxava.annotations.*;
import java.math.*;

@Embeddable
public class DetalleVenta
{
    @ManyToOne(fetch=FetchType.LAZY) private Producto producto;
    private int cantidad;
    @Stereotype("MONEY") private BigDecimal precio;

    @Stereotype("MONEY") @Depends("precio, cantidad")
    public BigDecimal getImporte() {
        return (precio == null) ? BigDecimal.ZERO : precio.multiply(new BigDecimal(cantidad));
    }
    // Getters y Setters
    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }
}
