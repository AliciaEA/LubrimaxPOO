package org.example.LubrimaxPOO.models;
import javax.persistence.*;
import org.openxava.annotations.*;
import org.openxava.model.*;

@Entity
public class Cliente extends Identifiable
{
    @Column(length = 50)
    @Required
    private String nombre;

    @Column(length = 20)
    private String cedula;

    public String getNombre() {return nombre;}

    public void setNombre(String nombre) {this.nombre = nombre;}

    public String getCedula() {return cedula;}

    public void setCedula(String cedula) {this.cedula = cedula;}
}
