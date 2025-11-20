# 🧪 Pruebas con Postman - API de Vehículos

## 📋 Descripción

Este directorio contiene una colección completa de Postman con todas las pruebas necesarias para validar la funcionalidad de la **API REST de Gestión de Vehículos**.

## 📁 Archivos Incluidos

- **`Vehiculos API - Colección Completa.postman_collection.json`** - Colección principal con todas las pruebas
- **`Vehiculos API - Variables.postman_environment.json`** - Variables de entorno para Postman

## 🚀 Configuración Inicial

### 1. Importar la Colección en Postman

1. Abre **Postman**
2. Clic en **Import** (esquina superior izquierda)
3. Selecciona **File**
4. Importa el archivo: `Vehiculos API - Colección Completa.postman_collection.json`

### 2. Importar Variables de Entorno

1. En Postman, ve a **Environments** (panel izquierdo)
2. Clic en **Import**
3. Importa el archivo: `Vehiculos API - Variables.postman_environment.json`
4. Selecciona el environment **"Vehículos API - Environment"**

### 3. Verificar Configuración

- **Base URL**: `http://localhost:8080/api`
- **Environment**: "Vehículos API - Environment" seleccionado
- **API ejecutándose**: Asegúrate de que los contenedores Docker estén corriendo

## 📊 Estructura de las Pruebas

### 🗂️ Carpetas de Pruebas

```
Vehículos API - Colección Completa/
├── 🏥 Health Check
├── 📊 Estadísticas
├── 🆕 Operaciones CRUD - Crear
├── 📖 Operaciones CRUD - Leer
├── 🔍 Consultas Avanzadas
├── ✏️ Operaciones CRUD - Actualizar
├── 🗑️ Operaciones CRUD - Eliminar
├── ❌ Casos de Error
└── 🔄 Flujo Completo de Pruebas
```

### 🧪 Tipos de Pruebas Incluidas

#### ✅ **Pruebas Positivas** (Funcionalidades Correctas)
- Crear vehículos con datos válidos
- Listar y consultar vehículos existentes
- Actualizar información de vehículos
- Eliminar vehículos
- Búsquedas por diferentes criterios
- Paginación automática
- Estadísticas del sistema

#### ❌ **Pruebas Negativas** (Casos de Error)
- Crear vehículo con placa duplicada
- Formato de placa inválido
- Precio fuera de rango
- IDs inexistentes
- Parámetros inválidos

## 🎯 Guía de Ejecución

### **Orden Recomendado de Ejecución:**

1. **🏥 Health Check** - Verificar que la API esté funcionando
2. **📊 Estadísticas** - Ver estado inicial del sistema
3. **🆕 Crear vehículos** - Preparar datos de prueba
4. **📖 Leer/Listar** - Verificar consultas
5. **🔍 Consultas Avanzadas** - Probar filtros y búsquedas
6. **✏️ Actualizar** - Modificar datos existentes
7. **❌ Casos de Error** - Validar manejo de errores
8. **🗑️ Eliminar** - Limpiar datos de prueba

### **Ejecución Automática:**

1. Selecciona la colección completa
2. Clic en **Run Collection**
3. Configura las opciones de ejecución
4. Ejecuta todas las pruebas automáticamente

## 🔧 Variables de Entorno

| Variable | Descripción | Valor por Defecto |
|----------|-------------|-------------------|
| `base_url` | URL base de la API | `http://localhost:8080/api` |
| `vehiculo_id` | ID del vehículo creado | *(se actualiza automáticamente)* |
| `vehiculo_placa` | Placa para pruebas | `XYZ123` |
| `timestamp` | Timestamp único | *(generado automáticamente)* |

## 📝 Tests Automáticos Incluidos

Cada request incluye **tests automáticos** que verifican:

- ✅ **Códigos de respuesta HTTP** correctos
- ✅ **Estructura de respuesta** válida
- ✅ **Datos esperados** en las respuestas
- ✅ **Tiempos de respuesta** aceptables (< 2000ms)
- ✅ **Headers requeridos** presentes

### Ejemplo de Test Automático:

```javascript
pm.test("Status code is 201", function () {
    pm.response.to.have.status(201);
});

pm.test("Response has vehicle data", function () {
    var jsonData = pm.response.json();
    pm.expect(jsonData).to.have.property('id');
    pm.expect(jsonData.marca).to.eql('Toyota');
});
```

## 🎪 Casos de Prueba Detallados

