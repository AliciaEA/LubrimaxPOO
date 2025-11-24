package org.example.LubrimaxPOO.model;
import javax.persistence.*;
import org.openxava.annotations.*;
import lombok.*;

@Entity
@Getter @Setter
public class MetodoPago
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Hidden
    private Integer id;

    @Column(length=50)
    @Required
    private String nombre;
}
