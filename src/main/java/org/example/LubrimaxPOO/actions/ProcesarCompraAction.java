package org.example.LubrimaxPOO.actions;
import org.openxava.actions.*;
import org.openxava.jpa.*;
import org.example.LubrimaxPOO.models.*;
import org.openxava.model.MapFacade;

import javax.persistence.*;

public class ProcesarCompraAction extends ViewBaseAction
{
    public void execute() throws Exception
    {
        Compra compra = (Compra) MapFacade.findEntity("Compra", getView().getKeyValues());
        if (compra.isProcesada())
        {
            addError("Compra ya procesada");
            return;
        }

        EntityManager em = XPersistence.getManager();
        for (DetalleCompra d : compra.getDetalles())
        {
            Producto p = d.getProducto();
            p.setStock(p.getStock() + d.getCantidad());

            Movimiento mov = new Movimiento();
            mov.setProducto(p);
            mov.setCantidad(d.getCantidad());
            mov.setTipo(TipoMovimiento.ENTRADA_COMPRA);
            mov.setFecha(compra.getFecha());
            mov.setStockYaActualizado(true);
            em.persist(mov);
        }
        compra.setProcesada(true);
        em.merge(compra);
        addMessage("Compra Procesada");
        getView().refresh();
    }
}
