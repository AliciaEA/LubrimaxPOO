package org.example.LubrimaxPOO.actions;
import com.openxava.naviox.actions.ForwardToOriginalURIBaseAction;
import com.openxava.naviox.impl.SignInHelper;
import org.example.LubrimaxPOO.models.Empleado;
import org.openxava.jpa.XPersistence;
import org.openxava.util.Is;

import javax.persistence.Query;

public class DarAccesoAction extends ForwardToOriginalURIBaseAction
{
    public void execute() throws Exception {
        SignInHelper.initRequest(getRequest(), getView());

        if (getErrors().contains()) return;

        // --- INICIO DE LA LÓGICA DEL SUPER USUARIO ---
        // 1. Verificamos si la tabla está vacía
        Query queryConteo = XPersistence.getManager().createQuery("select count(e) from Empleado e");
        Long totalEmpleados = (Long) queryConteo.getSingleResult();

        if (totalEmpleados == 0) {
            // 2. Si está vacía, creamos el Super Admin
            Empleado admin = new Empleado();
            admin.setNombre("Super Administrador");
            admin.setUsername("admin");
            admin.setPassword("admin");

            // 3. Guardamos
            XPersistence.getManager().persist(admin);
            XPersistence.getManager().flush(); // Forzamos el guardado para que la siguiente consulta lo encuentre

            addMessage("Sistema inicializado: Se ha creado el usuario 'admin' con contraseña 'admin'.");
        }
        // --- FIN DE LA LÓGICA DEL SUPER USUARIO ---

        String userName = getView().getValueString("user");
        String password = getView().getValueString("password");

        if (Is.emptyString(userName, password)) {
            addError("unauthorized_user");
            return;
        }

        Long count = 0L;
        // Tu consulta original busca si el usuario y contraseña coinciden
        Query query = XPersistence.getManager().createQuery("select count(e) from Empleado e where e.username = :username and e.password = :password");
        query.setParameter("username", userName);
        query.setParameter("password", password);

        count = (Long) query.getSingleResult();

        if (count == 0L) {
            addError("unauthorized_user"); // Usuario o clave incorrectos
            return;
        }

        // Si pasó el filtro, iniciamos sesión en OpenXava
        SignInHelper.signIn(getRequest(), userName);
        getView().reset();
        getContext().resetAllModulesExceptCurrent(getRequest());
        forwardToOriginalURI();
    }

}
