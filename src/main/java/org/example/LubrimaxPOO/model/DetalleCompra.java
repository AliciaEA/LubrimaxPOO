package org.example.LubrimaxPOO.model;
import java.time.*;
import javax.persistence.*;
import org.openxava.annotations.*;
import lombok.*;

@Entity
@Table(name = "Detalle_Compras")
@Getter @Setter
public class DetalleCompra
{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Hidden
    @Column(name = "id_detalle_compra")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_compra")
    private Compra compra;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_producto")
    private Producto producto;

    @Column(name = "cantidad_compras", nullable = false)
    private Integer cantidad;

    @Column(name = "precio_unitario_compra")
    private Double precioUnitarioCompra;

   
    @PrePersist
    private void actualizarStockAlGuardar() {
        if (producto != null && cantidad != null && cantidad > 0) {
            int stockActual = producto.getStockActual() == null ? 0 : producto.getStockActual();
            producto.setStockActual(stockActual + cantidad);

            // Crear y asociar el movimiento de entrada
            if (compra != null) {
                Movimiento m = new Movimiento();
                m.setProducto(producto);
                m.setTipo(MovimientoTipo.ENTRADA);
                m.setCantidad(cantidad);
                m.setReferencia("Compra id: " + (compra.getId() == null ? "(nueva)" : compra.getId()));
                m.setFechaMovimiento(LocalDateTime.now());
                m.setCompra(compra);
                if (compra.getMovimientos() == null) compra.setMovimientos(new java.util.ArrayList<>());
                compra.getMovimientos().add(m);
            }
        }
    }

    @PreRemove
    private void revertirStockAlEliminar() {
        if (producto != null && cantidad != null && cantidad > 0) {
            int stockActual = producto.getStockActual() == null ? 0 : producto.getStockActual();
            int nuevoStock = stockActual - cantidad;
            if (nuevoStock < 0) nuevoStock = 0; 
            producto.setStockActual(nuevoStock);
        }
    }
}
