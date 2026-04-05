# 🎬 Sistema de Gestión de Cine

## 📌 Descripción
Sistema completo para la gestión de salas de cine. Desarrollado como prueba técnica implementando un **backend en Spring Boot (Java 21)** 
y un **frontend en Angular 21** con Bootstrap.

---

## 🛠️ Tecnologías Utilizadas

| Capa | Tecnologías |
|------|-------------|
| **Backend** | Java 21, Spring Boot, Spring Data JPA, PostgreSQL, Maven, Swagger |
| **Frontend** | Angular 21, TypeScript, Bootstrap, Bootstrap Icons |
| **Base de Datos** | PostgreSQL |

---

## ✅ Funcionalidades Implementadas

### Backend (API REST)
- CRUD completo de **películas**
- CRUD completo de **salas de cine**
- **Asignación** de películas a salas
- **Eliminación lógica** en todas las entidades
- **Validaciones** de datos de entrada
- **Manejo global de excepciones**
- **Documentación con Swagger**

### Consultas Especiales Requeridas
- Buscar película por **nombre + ID de sala**
- Listar películas por **fecha de publicación** (con cantidad total)
- Buscar sala por **nombre** y mostrar **sus películas asignadas** (o mensaje si no tiene)

### Frontend
- **Login** con credenciales por defecto (`admin` / `admin`)
- **Dashboard** con indicadores:
  - Total de películas
  - Total de salas
  - Salas disponibles
- **CRUD completo de películas** (listar, crear, editar, eliminar)
- **CRUD completo de salas** (listar, crear, editar, eliminar)
- **Asignación de películas a salas** (crear y eliminar)
- **Menú de navegación** profesional
- **Diseño responsive** con Bootstrap
- **Notificaciones elegantes** tipo toast

---

## 🚀 Instalación y Ejecución

### Requisitos Previos
```bash
Java 21+      # java -version
Node.js 18+   # node -v
PostgreSQL 14+# psql --version
Angular CLI   # ng version

🔗 Enlaces Importantes
Recurso	URL
Backend	http://localhost:8090
Swagger UI	http://localhost:8090/swagger-ui.html
Frontend	http://localhost:4200
