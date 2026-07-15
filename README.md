# Sistema de Gestión de Tareas

Sistema de gestión de tareas desarrollado con **Spring Boot** y **JavaFX**, que permite realizar operaciones CRUD sobre las tareas registradas a través de una interfaz gráfica de escritorio. Utiliza **Spring Data JPA** para el acceso a datos y **MySQL** como motor de base de datos.

---

## 📋 Tabla de Contenidos

- [Características](#características)
- [Tecnologías](#tecnologías)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Requisitos Previos](#requisitos-previos)
- [Configuración de la Base de Datos](#configuración-de-la-base-de-datos)
- [Configuración de la Aplicación](#configuración-de-la-aplicación)
- [Uso](#uso)
- [Arquitectura](#arquitectura)
- [Autor](#autor)

---

## ✅ Características

- Listar todas las tareas registradas en una tabla interactiva
- Agregar nuevas tareas con nombre, responsable y estatus
- Modificar los datos de una tarea existente seleccionándola en la tabla
- Eliminar una tarea seleccionada
- Limpiar el formulario de entrada
- Validación de campos obligatorios con alertas informativas
- Integración de Spring Boot con JavaFX mediante ciclo de vida compartido
- Registro de operaciones con SLF4J Logger

---

## 🛠️ Tecnologías

| Tecnología | Versión | Uso |
|---|---|---|
| Java | 17+ | Lenguaje principal |
| Spring Boot | 3+ | Framework principal e inyección de dependencias |
| Spring Data JPA | 3+ | Acceso a datos y operaciones CRUD |
| Hibernate | 6+ | ORM para mapeo objeto-relacional |
| JavaFX | 21+ | Interfaz gráfica de escritorio |
| MySQL | 8+ | Motor de base de datos |
| SLF4J | - | Registro de logs |
| Maven | - | Gestión de dependencias |

---

## 📁 Estructura del Proyecto

```
src/
└── main/
    ├── java/
    │   └── ernidev/tareas/
    │       ├── TareasApplication.java          # Clase principal, punto de entrada
    │       ├── controlador/
    │       │   └── IndexControlador.java       # Controlador JavaFX con Spring
    │       ├── modelo/
    │       │   └── Tarea.java                  # Entidad JPA que representa una tarea
    │       ├── presentacion/
    │       │   └── SistemaTareasFx.java        # Integración del ciclo de vida JavaFX + Spring
    │       ├── repositorio/
    │       │   └── TareaRepositorio.java       # Repositorio Spring Data JPA
    │       └── servicio/
    │           └── TareaServicio.java          # Capa de servicio con lógica de negocio
    └── resources/
        ├── application.properties              # Configuración de la aplicación
        └── templates/
            └── index.fxml                      # Vista principal de JavaFX
```

---

## ⚙️ Requisitos Previos

- Java JDK 17 o superior
- MySQL 8 o superior instalado y en ejecución
- Maven 3.8 o superior
- IDE compatible con JavaFX (IntelliJ IDEA recomendado)

---

## 🗄️ Configuración de la Base de Datos

Ejecuta el siguiente script SQL para crear la base de datos y la tabla necesaria:

```sql
CREATE DATABASE zona_fit_db;

USE zona_fit_db;

CREATE TABLE tarea (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre_tarea VARCHAR(100) NOT NULL,
    responsable  VARCHAR(100) NOT NULL,
    estatus      VARCHAR(50)  NOT NULL
);
```

---

## 🔌 Configuración de la Aplicación

Los parámetros de configuración se encuentran en `src/main/resources/application.properties`:

```properties
# Nombre de la aplicación
spring.application.name=zona_fit

# Conexión a la base de datos
spring.datasource.url=jdbc:mysql://localhost:3306/zona_fit_db
spring.datasource.username=root
spring.datasource.password=

# Driver JDBC
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA / Hibernate
spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=false

# Desactiva el servidor web (aplicación de escritorio)
spring.main.web-application-type=none
```

Modifica `spring.datasource.username` y `spring.datasource.password` según las credenciales de tu servidor MySQL.

---

## 🚀 Uso

1. Clona el repositorio y abre el proyecto en tu IDE
2. Configura las credenciales de base de datos en `application.properties`
3. Ejecuta el script SQL para crear la base de datos
4. Corre la clase `TareasApplication`

La aplicación abrirá una ventana de escritorio con la siguiente interfaz:

```
┌─────────────────────────────────────────────┐
│         Sistema de Tareas                   │
├──────────────────┬──────────────────────────┤
│  Formulario      │  Tabla de Tareas         │
│  Responsable: __ │  Id | Tarea | Resp | Est │
│  Tarea:       __ │  ----------------------- │
│  Estatus:     __ │  Fila 1...               │
│                  │  Fila 2...               │
├──────────────────┴──────────────────────────┤
│  [Agregar] [Modificar] [Eliminar] [Limpiar] │
└─────────────────────────────────────────────┘
```

**Flujo de uso:**
- **Agregar:** completa el formulario y presiona *Agregar*
- **Modificar:** haz clic en una fila de la tabla, edita el formulario y presiona *Modificar*
- **Eliminar:** selecciona una fila de la tabla y presiona *Eliminar*
- **Limpiar:** presiona *Limpiar Formulario* para resetear los campos

---

## 🏗️ Arquitectura

El proyecto sigue una arquitectura en capas con integración Spring Boot + JavaFX:

```
┌─────────────────────────────┐
│     Vista (FXML)            │  index.fxml
├─────────────────────────────┤
│     Controlador (JavaFX)    │  IndexControlador.java
├─────────────────────────────┤
│     Servicio (Spring)       │  TareaServicio.java
├─────────────────────────────┤
│     Repositorio (JPA)       │  TareaRepositorio.java
├─────────────────────────────┤
│     Modelo (Entidad JPA)    │  Tarea.java
├─────────────────────────────┤
│     Base de Datos (MySQL)   │  zona_fit_db
└─────────────────────────────┘
```

**Integración Spring Boot + JavaFX:**
- `TareasApplication` lanza JavaFX en lugar del servidor web de Spring
- `SistemaTareasFx` inicializa el contexto de Spring en el método `init()` de JavaFX
- `IndexControlador` es un bean de Spring (`@Component`) con soporte para `@Autowired`

---

## 👤 Autor

**Erick Gonzalez**
