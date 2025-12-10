package org.example.LubrimaxPOO.actions;

import org.example.LubrimaxPOO.models.*;
import org.openxava.actions.*;
import org.openxava.jpa.*;
import org.openxava.util.*;
import javax.persistence.*;
import javax.servlet.http.*; // Necesario para HttpSession

public class SeguridadModulosAction extends ViewBaseAction implements IForwardAction {

    private String forwardURI = null;

    @Override
    public void execute() throws Exception {
        String username = Users.getCurrent();

        // 1. Admin y sistema pasan siempre
        if (username == null || "admin".equals(username)) return;

        // 2. Buscar Empleado
        Empleado empleado = null;
        try {
            Query query = XPersistence.getManager()
                    .createQuery("FROM Empleado e WHERE e.username = :user");
            query.setParameter("user", username);
            empleado = (Empleado) query.getSingleResult();
        } catch (NoResultException e) {
            bloquearAcceso("Error crítico: Usuario sin perfil de empleado asignado.");
            return;
        }

        String moduloActual = getManager().getModuleName();
        Rol rol = empleado.getRol();
        boolean permitido = false;

        // 3. REGLAS DE ACCESO (Listas Blancas)
        if (moduloActual.equals("Inicio") || moduloActual.equals("SignIn")) {
            permitido = true;
        }
        else if (rol == Rol.VENDEDOR) {
            if (moduloActual.equals("Venta") ||
                    moduloActual.equals("Cliente") ||
                    moduloActual.equals("Producto")) {
                permitido = true;
            }
        }
        else if (rol == Rol.BODEGA) {
            if (moduloActual.equals("Compra") ||
                    moduloActual.equals("Movimiento") ||
                    moduloActual.equals("Producto") ||
                    moduloActual.equals("Proveedor") ||
                    moduloActual.equals("Categoria") ||
                    moduloActual.equals("MetodoPago")) {
                permitido = true;
            }
        }

        // 4. EJECUTAR BLOQUEO
        if (!permitido) {
            String mensaje = "⛔ ACCESO DENEGADO: Tu perfil de '" + rol +
                    "' no tiene autorización para acceder al módulo de '" + moduloActual + "'.";
            bloquearAcceso(mensaje);
        }
    }

    private void bloquearAcceso(String mensaje) {
        // 1. Guardamos el mensaje en la sesión (Buzón temporal)
        getRequest().getSession().setAttribute("LUBRIMAX_SECURITY_ERROR", mensaje);

        // 2. TRUCO DE ORO:
        // Añadimos "?action=DashboardController.refrescar" a la URL.
        // Esto obliga a OpenXava a ejecutar la acción 'refrescar' (que es la misma que Iniciar)
        // CADA VEZ que llegamos, leyendo así el mensaje de la sesión.
        long timestamp = System.currentTimeMillis();
        forwardURI = "/modules/Inicio?action=DashboardController.refrescar&t=" + timestamp;
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