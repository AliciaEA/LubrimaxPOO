package org.example.LubrimaxPOO.models;
import javax.persistence.*;
import org.openxava.annotations.*;
import org.openxava.model.*;

@Entity
public class Empleado extends Identifiable
{
    @Column(length=50)
    @Required
    private String nombre;

    @Column(length=30)
    @Required
    // Este nombre debe coincidir con el que usas para entrar al sistema (ej: "admin", "juan")
    private String username;

    @Column(length=30)
    @Stereotype("PASSWORD") // Para que se vean asteriscos ****
    private String password;

    @Enumerated(EnumType.STRING)
    @Required
    private Rol rol;

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getUsername() { return username; }
    public void setUsername(String usuario) { this.username = usuario; }
    public String getPassword() { return password; }
    public void setPassword(String contrasena) { this.password = contrasena; }
    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }
}
