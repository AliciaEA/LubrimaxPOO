package org.example.LubrimaxPOO.model;
import java.time.*;
import javax.persistence.*;
import javax.validation.*;
import org.openxava.annotations.*;
import lombok.*;

@Entity
@Table(name = "Detalle_Ventas")
@EntityListeners(org.example.LubrimaxPOO.listeners.DetalleVentaListener.class)
@Getter @Setter
public class DetalleVenta
{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Hidden
    @Column(name = "id_detalle")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_venta")
    private Venta venta;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_producto")
    private Producto producto;

    @Column(name = "cantidad_ventas", nullable = false)
    private Integer cantidad;

    @Column(name = "precio_unitario_venta")
    private Double precioUnitarioVenta;

    @PrePersist
    private void actualizarStockAlGuardar() {
        if (producto != null && cantidad != null && cantidad > 0) {
            int stockActual = producto.getStockActual() == null ? 0 : producto.getStockActual();
            if (stockActual < cantidad) {
                throw new IllegalStateException(
                    "Stock insuficiente para el producto: " + producto.getNombre() + 
                    ". Stock disponible: " + stockActual + 
                    ", cantidad solicitada: " + cantidad
                );
            }
            producto.setStockActual(stockActual - cantidad);
        }
    }

    @PreRemove
    private void revertirStockAlEliminar() {
        if (producto != null && cantidad != null && cantidad > 0) {
            int stockActual = producto.getStockActual() == null ? 0 : producto.getStockActual();
            producto.setStockActual(stockActual + cantidad);
        }
    }
}
