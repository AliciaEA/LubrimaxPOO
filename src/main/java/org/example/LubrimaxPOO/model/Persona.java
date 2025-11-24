package org.example.LubrimaxPOO.model;
import javax.persistence.*;
import org.openxava.annotations.*;
import lombok.*;

@MappedSuperclass
@Getter @Setter
public class Persona
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incrementable (1, 2, 3...)
    @Hidden
    private Integer id;

    @Column(length=50) @Required
    private String nombres;

    @Column(length=50) @Required
    private String apellidos;

    @Column(length=20) @Required
    private String cedula;

    @Column(length=20)
    private String telefono;

    @Stereotype("EMAIL")
    private String email;

    public String getNombreCompleto() {
        return (nombres != null ? nombres : "") + " " + (apellidos != null ? apellidos : "");
    }
}
