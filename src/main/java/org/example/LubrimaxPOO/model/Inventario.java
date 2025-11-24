package org.example.LubrimaxPOO.model;
import java.time.*;
import javax.persistence.*;
import org.openxava.annotations.*;
import lombok.*;

@Entity
@Getter @Setter

public class Inventario
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Hidden
    private Integer id;

    @ManyToOne(fetch=FetchType.LAZY)
    @DescriptionsList(descriptionProperties="nombre")
    @Required
    private Producto producto;

    @Column(length=50)
    private String lote;

    @Required
    private int cantidadActual;

    private int stockMinimo;

    private LocalDate fechaUltMovimiento;

    @Stereotype("MEMO")
    private String observaciones;
}
