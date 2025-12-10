package org.example.LubrimaxPOO.calculators;
import org.openxava.calculators.*;
import org.openxava.jpa.*;
import org.openxava.util.*;
import javax.persistence.*;

public class CurrentUserEmpleadoCalculator implements ICalculator
{
    public Object calculate() throws Exception {
        String loginActual = Users.getCurrent(); // El usuario logueado en la web (ej: "admin")

        // Buscamos un Empleado cuyo campo 'usuario' coincida con el login actual
        Query query = XPersistence.getManager()
                .createQuery("from Empleado e where e.usuario = :loginActual");
        query.setParameter("loginActual", loginActual);

        try {
            return query.getSingleResult();
        } catch (Exception e) {
            return null; // Si no hay empleado con ese usuario
        }
    }
}
