package org.example.LubrimaxPOO.model;
import java.math.*;
import javax.persistence.*;
import org.openxava.annotations.*;
import lombok.*;

@Entity
@Table(name = "Detalle_Compras")
@Getter @Setter
public class DetalleCompra
{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Hidden
    @Column(name = "id_detalle_compra")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_compra")
    private Compra compra;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_producto")
    private Producto producto;

    @Column(name = "cantidad_compras", nullable = false)
    private Integer cantidad;

    @Column(name = "precio_unitario_compra")
    private Double precioUnitarioCompra;
}
