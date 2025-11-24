// Script de inicialización de MongoDB para Sistema de Reclamos

// Crear usuario para la aplicación
db.createUser({
  user: 'reclamos_user',
  pwd: 'reclamos_password',
  roles: [
    {
      role: 'readWrite',
      db: 'reclamosdb'
    }
  ]
});

// Cambiar a la base de datos de reclamos
db = db.getSiblingDB('reclamosdb');

// Crear colección de usuarios con datos iniciales
db.usuarios.insertMany([
  {
    _id: 1,
    nombre: "Juan Pérez",
    email: "juan@email.com",
    password: "password",
    rol: "CLIENTE",
    fechaCreacion: new Date()
  },
  {
    _id: 2,
    nombre: "María García",
    email: "maria@email.com",
    password: "password",
    rol: "CLIENTE",
    fechaCreacion: new Date()
  },
  {
    _id: 3,
    nombre: "Carlos López",
    email: "carlos@email.com",
    password: "password",
    rol: "SOPORTE",
    fechaCreacion: new Date()
  },
  {
    _id: 4,
    nombre: "Ana Rodríguez",
    email: "ana@email.com",
    password: "password",
    rol: "SOPORTE",
    fechaCreacion: new Date()
  },
  {
    _id: 5,
    nombre: "Pedro Martínez",
    email: "pedro@email.com",
    password: "password",
    rol: "CLIENTE",
    fechaCreacion: new Date()
  }
]);

// Crear colección de reclamos con datos iniciales
db.reclamos.insertMany([
  {
    _id: 1,
    titulo: "Problema con suministro de agua",
    descripcion: "No tengo agua desde hace 2 días en mi casa",
    tipo: "AGUA",
    ubicacion: "Calle Principal 123",
    estado: "PENDIENTE",
    clienteId: 1,
    fechaCreacion: new Date()
  },
  {
    _id: 2,
    titulo: "Factura de luz incorrecta",
    descripcion: "Cargo extra no justificado en mi factura del mes pasado",
    tipo: "LUZ",
    ubicacion: "Avenida Central 456",
    estado: "RESUELTO",
    clienteId: 2,
    fechaCreacion: new Date()
  },
  {
    _id: 3,
    titulo: "Internet muy lento",
    descripcion: "La velocidad de internet ha bajado considerablemente",
    tipo: "INTERNET",
    ubicacion: "Barrio Nuevo 789",
    estado: "PENDIENTE",
    clienteId: 1,
    fechaCreacion: new Date()
  },
  {
    _id: 4,
    titulo: "Daño en alcantarillado",
    descripcion: "Hay una fuga en la calle causando inundación",
    tipo: "ALCANTARILLADO",
    ubicacion: "Plaza Central 321",
    estado: "EN_PROCESO",
    clienteId: 5,
    fechaCreacion: new Date()
  },
  {
    _id: 5,
    titulo: "Problema con teléfono",
    descripcion: "No tengo línea telefónica desde ayer",
    tipo: "INTERNET",
    ubicacion: "Zona Residencial 654",
    estado: "CERRADO",
    clienteId: 2,
    fechaCreacion: new Date()
  }
]);

