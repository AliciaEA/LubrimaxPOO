package org.example.LubrimaxPOO.model;
import java.math.*;
import javax.persistence.*;
import org.openxava.annotations.*;
import lombok.*;

@Entity
@Table(name = "Productos")
@Getter @Setter
public class Producto
{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Hidden
    @Column(name = "id_producto")
    private Long id;

    @Column(name = "codigo_barras", length = 100)
    private String codigoBarras;

    @Column(name = "nombre", length = 100) @Required
    private String nombre;

    @Stereotype("MEMO")
    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "precio_compra")
    private Double precioCompra;

    @Column(name = "precio_venta")
    private Double precioVenta;

    @Column(name = "stock_actual")
    private Integer stockActual = 0;

    @ManyToOne @JoinColumn(name = "id_categoria")
    private Categoria categoria;

    @ManyToOne @JoinColumn(name = "id_proveedor")
    private Proveedor proveedor;
}
