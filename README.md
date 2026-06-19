# tienda-instrumentos

Proyecto FullStack I — Tienda de instrumentos musicales con arquitectura de microservicios.

## Arquitectura

El sistema consta de **12 proyectos Spring Boot** independientes:

| Proyecto | Puerto | Eureka | Descripcion |
|----------|--------|--------|-------------|
| eureka-server | 8090 | — | Service discovery |
| api-gateway | 8080 | api-gateway | Punto unico de entrada + JWT |
| catalogo | 8081 | CATALOGO | Catalogo de instrumentos |
| inventario | 8082 | INVENTARIO | Stock por instrumento |
| compra | 8083 | COMPRA | Orquestacion de compras |
| pago | 8084 | PAGO | Procesamiento de pagos |
| reserva | 8085 | RESERVA | Reservas de instrumentos |
| postventa | 8086 | POSTVENTA | Tickets post-venta |
| despacho | 8088 | DESPACHO | Envios y despachos |
| usuario | 8089 | USUARIO | Usuarios, roles, JWT |
| cotizaciones | 8091 | COTIZACIONES | Cotizaciones con detalle |
| proyecto_reseña | 8092 | RESENAS | Resenas de productos |

Cada microservicio tiene su propia base de datos MySQL en `localhost:3308` (database per service).

## Requisitos

- Java 17
- MySQL en puerto 3308 (XAMPP / MySQL Workbench)
- Maven (incluido via `mvnw` en cada proyecto)

## Orden de ejecucion

1. **MySQL** — iniciar en puerto 3308
2. **Eureka Server** — `eureka-server` (puerto 8090)
3. **Microservicios** — los 10 servicios de negocio (cualquier orden)
4. **API Gateway** — `api-gateway` (puerto 8080, ultimo en iniciar)

Verificar Eureka: http://localhost:8090

## API Gateway

Todas las peticiones desde Postman deben ir al gateway en `http://localhost:8080`:

| Ruta Gateway | Microservicio |
|---|---|
| `/instrumentos/**` | CATALOGO |
| `/inventario/**` | INVENTARIO |
| `/compras/**` | COMPRA |
| `/pagos/**` | PAGO |
| `/reservas/**` | RESERVA |
| `/postventa/**` | POSTVENTA |
| `/despachos/**` | DESPACHO |
| `/usuarios/**` | USUARIO |
| `/cotizaciones/**` | COTIZACIONES |
| `/resenas/**` | RESENAS |

## Autenticacion JWT

1. **Registrar usuario** (publico):
   ```
   POST http://localhost:8080/usuarios
   Body: { "nombre": "Juan", "email": "juan@test.com", "pass": "123456", "telefono": "999999999" }
   ```

2. **Login** (publico):
   ```
   POST http://localhost:8080/usuarios/login
   Body: { "email": "juan@test.com", "pass": "123456" }
   ```
   Respuesta: token JWT

3. **Peticiones protegidas** — agregar header:
   ```
   Authorization: Bearer <token>
   ```

Rutas publicas (sin token): `POST /usuarios`, `POST /usuarios/login`, `GET /usuarios/validar/{id}`

## Swagger UI

Documentacion disponible en cada microservicio:

| Servicio | URL Swagger |
|----------|-------------|
| Catalogo | http://localhost:8081/swagger-ui/index.html |
| Inventario | http://localhost:8082/swagger-ui/index.html |
| Compra | http://localhost:8083/swagger-ui/index.html |
| Pago | http://localhost:8084/swagger-ui/index.html |
| Reserva | http://localhost:8085/swagger-ui/index.html |
| Postventa | http://localhost:8086/swagger-ui/index.html |
| Despacho | http://localhost:8088/swagger-ui/index.html |
| Usuario | http://localhost:8089/swagger-ui/index.html |
| Cotizaciones | http://localhost:8091/swagger-ui/index.html |
| Resenas | http://localhost:8092/swagger-ui/index.html |

## Pruebas unitarias

Cada microservicio tiene pruebas unitarias JUnit 5 + Mockito en la capa Service.

Ejecutar en cada carpeta:
```bash
./mvnw test
```

No requiere que los servicios esten corriendo.

## Flujo demo Postman (presentacion)

1. Crear instrumento en catalogo: `POST /instrumentos`
2. Crear inventario: `POST /inventario`
3. Crear usuario: `POST /usuarios`
4. Login: `POST /usuarios/login` → obtener token
5. Realizar compra: `POST /compras` (con Bearer token)
6. Verificar pago: `GET /pagos`
7. Crear despacho: `POST /despachos`
8. Crear ticket postventa: `POST /postventa`

## Comunicacion entre microservicios

Los servicios se comunican via `RestTemplate` (HTTP/REST sincrono):

- **compra** → catalogo, inventario, pago
- **pago** → compra
- **inventario, reserva** → catalogo (validar instrumento)
- **postventa, despacho** → compra, usuario

## Estructura por microservicio

```
src/main/java/
  ├── *Application.java
  ├── config/          (AppConfig, OpenApiConfig, SecurityConfig)
  ├── controller/      (REST API)
  ├── services/        (logica de negocio)
  ├── repository/      (JPA interfaces)
  └── model/           (entidades @Entity)
src/test/java/
  └── *ServiceTest.java (pruebas unitarias)
```
