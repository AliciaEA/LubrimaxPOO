package org.example.LubrimaxPOO.model;
import java.time.*;
import javax.persistence.*;
import org.openxava.annotations.*;
import lombok.*;

@Entity
@Getter @Setter
public class Empleado extends Persona
{
    @Required
    private LocalDate fechaContratacion;

    @Column(length=50)
    private String puesto;
}
