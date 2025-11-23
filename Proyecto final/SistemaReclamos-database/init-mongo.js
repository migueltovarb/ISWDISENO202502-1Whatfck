// Inicialización de la base de datos para Sistema de Reclamos

// Crear usuario para la aplicación
db.createUser({
  user: 'sistema_user',
  pwd: 'sistema_pass',
  roles: [
    {
      role: 'readWrite',
      db: 'sistema_reclamos'
    }
  ]
});

// Cambiar a la base de datos del sistema
db = db.getSiblingDB('sistema_reclamos');

// Crear colecciones principales
db.createCollection('usuarios');
db.createCollection('reclamos');
db.createCollection('respuestas');
db.createCollection('notificaciones');

// Crear índices para optimización
db.usuarios.createIndex({ "email": 1 }, { unique: true });
db.reclamos.createIndex({ "clienteId": 1 });
db.reclamos.createIndex({ "estado": 1 });
db.reclamos.createIndex({ "fechaCreacion": -1 });
db.respuestas.createIndex({ "reclamoId": 1 });
db.respuestas.createIndex({ "fecha": -1 });
db.notificaciones.createIndex({ "reclamoId": 1 });
db.notificaciones.createIndex({ "fechaEnvio": -1 });

// Insertar datos de prueba
db.usuarios.insertMany([
  {
    nombre: "Juan Pérez",
    email: "juan.perez@email.com",
    rol: "CLIENTE",
    fechaRegistro: new Date()
  },
  {
    nombre: "María García",
    email: "maria.garcia@email.com",
    rol: "CLIENTE",
    fechaRegistro: new Date()
  },
  {
    nombre: "Carlos López",
    email: "carlos.lopez@email.com",
    rol: "SOPORTE",
    fechaRegistro: new Date()
  }
]);

db.reclamos.insertMany([
  {
    titulo: "Problema con el suministro de agua",
    descripcion: "No tengo agua desde hace 2 días en mi casa",
    tipo: "AGUA",
    ubicacion: "Calle Principal 123",
    fechaCreacion: new Date(),
    estado: "ABIERTO",
    clienteId: ObjectId() // Se asignará dinámicamente
  },
  {
    titulo: "Factura de luz incorrecta",
    descripcion: "La factura del mes pasado tiene un cargo extra no justificado",
    tipo: "LUZ",
    ubicacion: "Avenida Central 456",
    fechaCreacion: new Date(),
    estado: "PENDIENTE",
    clienteId: ObjectId() // Se asignará dinámicamente
  }
]);

print("Base de datos 'sistema_reclamos' inicializada correctamente");
print("Usuario 'sistema_user' creado");
print("Colecciones y índices creados");
print("Datos de prueba insertados");