package org.example.LubrimaxPOO.calculators;

import org.openxava.calculators.ICalculator;
import org.openxava.jpa.XPersistence;
import javax.persistence.Query;
import java.util.List;

public class NumeroVentaCalculator implements ICalculator {
    
    public Object calculate() throws Exception {
        Query query = XPersistence.getManager()
            .createQuery("SELECT v.numero FROM Venta v WHERE v.numero IS NOT NULL");
        
        List<?> resultados = query.getResultList();
        
        if (resultados.isEmpty()) {
            return "1";
        }
        
        int maxNumero = 0;
        for (Object resultado : resultados) {
            if (resultado != null) {
                try {
                    String numeroStr = ((String) resultado).trim();
                    int numero = Integer.parseInt(numeroStr);
                    if (numero > maxNumero) {
                        maxNumero = numero;
                    }
                } catch (NumberFormatException e) {
                }
            }
        }
        
        return String.valueOf(maxNumero + 1);
    }
}

