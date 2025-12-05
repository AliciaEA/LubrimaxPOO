package org.example.LubrimaxPOO.model;
import javax.persistence.*;
import org.openxava.annotations.*;
import lombok.*;

@Entity
@Table(name = "Proveedores")
@Getter @Setter
public class Proveedor
{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Hidden
    @Column(name = "id_proveedor")
    private Long id;

    @Column(name = "nombre_empresa", length = 150) @Required
    private String nombreEmpresa;
    @Column(name = "nombre_contacto", length = 100)
    private String nombreContacto;

    @Column(name = "telefono", length = 50)
    private String telefono;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "direccion", length = 255)
    private String direccion;
}