### **1. Crear Vehículo**
```http
POST /api/vehiculos
Content-Type: application/json

{
  "marca": "Toyota",
  "modelo": "Corolla",
  "año": 2024,
  "placa": "ABC123",
  "precio": 25000.00,
  "tipo": "sedan",
  "disponible": true
}
```

### **2. Listar con Paginación**
```http
GET /api/vehiculos?page=0&size=10
```

### **3. Búsqueda Avanzada**
```http
GET /api/vehiculos/buscar?texto=toyota&page=0&size=10
```

### **4. Obtener Estadísticas**
```http
GET /api/vehiculos/estadisticas
```

## 🚨 Casos de Error Incluidos

### **Placa Duplicada**
- **Request**: Crear vehículo con placa existente
- **Expected**: `400 Bad Request`

### **Formato de Placa Inválido**
- **Request**: Placa que no cumple regex `^[A-Z]{3}[0-9]{3}$`
- **Expected**: `400 Bad Request`

### **Precio Fuera de Rango**
- **Request**: Precio < 1000.0 o > 1000000.0
- **Expected**: `400 Bad Request`

### **Recurso No Encontrado**
- **Request**: ID inexistente
- **Expected**: `400 Bad Request`

## 📈 Reportes de Pruebas

### **Ejecutar con Reportes:**

1. En Postman, ve a **Runner**
2. Selecciona la colección
3. Activa **Generate Reports**
4. Ejecuta las pruebas
5. Revisa el reporte generado

### **Métricas Incluidas:**
- ✅ **Tasa de éxito** de las pruebas
- ⏱️ **Tiempos de respuesta**
- 📊 **Cobertura de endpoints** probados
- 🚨 **Errores encontrados**

## 🔧 **Correcciones Realizadas**

### **Problemas Solucionados:**
- ✅ **Placas duplicadas**: Cambiadas las placas de prueba para evitar conflictos con datos iniciales
  - `TEST123` → `XYZ123` (vehículo principal de prueba)
  - `HON456` → `HON999` (Honda Civic de prueba)
  - `FOR789` → `FOR888` (Ford Explorer de prueba)
- ✅ **Test de error**: Configurado para usar placa existente (`DEF456`) del dataset inicial
- ✅ **Variables sincronizadas**: Archivo de environment actualizado con nuevas placas

### **Datos de Inicialización (MongoDB):**
```
Toyota Corolla - ABC123
Honda Civic    - DEF456
Ford Explorer  - GHI789
```

### **Datos de Prueba (únicos):**
```
Toyota Corolla - XYZ123  ← Vehículo principal
Honda Civic    - HON999  ← Segundo vehículo
Ford Explorer  - FOR888  ← Vehículo no disponible
```

## 🔄 Integración Continua

### **Para CI/CD:**

```yaml
# Ejemplo de integración con GitHub Actions
- name: Run API Tests
  run: |
    npm install -g newman
    newman run "Vehiculos API - Colección Completa.postman_collection.json" \
      --environment "Vehiculos API - Variables.postman_environment.json" \
      --reporters cli,json \
      --reporter-json-export results.json
```

## 🐛 Solución de Problemas

### **API No Responde:**
```bash
# Verificar que los contenedores estén corriendo
docker-compose ps

# Reiniciar servicios
docker-compose restart

# Ver logs
docker-compose logs vehiculos-api
```

### **Errores de Conexión:**
- Verificar que `base_url` apunte a `http://localhost:8080/api`
- Asegurar que el environment esté seleccionado
- Confirmar que la API esté healthy: `GET /health`

### **Tests Fallando:**
- Ejecutar requests individualmente para debug
- Verificar datos en Mongo Express: `http://localhost:8081`
- Revisar logs de la aplicación

## 📞 Soporte

Para problemas con las pruebas:

1. **Verificar configuración** de Postman
2. **Revisar logs** de la API
3. **Confirmar estado** de los contenedores
4. **Ejecutar tests** individualmente

---

## 🎯 Checklist de Pruebas

- [x] **Health Check** funciona
- [x] **Crear vehículo** exitoso
- [x] **Listar vehículos** con paginación
- [x] **Consultas avanzadas** funcionan
- [x] **Actualizar vehículo** exitoso
- [x] **Eliminar vehículo** exitoso
- [x] **Casos de error** manejados correctamente
- [x] **Estadísticas** actualizadas
- [x] **Tests automáticos** pasan
- [x] **Tiempos de respuesta** aceptables

**¡Todas las pruebas deben pasar para considerar la API completamente funcional!** ✅