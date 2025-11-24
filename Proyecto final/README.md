# Sistema de Reclamos - Aplicación de Consola

Una aplicación de consola Java para la gestión de reclamos de servicios públicos, con persistencia en MongoDB.

## 🚀 Características

- ✅ **Interfaz de Consola Mejorada**: Colores ANSI, validaciones robustas, navegación intuitiva
- ✅ **Persistencia en MongoDB**: Base de datos NoSQL con Docker
- ✅ **Gestión de Usuarios**: Autenticación y roles (Cliente/Soporte)
- ✅ **Sistema de Reclamos**: Crear, gestionar y responder reclamos
- ✅ **Estados de Reclamos**: ABIERTO → PENDIENTE → EN_PROCESO → RESUELTO → CERRADO
- ✅ **Notificaciones**: Automáticas para cambios de estado
- ✅ **Cierre Automático**: Reclamos sin respuesta en 3 días

## 🏗️ Arquitectura

```
SistemaReclamos/
├── src/main/java/com/sistema/
│   ├── SistemaReclamos.java          # Clase principal
│   ├── MongoDBConnection.java        # Conexión a MongoDB
│   ├── ConsoleUtils.java             # Utilidades de consola
│   ├── controllers/
│   │   ├── UsuarioController.java    # Controlador de usuarios
│   │   └── ReclamoController.java    # Controlador de reclamos
│   ├── services/
│   │   ├── UserService.java          # Servicio de usuarios
│   │   └── ReclamoService.java       # Servicio de reclamos
│   ├── model/
│   │   ├── Usuario.java              # Modelo de usuario
│   │   ├── Reclamo.java              # Modelo de reclamo
│   │   ├── Respuesta.java            # Modelo de respuesta
│   │   └── Notificacion.java         # Modelo de notificación
│   └── enums/
│       ├── Rol.java                  # Enum de roles
│       ├── EstadoReclamo.java        # Enum de estados
│       └── TipoReclamo.java          # Enum de tipos
└── SistemaReclamos-database/         # Configuración Docker MongoDB
    ├── Dockerfile                    # Imagen MongoDB
    └── init-mongo.js                # Datos iniciales
```

## 📋 Prerrequisitos

- **Java 17** o superior
- **Maven 3.6+**
- **Docker** y **Docker Compose**
- **Git** (opcional)

## 🛠️ Instalación y Configuración

### 1. Clonar el repositorio
```bash
git clone <url-del-repositorio>
cd ISWDISENO202502-1Whatfck/Proyecto final
```

### 2. Iniciar la Base de Datos MongoDB
```bash
# Iniciar MongoDB con Docker Compose
docker-compose up -d

# Verificar que MongoDB esté ejecutándose
docker ps
```

### 3. Compilar el Proyecto
```bash
cd SistemaReclamos

# Descargar dependencias y compilar
mvn clean compile
```

### 4. Ejecutar la Aplicación
```bash
# Opción 1: Ejecutar automáticamente (recomendado)
./start.bat  # En Windows

# Opción 2: Ejecutar con Maven
cd SistemaReclamos
mvn exec:java -Dexec.mainClass="com.sistema.SistemaReclamos"

# Opción 3: Ejecutar JAR con dependencias
java -jar target/sistema-reclamos-1.0.0.jar
```

## 👥 Usuarios del Sistema

### **Usuarios de Soporte (Predefinidos)**
Estos usuarios ya están configurados en la base de datos y no pueden ser registrados:

| Email | Nombre | Rol | Contraseña |
|-------|--------|-----|------------|
| `carlos@email.com` | Carlos López | SOPORTE | `password` |
| `ana@email.com` | Ana Rodríguez | SOPORTE | `password` |

### **Usuarios Clientes (Datos de Prueba):**
Usuarios ya registrados en el sistema:

| Email | Nombre | Rol | Contraseña |
|-------|--------|-----|------------|
| `juan@email.com` | Juan Pérez | CLIENTE | `password` |
| `maria@email.com` | María García | CLIENTE | `password` |
| `pedro@email.com` | Pedro Martínez | CLIENTE | `password` |

