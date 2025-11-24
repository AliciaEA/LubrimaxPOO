package org.example.LubrimaxPOO.model;
import javax.persistence.*;
import org.openxava.annotations.*;
import lombok.*;

@Entity
@View(name = "Simple", members = "nombre; telefono; direccion")
@Getter @Setter
public class Proveedor
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Hidden
    private Integer id;

    @Column(length=100)
    @Required
    private String nombreEmpresa;

    @Column(length=50)
    private String nombreContacto;

    @Column(length=20)
    private String telefono;

    @Stereotype("EMAIL")
    private String email;

    @Stereotype("ADDRESS")
    private String direccion;
}
