package org.example.LubrimaxPOO.models;
import javax.persistence.*;
import org.openxava.annotations.*;
import org.openxava.model.*;
import com.openxava.naviox.model.*;
@Entity
public class Empleado extends Identifiable
{
    @Column(length=50)
    @Required
    private String nombre;

    @Column(length=30)
    @Required
    // Este nombre debe coincidir con el que usas para entrar al sistema (ej: "admin", "juan")
    private String usuario;

    @Column(length=30)
    @Stereotype("PASSWORD") // Para que se vean asteriscos ****
    private String contrasena;

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }
    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
}