// Crear colección de respuestas con datos iniciales
db.respuestas.insertMany([
  {
    _id: 1,
    mensaje: "Hemos recibido su reclamo sobre el suministro de agua. Un técnico visitará su domicilio en las próximas 24 horas.",
    reclamoId: 1,
    usuarioId: 3,
    fecha: new Date()
  },
  {
    _id: 2,
    mensaje: "Estamos revisando su factura de luz. Le notificaremos sobre cualquier ajuste necesario.",
    reclamoId: 2,
    usuarioId: 4,
    fecha: new Date()
  },
  {
    _id: 3,
    mensaje: "Gracias por la respuesta. ¿Podrían darme una fecha aproximada para la reparación?",
    reclamoId: 1,
    usuarioId: 1,
    fecha: new Date()
  },
  {
    _id: 4,
    mensaje: "El técnico estará en su domicilio mañana entre las 9:00 y 11:00 AM.",
    reclamoId: 1,
    usuarioId: 3,
    fecha: new Date()
  },
  {
    _id: 5,
    mensaje: "Hemos revisado su factura y efectivamente había un cargo erróneo. El ajuste se reflejará en su próxima factura.",
    reclamoId: 2,
    usuarioId: 4,
    fecha: new Date()
  },
  {
    _id: 6,
    mensaje: "Perfecto, muchas gracias por la corrección. Estoy satisfecho con la solución.",
    reclamoId: 2,
    usuarioId: 2,
    fecha: new Date()
  },
  {
    _id: 7,
    mensaje: "Estamos trabajando en optimizar la señal de internet en su zona. Un técnico especializado visitará su domicilio esta semana.",
    reclamoId: 3,
    usuarioId: 4,
    fecha: new Date()
  },
  {
    _id: 8,
    mensaje: "Hemos identificado una fuga en el alcantarillado de la zona. Nuestro equipo de mantenimiento está trabajando en la reparación.",
    reclamoId: 4,
    usuarioId: 3,
    fecha: new Date()
  },
  {
    _id: 9,
    mensaje: "La línea telefónica ha sido restaurada. Disculpe las molestias ocasionadas.",
    reclamoId: 5,
    usuarioId: 4,
    fecha: new Date()
  }
]);

// Crear colección de notificaciones
db.notificaciones.insertMany([
  {
    _id: 1,
    mensaje: "Nuevo reclamo registrado: Problema con suministro de agua",
    reclamoId: 1,
    fechaEnvio: new Date()
  },
  {
    _id: 2,
    mensaje: "El estado del reclamo 'Factura de luz incorrecta' cambió de ABIERTA a EN_PROCESO",
    reclamoId: 2,
    fechaEnvio: new Date()
  },
  {
    _id: 3,
    mensaje: "El estado del reclamo 'Factura de luz incorrecta' cambió de EN_PROCESO a RESUELTO",
    reclamoId: 2,
    fechaEnvio: new Date()
  },
  {
    _id: 4,
    mensaje: "Tienes una respuesta en 'Problema con suministro de agua' ID: 1",
    reclamoId: 1,
    fechaEnvio: new Date()
  },
  {
    _id: 5,
    mensaje: "Tienes una respuesta en 'Factura de luz incorrecta' ID: 2",
    reclamoId: 2,
    fechaEnvio: new Date()
  },
  {
    _id: 6,
    mensaje: "Tienes una respuesta en 'Internet muy lento' ID: 3",
    reclamoId: 3,
    fechaEnvio: new Date()
  },
  {
    _id: 7,
    mensaje: "Nuevo reclamo registrado: Daño en alcantarillado",
    reclamoId: 4,
    fechaEnvio: new Date()
  },
  {
    _id: 8,
    mensaje: "El estado del reclamo 'Daño en alcantarillado' cambió de ABIERTA a EN_PROCESO",
    reclamoId: 4,
    fechaEnvio: new Date()
  },
  {
    _id: 9,
    mensaje: "Nuevo reclamo registrado: Problema con teléfono",
    reclamoId: 5,
    fechaEnvio: new Date()
  },
  {
    _id: 10,
    mensaje: "El estado del reclamo 'Problema con teléfono' cambió de ABIERTA a EN_PROCESO",
    reclamoId: 5,
    fechaEnvio: new Date()
  },
  {
    _id: 11,
    mensaje: "El estado del reclamo 'Problema con teléfono' cambió de EN_PROCESO a RESUELTO",
    reclamoId: 5,
    fechaEnvio: new Date()
  },
  {
    _id: 12,
    mensaje: "El reclamo 'Factura de luz incorrecta' ha sido cerrado satisfactoriamente por el cliente",
    reclamoId: 2,
    fechaEnvio: new Date()
  },
  {
    _id: 13,
    mensaje: "El estado del reclamo 'Factura de luz incorrecta' cambió de RESUELTO a CERRADO",
    reclamoId: 2,
    fechaEnvio: new Date()
  }
]);

print("✅ Base de datos de Sistema de Reclamos inicializada correctamente");
print("📊 Usuarios creados: " + db.usuarios.count());
print("📋 Reclamos creados: " + db.reclamos.count());
print("💬 Respuestas creadas: " + db.respuestas.count());
print("🔔 Notificaciones creadas: " + db.notificaciones.count());