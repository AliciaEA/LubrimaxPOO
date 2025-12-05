package org.example.LubrimaxPOO.model;
import javax.persistence.*;
import org.openxava.annotations.*;
import lombok.*;

@MappedSuperclass
@Getter @Setter
public abstract class Persona
{
    @Column(name = "nombres", length = 100) @Required
    private String nombres;

    @Column(name = "apellidos", length = 100) @Required
    private String apellidos;

    @Column(name = "cedula", length = 50) @Required
    private String cedula;

    @Column(name = "email", length = 100)
    private String email;
}
