// Script de inicialización para MongoDB
// Crear usuario de aplicación específico para la base de datos de vehículos

// Cambiar a la base de datos de vehículos
db = db.getSiblingDB('vehiculosdb');

// Crear usuario de aplicación con permisos específicos
db.createUser({
  user: 'vehiculos_user',
  pwd: 'vehiculos_password',
  roles: [
    {
      role: 'readWrite',
      db: 'vehiculosdb'
    }
  ]
});

// Crear colección inicial con índice único en placa
db.createCollection('vehiculos');
db.vehiculos.createIndex({ "placa": 1 }, { unique: true });

// Insertar datos de ejemplo
db.vehiculos.insertMany([
  {
    marca: "Toyota",
    modelo: "Corolla",
    año: 2023,
    placa: "ABC123",
    precio: 25000.00,
    tipo: "sedan",
    disponible: true,
    fechaCreacion: new Date()
  },
  {
    marca: "Honda",
    modelo: "Civic",
    año: 2022,
    placa: "DEF456",
    precio: 22000.00,
    tipo: "sedan",
    disponible: true,
    fechaCreacion: new Date()
  },
  {
    marca: "Ford",
    modelo: "Explorer",
    año: 2023,
    placa: "GHI789",
    precio: 35000.00,
    tipo: "SUV",
    disponible: false,
    fechaCreacion: new Date()
  }
]);

print("Base de datos de vehículos inicializada correctamente");