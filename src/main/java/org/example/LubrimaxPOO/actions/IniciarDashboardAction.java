package org.example.LubrimaxPOO.actions;

import org.openxava.actions.*;
import org.example.LubrimaxPOO.models.*;

public class IniciarDashboardAction extends ViewBaseAction {

    @Override
    public void execute() throws Exception {
        // Creamos una instancia del dashboard en memoria
        Dashboard dashboard = new Dashboard();

        // Calculamos los datos reales
        dashboard.calcularIndicadores();

        // Pasamos los datos a la vista
        getView().setModel(dashboard);
        getView().setEditable(false); // Para que parezca solo lectura
    }
}