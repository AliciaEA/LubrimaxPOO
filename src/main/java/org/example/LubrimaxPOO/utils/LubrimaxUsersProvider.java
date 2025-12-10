package org.example.LubrimaxPOO.utils;

import org.example.LubrimaxPOO.models.Empleado;
import org.openxava.jpa.XPersistence;
import org.openxava.util.Users;

import javax.persistence.NoResultException;
import javax.persistence.Query;

public class LubrimaxUsersProvider {

    /**
     * Valida usuario y contraseña contra la entidad Empleado.
     * Devuelve true si el login es correcto, false en caso contrario.
     * Además, si es correcto, llama a Users.setCurrent(userName).
     */
    public static boolean login(String userName, String password) {
        // Super usuario por defecto para desarrollo
        if ("admin".equals(userName) && "admin".equals(password)) {
            Users.setCurrent("admin");
            return true;
        }

        try {
            Query query = XPersistence.getManager().createQuery(
                    "from Empleado e where e.username = :username and e.password = :password");
            query.setParameter("username", userName);
            query.setParameter("password", password);

            Empleado empleado = (Empleado) query.getSingleResult();

            // Configuramos el usuario actual en el contexto de OpenXava
            Users.setCurrent(empleado.getUsername());
            return true;
        }
        catch (NoResultException ex) {
            return false;
        }
    }
}
