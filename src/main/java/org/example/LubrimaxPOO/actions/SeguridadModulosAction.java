package org.example.LubrimaxPOO.actions;

import org.example.LubrimaxPOO.models.*;
import org.openxava.actions.*; // Importante para IForwardAction
import org.openxava.jpa.*;
import org.openxava.util.*;
import javax.persistence.*;

// CAMBIO: Implementamos IForwardAction para redirección HTTP dura
public class SeguridadModulosAction extends ViewBaseAction implements IForwardAction {

    private String forwardURI = null;

    @Override
    public void execute() throws Exception {
        String username = Users.getCurrent();
        if (username == null || "admin".equals(username)) return;

        Empleado empleado = null;
        try {
            Query query = XPersistence.getManager()
                    .createQuery("FROM Empleado e WHERE e.username = :user");
            query.setParameter("user", username);
            empleado = (Empleado) query.getSingleResult();
        } catch (NoResultException e) {
            return;
        }

        String moduloActual = getManager().getModuleName();
        Rol rol = empleado.getRol();
        String destino = null;

        // Reglas
        if (rol == Rol.VENDEDOR) {
            if (moduloActual.equals("Compra") || moduloActual.equals("Empleado") ||
                    moduloActual.equals("Movimiento") || moduloActual.equals("Proveedor")) {
                destino = "Venta";
            }
        }
        else if (rol == Rol.BODEGA) {
            if (moduloActual.equals("Venta") || moduloActual.equals("Cliente") ||
                    moduloActual.equals("Empleado") || moduloActual.equals("MetodoPago")) {
                destino = "Compra";
            }
        }

        // Ejecutar Redirección si hubo violación
        if (destino != null) {
            addError("Acceso denegado: No tienes permiso para ver " + moduloActual);
            // Redirección HTTP forzada al módulo permitido
            // La URL suele ser /NombreProyecto/modules/NombreModulo
            forwardURI = "/modules/" + destino;
        }
    }

    @Override
    public String getForwardURI() {
        return forwardURI;
    }

    @Override
    public boolean inNewWindow() {
        return false;
    }
}