package org.example.LubrimaxPOO.model;
import java.time.*;
import java.util.*;
import javax.persistence.*;
import org.openxava.annotations.*;
import lombok.*;

@Entity
@Table(name = "Ventas")
@Getter @Setter
public class Venta
{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Hidden
    @Column(name = "id_venta")
    private Long id;

    @Column(name = "fecha_hora")
    private LocalDateTime fechaHora = LocalDateTime.now();

    @Column(name = "total_venta")
    private Double totalVenta;

    @ManyToOne @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @ManyToOne @JoinColumn(name = "id_empleado")
    private Empleado empleado;

    @ManyToOne @JoinColumn(name = "id_metodo_pago")
    private MetodoPago metodoPago;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleVenta> detalles;

    // Movimientos asociados a esta venta
    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Movimiento> movimientos = new ArrayList<>();
}
