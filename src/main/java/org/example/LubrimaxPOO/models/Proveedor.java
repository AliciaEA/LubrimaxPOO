package org.example.LubrimaxPOO.models;
import javax.persistence.*;
import org.openxava.annotations.*;
import org.openxava.model.*;

@Entity
public class Proveedor extends Identifiable
{
    @Column(length = 50)
    @Required
    private String nombre;

    @Column(length = 20)
    private String ruc;

    public String getNombre() {return nombre;}

    public void setNombre(String nombre) {this.nombre = nombre;}

    public String getRuc() {return ruc;}

    public void setRuc(String ruc) {this.ruc = ruc;}
}
