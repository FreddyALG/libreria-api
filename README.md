# API Librería – Spring Boot + Spring Data + MariaDB + Docker

**Autor:** Fredy Linares – Universidad Distrital Francisco José de Caldas
**Base de datos:** `libreria` (tablas `autores`, `editoriales`, `libros`)
**Red Docker:** `linares`

---

## 1. Estructura del proyecto

```
libreria-api/
├── pom.xml
├── Dockerfile
├── docker-compose.yml
├── db/libreria.sql                    ← script original entregado por el profesor
├── src/main/resources/data.sql        ← datos iniciales (INSERT IGNORE)
├── diagramas/
│   ├── diagrama_componentes.puml
│   └── diagrama_clases.puml
└── src/main/java/com/linares/libreria/
    ├── LibreriaApiApplication.java
    ├── config/        OpenApiConfig (personalización de Swagger)
    ├── controller/    AutorController, EditorialController, LibroController, HomeController
    ├── dto/           Request/Response DTO (records) + ErrorResponseDTO
    ├── exception/     GlobalExceptionHandler + excepciones propias
    ├── mapper/        AutorMapper, EditorialMapper, LibroMapper
    ├── persistence/   ← UNA sola carpeta de persistencia
    │   ├── entity/       AutorEntity, EditorialEntity, LibroEntity
    │   └── repository/   AutorRepository, EditorialRepository, LibroRepository
    └── service/       ICrudService, AutorService, EditorialService, LibroService
        └── impl/         AutorServiceImpl, EditorialServiceImpl, LibroServiceImpl
```

### Modelo de datos (libreria.sql)
- `autores (id_autor PK AI, nombre, pais)`
- `editoriales (id_editorial PK AI, nombre, pais)`
- `libros (id_libro PK AI, titulo, fecha_publicacion DATE, id_autor FK NULL, id_editorial FK NULL)`

Relaciones: un autor tiene muchos libros y una editorial tiene muchos libros (`@ManyToOne` en `LibroEntity`).
Las tres llaves primarias son AUTO_INCREMENT, así que el cliente **nunca** envía el id al crear.

---

## 2. Base de datos en Docker (red `linares`)

La base de datos **la crea la aplicación automáticamente**:
- `createDatabaseIfNotExist=true` en la URL crea la base `libreria`.
- `spring.jpa.hibernate.ddl-auto=update` crea las tablas a partir de las entidades.
- `data.sql` inserta los autores, editoriales y libros (con `INSERT IGNORE`, no se duplican al reiniciar).

Conexión: usuario `root` sin contraseña.

```bash
docker network create linares
cd ~/eclipse-workspace/libreria-api
docker compose up -d db-linares
```

Después de arrancar la API (Eclipse o Docker):
```bash
docker exec -it db-linares mariadb -uroot libreria -e "SHOW TABLES; SELECT COUNT(*) FROM libros;"
```

> Para reiniciar la BD desde cero: `docker compose down -v` y luego `docker compose up -d`.

---

## 3. Ejecutar desde Eclipse

1. `File → Import → Maven → Existing Maven Projects` → carpeta `libreria-api`.
2. Clic derecho → `Maven → Update Project…` (Alt+F5).
3. Abrir `LibreriaApiApplication.java` → `Run As → Java Application`.
4. Abrir **http://localhost:8080/swagger-ui.html**.

---

## 4. Dockerizar la API

Detén primero la app en Eclipse (botón cuadrado rojo de la consola).
```bash
docker compose up -d --build
docker compose ps
docker logs -f api-linares
docker network inspect linares
```

Detener: `docker compose stop` · Eliminar contenedores: `docker compose down` · Eliminar también los datos: `docker compose down -v`

---

## 5. Endpoints

| Método | Ruta | Descripción | Códigos |
|---|---|---|---|
| GET | `/api/autores` | Listar autores | 200 |
| GET | `/api/autores/{id}` | Buscar autor | 200, 404 |
| GET | `/api/autores/{id}/libros` | Libros del autor | 200, 404 |
| POST | `/api/autores` | Crear autor | 201, 400 |
| PUT | `/api/autores/{id}` | Actualizar autor | 200, 400, 404 |
| DELETE | `/api/autores/{id}` | Eliminar autor | 204, 404, 409 |
| GET | `/api/editoriales` | Listar editoriales | 200 |
| GET | `/api/editoriales/{id}` | Buscar editorial | 200, 404 |
| GET | `/api/editoriales/{id}/libros` | Libros de la editorial | 200, 404 |
| POST | `/api/editoriales` | Crear editorial | 201, 400 |
| PUT | `/api/editoriales/{id}` | Actualizar editorial | 200, 400, 404 |
| DELETE | `/api/editoriales/{id}` | Eliminar editorial | 204, 404, 409 |
| GET | `/api/libros` | Listar libros | 200 |
| GET | `/api/libros/buscar?titulo=` | Buscar por título | 200, 400 |
| GET | `/api/libros/{id}` | Buscar libro | 200, 404 |
| POST | `/api/libros` | Crear libro | 201, 400, 404 |
| PUT | `/api/libros/{id}` | Actualizar libro | 200, 400, 404 |
| DELETE | `/api/libros/{id}` | Eliminar libro | 204, 404 |

Ejemplos con curl:
```bash
curl http://localhost:8080/api/libros
curl "http://localhost:8080/api/libros/buscar?titulo=soledad"
curl http://localhost:8080/api/autores/1/libros

curl -X POST http://localhost:8080/api/autores -H "Content-Type: application/json" \
  -d '{"nombre":"Juan Rulfo","pais":"México"}'

curl -X POST http://localhost:8080/api/libros -H "Content-Type: application/json" \
  -d '{"titulo":"El llano en llamas","fechaPublicacion":"1953-09-01","idAutor":11,"idEditorial":7}'

curl -X PUT http://localhost:8080/api/libros/41 -H "Content-Type: application/json" \
  -d '{"titulo":"El llano en llamas (ed. revisada)","fechaPublicacion":"1953-09-01","idAutor":11,"idEditorial":9}'

curl -X DELETE http://localhost:8080/api/libros/41
curl -X DELETE http://localhost:8080/api/autores/1     # → 409, tiene libros
```

---

## 6. Swagger

- UI: http://localhost:8080/swagger-ui.html
- JSON OpenAPI: http://localhost:8080/api-docs

Personalizado con título, descripción, contacto, licencia, tags (`OpenApiConfig`), `@Operation`/`@ApiResponses`/`@Parameter`
en cada endpoint, `@Schema` con ejemplos en cada DTO y opciones de la UI en `application.properties`.

---

## 7. Diagramas (draw.io)

`Organizar → Insertar → Avanzado → PlantUML…` y pegar el contenido de cada archivo `.puml` de la carpeta `diagramas/`.
