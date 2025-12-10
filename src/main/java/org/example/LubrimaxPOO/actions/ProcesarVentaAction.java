package org.example.LubrimaxPOO.actions;
import org.openxava.actions.*;
import org.openxava.jpa.*;
import org.example.LubrimaxPOO.models.*;
import org.openxava.model.MapFacade;

import javax.persistence.*;

public class ProcesarVentaAction extends ViewBaseAction
{
    public void execute() throws Exception {
        Venta venta = (Venta) MapFacade.findEntity("Venta", getView().getKeyValues());
        if (venta.isProcesada()) { addError("Venta ya procesada"); return; }

        EntityManager em = XPersistence.getManager();
        for (DetalleVenta d : venta.getDetalles()) {
            Producto p = d.getProducto();
            if (p.getStock() < d.getCantidad()) { addError("Sin stock: " + p.getNombre()); return; }
            p.setStock(p.getStock() - d.getCantidad());

            Movimiento mov = new Movimiento();
            mov.setProducto(p); mov.setCantidad(d.getCantidad());
            mov.setTipo(TipoMovimiento.SALIDA_VENTA); mov.setFecha(venta.getFecha());
            mov.setStockYaActualizado(true);
            em.persist(mov);
        }
        venta.setProcesada(true);
        em.merge(venta);
        addMessage("Venta Procesada");
        getView().refresh();
    }
}