### **Registro de Nuevos Clientes:**
Los usuarios que se registren serán automáticamente asignados como **CLIENTES** con contraseña `password`.

**Nota**: En un sistema real, las contraseñas estarían hasheadas y encriptadas.

## 🎮 Uso de la Aplicación

### Menú Principal
```
=== SISTEMA DE RECLAMOS ===
1. Iniciar Sesión
2. Registrarse
3. Salir
```

### Funcionalidades por Rol

#### 👤 Cliente
- ✅ Ver mis reclamos
- ✅ Crear nuevo reclamo
- ✅ Ver detalle de reclamo
- ✅ Cerrar sesión

#### 🔧 Soporte
- ✅ Ver todos los reclamos
- ✅ Ver reclamos pendientes
- ✅ Responder a reclamo
- ✅ Cambiar estado de reclamo
- ✅ Cerrar sesión

## 📊 Estados de Reclamos

| Estado | Descripción |
|--------|-------------|
| 🔵 ABIERTO | Reclamo recién creado |
| 🟡 PENDIENTE | Tiene respuesta de soporte |
| 🟠 EN_PROCESO | Está siendo atendido |
| 🟢 RESUELTO | Solución implementada |
| 🔴 CERRADO | Reclamo finalizado |

## 🗄️ Base de Datos

### Conexión
- **Host**: `localhost`
- **Puerto**: `27018`
- **Base de datos**: `reclamosdb`
- **Usuario**: `reclamos_user`
- **Contraseña**: `reclamos_password`

### Colecciones
- `usuarios` - Información de usuarios
- `reclamos` - Reclamos registrados
- `respuestas` - Respuestas a reclamos
- `notificaciones` - Notificaciones del sistema

## 🐳 Comandos Docker

```bash
# Iniciar servicios
docker-compose up -d

# Ver logs
docker-compose logs -f

# Detener servicios
docker-compose down

# Reiniciar con limpieza
docker-compose down
docker volume rm sistema_reclamos_mongodb_reclamos_data
docker-compose up -d
```

## 🔧 Desarrollo

### Compilación y Empaquetado
```bash
# Compilar el proyecto
mvn clean compile

# Crear JAR con todas las dependencias incluidas
mvn clean package
```

### Ejecución
```bash
# Ejecutar con Maven (modo desarrollo)
cd SistemaReclamos
mvn exec:java -Dexec.mainClass="com.sistema.SistemaReclamos"

# Ejecutar JAR independiente (producción)
cd SistemaReclamos
java -jar target/sistema-reclamos-1.0.0.jar
```

### Dependencias principales
- **MongoDB Driver**: `org.mongodb:mongodb-driver-sync:4.11.1`
- **Jackson**: `com.fasterxml.jackson.core:jackson-databind:2.15.2`
- **SLF4J**: `org.slf4j:slf4j-simple:2.0.9`
- **Maven Shade Plugin**: Para empaquetado con dependencias

## 📝 Scripts Disponibles

- `compile.bat` - Compilación con batch (Windows)
- `docker-compose.yml` - Orquestación de servicios

## 🚨 Solución de Problemas

### Error de conexión a MongoDB
```bash
# Verificar que Docker esté ejecutándose
docker ps

# Verificar logs de MongoDB
docker-compose logs mongodb-reclamos

# Reiniciar servicios
docker-compose restart
```

### Error de compilación
```bash
# Limpiar y recompilar
mvn clean compile

# Forzar descarga de dependencias
mvn dependency:resolve
```

## 🤝 Contribución

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## 📄 Licencia

Este proyecto está bajo la Licencia MIT - ver el archivo [LICENSE](LICENSE) para más detalles.

## 👨‍💻 Autor

**Sistema de Reclamos** - Proyecto académico de Diseño de Software

---
**¡Gracias por usar Sistema de Reclamos!** 🎉