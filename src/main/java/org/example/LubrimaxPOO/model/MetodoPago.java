package org.example.LubrimaxPOO.model;
import javax.persistence.*;
import org.openxava.annotations.*;
import lombok.*;

@Entity
@Table(name = "Metodos_Pago")
@Getter @Setter
public class MetodoPago
{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Hidden
    @Column(name = "id_metodo_pago")
    private Long id;

    @Column(name = "nombre", length = 100) @Required
    private String nombre;
}
