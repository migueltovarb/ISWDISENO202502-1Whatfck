# 🎯 Sistema de Reclamos - Gestión de Tickets de Servicios Públicos

<div align="center">

![Java](https://img.shields.io/badge/Java-17+-orange?style=for-the-badge&logo=java)
![MongoDB](https://img.shields.io/badge/MongoDB-4.4+-green?style=for-the-badge&logo=mongodb)
![Docker](https://img.shields.io/badge/Docker-Ready-blue?style=for-the-badge&logo=docker)
![Maven](https://img.shields.io/badge/Maven-3.6+-purple?style=for-the-badge&logo=apache-maven)

[![Repository](https://img.shields.io/badge/GitHub-Repository-black?style=for-the-badge&logo=github)](https://github.com/migueltovarb/ISWDISENO202502-1Whatfck)
[![License](https://img.shields.io/badge/License-MIT-blue?style=for-the-badge)](LICENSE)

---

## 🎥 Demo del Proyecto
> **[📺 Ver Exposición en YouTube](https://youtube.com/watch?v=)** *(Próximamente)*

---

### 📋 Descripción
Aplicación de consola Java para la gestión integral de reclamos de servicios públicos. Implementa arquitectura MVC con persistencia en MongoDB, autenticación de usuarios y sistema completo de notificaciones.

</div>

## ✨ Características Principales

<table>
<tr>
<td align="center">
<img src="https://img.shields.io/badge/🎨-UI_Consola-blue?style=flat-square" alt="UI Consola"/>
<br><b>Interfaz Mejorada</b>
<br>• Colores ANSI vibrantes
<br>• Validaciones robustas
<br>• Navegación intuitiva
</td>
<td align="center">
<img src="https://img.shields.io/badge/👥-Usuarios-green?style=flat-square" alt="Usuarios"/>
<br><b>Gestión de Usuarios</b>
<br>• Autenticación segura
<br>• Roles Cliente/Soporte
<br>• Registro automático
</td>
<td align="center">
<img src="https://img.shields.io/badge/📋-Reclamos-orange?style=flat-square" alt="Reclamos"/>
<br><b>Sistema de Reclamos</b>
<br>• Estados dinámicos
<br>• Respuestas en hilo
<br>• Cierre automático
</td>
</tr>
</table>

### 🔄 Flujo de Estados
```
🔵 ABIERTO → 🟡 PENDIENTE → 🟠 EN_PROCESO → 🟢 RESUELTO → 🔴 CERRADO
```

## 🚀 Inicio Rápido

### 📋 Prerrequisitos
- **Java 17+** • **Maven 3.6+** • **Docker & Docker Compose**

### ⚡ Instalación en 3 pasos

```bash
# 1. Clonar repositorio
git clone https://github.com/migueltovarb/ISWDISENO202502-1Whatfck.git
cd ISWDISENO202502-1Whatfck/Proyecto\ final

# 2. Iniciar base de datos
docker-compose up -d

# 3. Ejecutar aplicación
cd SistemaReclamos && mvn exec:java -Dexec.mainClass="com.sistema.SistemaReclamos"
```

## 👥 Usuarios del Sistema

### 🔧 Soporte (Predefinidos)
| Usuario | Email | Contraseña |
|---------|-------|------------|
| Carlos López | `carlos@email.com` | `password` |
| Ana Rodríguez | `ana@email.com` | `password` |

### 👤 Clientes (Datos de Prueba)
| Usuario | Email | Contraseña |
|---------|-------|------------|
| Juan Pérez | `juan@email.com` | `password` |
| María García | `maria@email.com` | `password` |
| Pedro Martínez | `pedro@email.com` | `password` |

> 💡 **Nota**: Nuevos registros son automáticamente asignados como **CLIENTES**

## 🎮 Funcionalidades

### 👤 Cliente
- ✅ **Ver mis reclamos** - Lista personalizada
- ✅ **Crear reclamo** - Nuevo ticket con validaciones
- ✅ **Ver detalles** - Historial completo con respuestas
- ✅ **Editar reclamo** - Modificar datos (estados permitidos)
- ✅ **Cerrar satisfactoriamente** - Confirmación de resolución
- ✅ **Ver notificaciones** - Alertas del sistema

### 🔧 Soporte
- ✅ **Ver todos los reclamos** - Vista completa del sistema
- ✅ **Gestionar pendientes** - Atención prioritaria
- ✅ **Responder reclamos** - Comunicación bidireccional
- ✅ **Cambiar estados** - Control del flujo de trabajo
- ✅ **Buscar reclamos** - Filtros avanzados
- ✅ **Ver notificaciones** - Seguimiento de actividades

## 🏗️ Arquitectura

```
SistemaReclamos/
├── 🎯 SistemaReclamos.java          # Punto de entrada
├── 🔌 MongoDBConnection.java        # Conexión BD
├── 🎨 ConsoleUtils.java             # Utilidades UI
├── 🎮 controllers/                  # Controladores MVC
│   ├── UsuarioController.java       # Gestión usuarios
│   └── ReclamoController.java       # Gestión reclamos
├── 🔧 services/                     # Lógica de negocio
│   ├── UserService.java            # Servicio usuarios
│   ├── ReclamoService.java         # Servicio reclamos
│   └── NotificacionService.java    # Servicio notificaciones
├── 📦 model/                        # Modelos de datos
│   ├── Usuario.java                 # Entidad usuario
│   ├── Reclamo.java                 # Entidad reclamo
│   ├── Respuesta.java               # Entidad respuesta
│   └── Notificacion.java            # Entidad notificación
└── 🏷️ enums/                        # Enumeraciones
    ├── Rol.java                     # Roles del sistema
    ├── EstadoReclamo.java           # Estados de reclamo
    └── TipoReclamo.java             # Tipos de servicio
```

## 🐳 Docker & Base de Datos

### 📊 Configuración MongoDB
```yaml
Host: localhost:27018
Database: reclamosdb
Usuario: reclamos_user
Contraseña: reclamos_password
```

### 🛠️ Comandos Útiles
```bash
# Gestionar servicios
docker-compose up -d          # Iniciar
docker-compose logs -f        # Ver logs
docker-compose down           # Detener

# Desarrollo
mvn clean compile             # Compilar
mvn exec:java -Dexec.mainClass="com.sistema.SistemaReclamos"  # Ejecutar
```

## 📈 Estados de Reclamos

| Estado | Icono | Descripción |
|--------|-------|-------------|
| **ABIERTO** | 🔵 | Reclamo recién creado |
| **PENDIENTE** | 🟡 | Esperando respuesta del cliente |
| **EN_PROCESO** | 🟠 | Siendo atendido por soporte |
| **RESUELTO** | 🟢 | Solución implementada |
| **CERRADO** | 🔴 | Reclamo finalizado |

## 🎯 Características Técnicas

- ✅ **Persistencia completa** en MongoDB
- ✅ **Autenticación robusta** con verificación de credenciales
- ✅ **Validaciones de entrada** con mensajes de error claros
- ✅ **Navegación intuitiva** con breadcrumbs
- ✅ **Sistema de notificaciones** automático
- ✅ **Cierre automático** de reclamos inactivos
- ✅ **Interfaz coloreada** con ANSI escape codes
- ✅ **Manejo de errores** comprehensivo

## 🤝 Contribución

1. 🍴 **Fork** el proyecto
2. 🌿 **Crea** una rama (`git checkout -b feature/AmazingFeature`)
3. 💾 **Commit** cambios (`git commit -m 'Add AmazingFeature'`)
4. 📤 **Push** rama (`git push origin feature/AmazingFeature`)
5. 🔄 **Pull Request**

## 📄 Licencia

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

**Proyecto académico - Diseño de Software** 🎓

---

<div align="center">

**¡Gracias por usar Sistema de Reclamos!** 🎉

*Construido con ❤️ para la gestión eficiente de servicios públicos*

</div>