package org.example.LubrimaxPOO.actions;

import org.openxava.actions.*;
import org.example.LubrimaxPOO.models.*;
import javax.servlet.http.*;

public class IniciarDashboardAction extends ViewBaseAction {

    @Override
    public void execute() throws Exception {
        // 1. REVISAR BUZÓN DE ERRORES DE SEGURIDAD
        HttpSession session = getRequest().getSession();
        String errorSeguridad = (String) session.getAttribute("LUBRIMAX_SECURITY_ERROR");

        if (errorSeguridad != null) {
            // Mostramos el error
            addError(errorSeguridad);

            // IMPORTANTE: Borramos el mensaje de la sesión para que no siga saliendo eternamente
            session.removeAttribute("LUBRIMAX_SECURITY_ERROR");
        }

        // 2. Cargar lógica normal del Dashboard
        Dashboard dashboard = new Dashboard();
        dashboard.calcularIndicadores();

        getView().setModel(dashboard);
        getView().setEditable(false);
    }
}