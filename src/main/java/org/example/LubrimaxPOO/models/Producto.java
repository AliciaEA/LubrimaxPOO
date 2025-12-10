package org.example.LubrimaxPOO.models;
import org.example.LubrimaxPOO.models.*;
import org.openxava.model.Identifiable;
import javax.persistence.*;
import org.openxava.annotations.*;
import org.openxava.model.*;
import java.math.*;

import java.math.*;

@Entity
public class Producto extends Identifiable
{
    @Column(length=50) @Required private String nombre;

    @ManyToOne(fetch=FetchType.LAZY) @DescriptionsList
    private Categoria categoria;

    @Stereotype("MONEY") private BigDecimal precio;

    @ReadOnly // Solo editable por movimientos
    private int stock;

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }
    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
}
