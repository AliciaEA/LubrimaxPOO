# 📦 Lubrimax

Este proyecto es una aplicación empresarial desarrollada en **Java** utilizando el framework **OpenXava**. Implementa un sistema completo de gestión de inventario automatizado, donde las transacciones de compra y venta afectan el stock en tiempo real mediante lógica de negocio encapsulada.

## 🚀 Características Principales

### 🛒 Gestión Comercial
* **Ventas:** Facturación a clientes con cálculo automático de subtotales y totales.
* **Compras:** Registro de entrada de mercancía de proveedores.
* **Validación de Stock:** El sistema impide realizar una venta si no hay suficiente stock disponible.

### 📦 Inventario Automatizado
* **Entradas Automáticas:** Al registrar una compra, el stock del producto aumenta automáticamente.
* **Salidas Automáticas:** Al registrar una venta, el stock del producto disminuye automáticamente.
* **Reversión Inteligente:** Si se elimina una venta o compra por error, el stock se corrige automáticamente (se devuelve o se resta).

### 👥 Gestión de Terceros
* **Herencia de Personas:** Implementación de Clientes y Empleados compartiendo datos comunes (Clase Padre `Persona`).
* **Maestros:** Gestión de Proveedores, Categorías y Métodos de Pago.

---

## Presentacion CANVAS

https://www.canva.com/design/DAGzzY0n0CI/PmaXXzApo_RPZ3vcpkG0IQ/edit?utm_content=DAGzzY0n0CI&utm_campaign=designshare&utm_medium=link2&utm_source=sharebutton

---
## 🛠️ Diagrama UML

https://online.visual-paradigm.com/share.jsp?id=343234323930322d31

### Modelo de Datos (UML)
* **IDs:** Se utilizan identificadores numéricos auto-incrementables (`Integer`) para todas las entidades.
* **Composición (♦):** Las Ventas y Compras tienen una relación de vida fuerte con sus Detalles.
* **Agregación (◇):** Las Categorías y Proveedores agrupan a los Productos.
* **Herencia (△):** `Cliente` y `Empleado` extienden de la superclase `Persona`.

### Lógica de Negocio (JPA Callbacks)
En lugar de usar Servicios externos, la lógica reside dentro de las entidades para garantizar la integridad de los datos:
* `@PrePersist`: Valida que `stockActual >= cantidad` antes de vender.
* `@PostPersist`: Actualiza el acumulado de stock tras guardar.
* `@PostRemove`: Revierte el movimiento de stock si se borra el registro.

---

---

## 🔧 Configuraciones

* Java JDK 17 
* Base de Datos (PostgreSQL).


## 📝 Notas de Uso

* **Primer paso:** Debes crear al menos una **Categoría** y un **Proveedor** antes de crear **Productos**.
* **Stock Inicial:** Los productos nacen con Stock 0. Debes registrar una **Compra** para darles entrada antes de intentar venderlos.
