# Dockerfile para la base de datos MongoDB
FROM mongo:6.0

# Exponer puerto de MongoDB
EXPOSE 27017

# Variables de entorno para MongoDB
ENV MONGO_INITDB_ROOT_USERNAME=admin
ENV MONGO_INITDB_ROOT_PASSWORD=password123
ENV MONGO_INITDB_DATABASE=vehiculosdb

# Script de inicialización (opcional para crear usuario de aplicación)
COPY init-mongo.js /docker-entrypoint-initdb.d/init-mongo.js

# Comando por defecto de MongoDB
CMD ["mongod"]