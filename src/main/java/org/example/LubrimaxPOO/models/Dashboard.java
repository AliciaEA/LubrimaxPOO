package org.example.LubrimaxPOO.models;

import javax.persistence.*;
import org.openxava.annotations.*;
import org.openxava.jpa.*;
import java.math.*;
import java.time.*;
import java.util.*;
import lombok.*;

@View(members=
        "Indicadores Generales [" +
                "  totalVentasHoy, productosBajoStock, valorInventario" +
                "];"
)
@Getter @Setter
public class Dashboard {

    @Stereotype("MONEY")
    @LabelFormat(LabelFormatType.SMALL)
    private BigDecimal totalVentasHoy;

    @Stereotype("Int")
    @LabelFormat(LabelFormatType.SMALL)
    private int productosBajoStock;

    @Stereotype("MONEY")
    @LabelFormat(LabelFormatType.SMALL)
    private BigDecimal valorInventario;

    public void calcularIndicadores() {
        this.totalVentasHoy = BigDecimal.ZERO;
        this.productosBajoStock = 0;
        this.valorInventario = BigDecimal.ZERO;

        try {
            EntityManager em = XPersistence.getManager();

            // 1. CALCULAR VENTAS DE HOY
            TypedQuery<Venta> queryVentas = em.createQuery(
                    "SELECT v FROM Venta v WHERE v.fecha = :hoy AND v.procesada = true",
                    Venta.class
            );
            queryVentas.setParameter("hoy", LocalDate.now());
            List<Venta> ventasHoy = queryVentas.getResultList();

            for (Venta v : ventasHoy) {
                if (v.getTotal() != null) {
                    this.totalVentasHoy = this.totalVentasHoy.add(v.getTotal());
                }
            }

            // 2. y 3. CÁLCULOS DE PRODUCTO (Corregido con tus nombres reales)
            TypedQuery<Producto> queryProd = em.createQuery("SELECT p FROM Producto p", Producto.class);
            List<Producto> productos = queryProd.getResultList();

            for (Producto p : productos) {
                // CORRECCIÓN: Usamos p.getStock()
                int stock = p.getStock();

                // Contamos si el stock es bajo (menor o igual a 10)
                if (stock <= 10) {
                    this.productosBajoStock++;
                }

                // CORRECCIÓN: Usamos p.getPrecio() porque no tienes precioCompra
                BigDecimal precio = p.getPrecio() != null ? p.getPrecio() : BigDecimal.ZERO;

                BigDecimal valorProducto = precio.multiply(new BigDecimal(stock));
                this.valorInventario = this.valorInventario.add(valorProducto);
            }

        } catch (Exception e) {
            e.printStackTrace(); // Imprime el error en la consola si ocurre
        }
    }
}