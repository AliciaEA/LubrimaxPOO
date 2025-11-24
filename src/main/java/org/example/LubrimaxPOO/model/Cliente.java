package org.example.LubrimaxPOO.model;
import java.time.*;
import javax.persistence.*;
import org.openxava.annotations.*;
import org.openxava.calculators.*;
import lombok.*;

@Entity
@Getter @Setter
@View(name="Simple", members="nombres, apellidos; cedula")
public class Cliente extends Persona
{
    @DefaultValueCalculator(CurrentLocalDateCalculator.class)
    private LocalDate fechaRegistro;
}
