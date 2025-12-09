package org.example.LubrimaxPOO.model;
import java.time.*;
import javax.persistence.*;
import org.openxava.annotations.*;
import lombok.*;

@Entity
@Table(name = "Movimientos")
@Getter @Setter
public class Movimiento
{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Hidden
    @Column(name = "id_movimiento")
    private Long id;

    @Column(name = "fecha_movimiento", nullable = false)
    private LocalDateTime fechaMovimiento = LocalDateTime.now();

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_producto")
    private Producto producto;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_movimiento", length = 20, nullable = false)
    private MovimientoTipo tipo;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "referencia", length = 255)
    private String referencia;

    // Relación con Venta (opcional)
    @ManyToOne
    @JoinColumn(name = "id_venta")
    private Venta venta;

    // Relación con Compra (opcional)
    @ManyToOne
    @JoinColumn(name = "id_compra")
    private Compra compra;
}
