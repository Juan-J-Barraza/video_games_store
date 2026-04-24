# 🎮 Pixel Vault — Video Game Store

Pixel Vault es una tienda de videojuegos construida con **Spring Boot 4**, **Thymeleaf**, **Spring Security** y **MySQL**. Permite a los usuarios registrarse, iniciar sesión, explorar un catálogo de videojuegos, agregarlos al carrito y simular una compra que descuenta el stock en la base de datos.

---

## ✨ Características

- **Registro e inicio de sesión** con contraseñas encriptadas (BCrypt) y sesiones gestionadas por Spring Security.
- **Catálogo de videojuegos** con imágenes, descripciones, precios y stock disponible.
- **Carrito de compras** para agregar, eliminar y revisar los juegos seleccionados.
- **Simulación de venta** que genera una orden, descuenta el stock y vacía el carrito.
- **Diseño responsive** con menú hamburguesa para dispositivos móviles.
- **Interfaz premium** con dark mode, glassmorphism y micro-animaciones.

---

## 🛠️ Tecnologías

| Capa        | Tecnología                          |
|-------------|-------------------------------------|
| Backend     | Java 21, Spring Boot 4.0.6         |
| Seguridad   | Spring Security (sesiones + BCrypt)|
| Persistencia| Spring Data JPA, Hibernate         |
| Base de datos| MySQL 8.0 (Docker)                |
| Frontend    | Thymeleaf, Vanilla CSS, JavaScript |
| Contenedores| Docker Compose                     |

---

## 📁 Estructura del Proyecto

```
src/main/java/com/example/store/demo/
├── controller/         # Controladores MVC (Auth, Store, Cart)
├── entity/             # Entidades JPA (AppUser, Game, Cart, Order...)
├── repository/         # Repositorios Spring Data JPA
├── security/           # Configuración de Spring Security
├── service/            # Lógica de negocio (CartService)
└── DemoApplication.java

src/main/resources/
├── templates/          # Vistas Thymeleaf (login, register, store, cart)
├── static/
│   ├── css/            # Estilos CSS
│   └── js/             # JavaScript
├── application.properties
└── schema.sql          # DDL + datos iniciales
```

---

## 📋 Prerrequisitos

- **Java 21** o superior
- **Maven 3.9+**
- **Docker** y **Docker Compose**

---

## 🚀 Cómo ejecutar el proyecto

### 1. Clonar el repositorio

```bash
git clone https://github.com/Juan-J-Barraza/video_games_store.git
cd video_games_store
```

### 2. Levantar la base de datos con Docker

```bash
docker compose up -d
```

Esto crea un contenedor MySQL 8.0 con:
- **Base de datos:** `demo_video_games_db`
- **Usuario:** `app_user`
- **Contraseña:** `secret`
- **Puerto:** `3306`

El archivo `schema.sql` se ejecuta automáticamente al iniciar el contenedor por primera vez, creando las tablas e insertando 6 videojuegos de ejemplo.

### 3. Compilar el proyecto

```bash
mvn clean package -DskipTests
```

### 4. Ejecutar la aplicación

```bash
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

### 5. Abrir en el navegador

```
http://localhost:8080
```

---

## 🎯 Flujo de uso

1. Al entrar verás el **catálogo de videojuegos** con sus imágenes y precios.
2. Haz clic en **Register** para crear una cuenta nueva.
3. Inicia sesión con tus credenciales en **Login**.
4. Usa el botón **Add to Cart** en cualquier juego para agregarlo al carrito.
5. Ve al **🛒 Cart** para revisar tu selección.
6. Presiona **Simulate Checkout** para procesar la compra (se descuenta el stock).

---

## ⚙️ Configuración

La configuración principal se encuentra en `src/main/resources/application.properties`:

| Propiedad                          | Valor por defecto                                        |
|------------------------------------|----------------------------------------------------------|
| `spring.datasource.url`           | `jdbc:mysql://localhost:3306/demo_video_games_db`        |
| `spring.datasource.username`      | `app_user`                                               |
| `spring.datasource.password`      | `secret`                                                 |
| `spring.jpa.hibernate.ddl-auto`   | `update`                                                 |

---

## 🗄️ Modelo de datos

```
app_users ──┐
            ├── carts ── cart_items ── games
            └── orders ── order_items ── games
```

- **app_users**: Usuarios registrados (username, password encriptado, role).
- **games**: Catálogo de videojuegos (título, descripción, precio, stock, imagen).
- **carts / cart_items**: Carrito de compras por usuario.
- **orders / order_items**: Órdenes generadas al simular la compra.

---

## 📄 Licencia

Este proyecto es de uso académico/educativo.
