package org.example.LubrimaxPOO.model;
import java.time.*;
import java.util.*;
import javax.persistence.*;
import org.openxava.annotations.*;
import lombok.*;

@Entity
@Table(name = "Compras")
@Getter @Setter
public class Compra
{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Hidden
    @Column(name = "id_compra")
    private Long id;

    @Column(name = "fecha_compra")
    private LocalDateTime fechaCompra = LocalDateTime.now();

    @Column(name = "total_compra")
    private Double totalCompra;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_proveedor")
    private Proveedor proveedor;

    @OneToMany(mappedBy = "compra", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleCompra> detalles;

    // Movimientos asociados a esta compra
    @OneToMany(mappedBy = "compra", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Movimiento> movimientos = new ArrayList<>();
}
