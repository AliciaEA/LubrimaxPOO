package org.example.LubrimaxPOO.model;
import java.math.*;
import javax.persistence.*;
import org.openxava.annotations.*;
import lombok.*;

@Entity
@View(name = "Simple", members =
        "codigoBarras; " +
                "nombre; " +
                "precioCompra, precioVenta; " +
                "categoria; " +
                "stockActual")
@Getter @Setter
public class Producto
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID Numérico
    @Hidden
    private Integer id;

    @Column(length=20)
    private String codigoBarras;

    @Column(length=100) @Required
    private String nombre;

    @Stereotype("MONEY")
    private BigDecimal precioCompra;

    @Stereotype("MONEY")
    private BigDecimal precioVenta;

    @ReadOnly
    private int stockActual;

    @ManyToOne(fetch=FetchType.LAZY)
    @DescriptionsList(descriptionProperties="nombre")
    private Categoria categoria;

    // --- MÉTODOS ---
    public void aumentarStock(int cantidad) {
        this.stockActual += cantidad;
    }

    public void disminuirStock(int cantidad) {
        this.stockActual -= cantidad;
    }
}
