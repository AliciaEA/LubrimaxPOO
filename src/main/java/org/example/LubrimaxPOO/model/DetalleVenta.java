package org.example.LubrimaxPOO.model;
import java.math.*;
import javax.persistence.*;
import javax.validation.*;
import org.openxava.annotations.*;
import lombok.*;

@Entity
@Table(name = "Detalle_Ventas")
@Getter @Setter
public class DetalleVenta
{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Hidden
    @Column(name = "id_detalle")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_venta")
    private Venta venta;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_producto")
    private Producto producto;

    @Column(name = "cantidad_ventas", nullable = false)
    private Integer cantidad;

    @Column(name = "precio_unitario_venta")
    private Double precioUnitarioVenta;
}
