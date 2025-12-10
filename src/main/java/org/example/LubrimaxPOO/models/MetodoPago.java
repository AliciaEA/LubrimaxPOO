package org.example.LubrimaxPOO.models;

import javax.persistence.Entity;
import javax.persistence.*;
import org.openxava.annotations.*;
import org.openxava.model.Identifiable;


@Entity
public class MetodoPago extends Identifiable
{
    @Column(length = 30)
    @Required
    private String nombre; // Efectivo, Tarjeta, Transferencia

    public String getNombre() {return nombre;}

    public void setNombre(String nombre) {this.nombre = nombre;}
}
