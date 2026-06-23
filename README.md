# Shop - Order Management API

Pequeña API REST de gestión de pedidos construida con Spring Boot, Spring Data JPA y H2 en memoria.

## Requisitos

- Java 17
- Maven 3.8+

## Cómo arrancar

```bash
mvn spring-boot:run
```

La aplicación arranca en `http://localhost:8080`.

Consola H2 disponible en `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:shopdb`, usuario `sa`, sin contraseña).

Al arrancar se cargan algunos datos de ejemplo (2 productos y 1 cliente).

## Endpoints principales

### Productos
- `GET /products/all` - lista todos los productos
- `GET /products/{id}` - obtiene un producto
- `POST /products/save` - crea o actualiza un producto
- `GET /products/applyDiscount?id=1&percent=10` - aplica un descuento

### Clientes
- `GET /customers/list` - lista clientes
- `POST /customers/register` - registra un cliente
- `POST /customers/login?email=...&password=...` - login
- `GET /customers/admin?key=...` - listado de administración

### Pedidos
- `POST /api/createOrder` - crea un pedido
- `GET /api/getOrdersByStatus?status=NEW` - pedidos por estado
- `GET /api/searchOrders?discountCode=VIP` - busca pedidos por código de descuento
- `GET /api/payOrder/{id}` - paga un pedido
- `GET /api/deleteOrder/{id}` - elimina un pedido

### Ejemplo de creación de pedido

```bash
curl -X POST http://localhost:8080/api/createOrder \
  -H "Content-Type: application/json" \
  -d '{
        "customerId": 1,
        "productIds": [1, 2],
        "quantities": [2, 1],
        "discountCode": "BLACKFRIDAY"
      }'
```

## Tarea

Revisa el código del proyecto como si fuera una Pull Request. Identifica los problemas
que encuentres y explica cómo los mejorarías.
