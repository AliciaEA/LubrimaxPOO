package org.example.LubrimaxPOO.model;
import java.time.*;
import javax.persistence.*;
import org.openxava.annotations.*;
import org.openxava.calculators.*;
import lombok.*;

@Entity
@Table(name = "Clientes")
@Getter @Setter
@View(name = "Simple", members = "nombres, apellidos; cedula")
public class Cliente extends Persona
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Hidden
    @Column(name = "id_cliente")
    private Long id;

    @Column(name = "telefono", length = 50)
    private String telefono;

    @Column(name = "fecha_registro")
    @DefaultValueCalculator(CurrentLocalDateCalculator.class)
    private LocalDate fechaRegistro;
}
