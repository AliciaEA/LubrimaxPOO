package org.example.LubrimaxPOO.model;
import javax.persistence.*;
import org.openxava.annotations.*;
import lombok.*;

@Entity
@Table(name = "Categorias")
@Getter @Setter

public class Categoria
{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Hidden
    @Column(name = "id_categoria")
    private Long id;

    @Column(name = "nombre", length = 50) @Required
    private String nombre;

    @Stereotype("MEMO")
    @Column(name = "descripcion")
    private String descripcion;
}
