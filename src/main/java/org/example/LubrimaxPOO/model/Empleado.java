package org.example.LubrimaxPOO.model;
import java.time.*;
import javax.persistence.*;
import org.openxava.annotations.*;
import lombok.*;

@Entity
@Table(name = "Empleados")
@Getter @Setter
public class Empleado extends Persona
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Hidden
    @Column(name = "id_empleado")
    private Long id;

    @Required
    @Column(name = "fecha_contratacion")
    private LocalDate fechaContratacion;
}
