# Sistema Reclamos - Base de Datos MongoDB

Este directorio contiene la configuración Docker para la base de datos MongoDB del Sistema de Reclamos.

## Requisitos

- Docker
- Docker Compose

## Inicio Rápido

1. **Levantar la base de datos:**
   ```bash
   docker-compose up -d
   ```

2. **Verificar que esté corriendo:**
   ```bash
   docker-compose ps
   ```

3. **Ver logs:**
   ```bash
   docker-compose logs -f mongo
   ```

4. **Detener la base de datos:**
   ```bash
   docker-compose down
   ```

## Configuración

### Variables de Entorno
- **MONGO_INITDB_ROOT_USERNAME**: `admin`
- **MONGO_INITDB_ROOT_PASSWORD**: `password123`
- **MONGO_INITDB_DATABASE**: `sistema_reclamos`

### Usuario de Aplicación
- **Usuario**: `sistema_user`
- **Contraseña**: `sistema_pass`
- **Base de datos**: `sistema_reclamos`
- **Rol**: `readWrite`

## Conexión desde la Aplicación

### String de conexión:
```
mongodb://sistema_user:sistema_pass@localhost:27017/sistema_reclamos
```

### Para desarrollo local:
```
mongodb://localhost:27017/sistema_reclamos
```

## Colecciones Creadas

- `usuarios` - Información de usuarios (clientes y soporte)
- `reclamos` - Reclamos registrados por clientes
- `respuestas` - Respuestas del soporte a los reclamos
- `notificaciones` - Historial de notificaciones enviadas

## Índices Optimizados

- `usuarios.email` (único)
- `reclamos.clienteId`
- `reclamos.estado`
- `reclamos.fechaCreacion` (descendente)
- `respuestas.reclamoId`
- `respuestas.fecha` (descendente)
- `notificaciones.reclamoId`
- `notificaciones.fechaEnvio` (descendente)

## Datos de Prueba

Se incluyen datos de prueba iniciales:
- 2 usuarios clientes
- 1 usuario soporte
- 2 reclamos de ejemplo

## Persistencia

Los datos se persisten en un volumen Docker llamado `mongo_data`. Los datos sobreviven a reinicios del contenedor.

## Troubleshooting

### Puerto ocupado
Si el puerto 27017 está ocupado:
```bash
# Cambiar el puerto en docker-compose.yml
ports:
  - "27018:27017"
```

### Resetear datos
Para resetear completamente la base de datos:
```bash
docker-compose down -v
docker-compose up -d
```

### Conectar con MongoDB Compass
- **Host**: `localhost`
- **Port**: `27017`
- **Authentication**: `sistema_user` / `sistema_pass`
- **Authentication Database**: `sistema_reclamos`