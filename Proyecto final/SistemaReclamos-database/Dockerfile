FROM mongo:6.0

# Copiar el script de inicialización
COPY init-mongo.js /docker-entrypoint-initdb.d/

# Exponer el puerto por defecto de MongoDB
EXPOSE 27017

# Comando por defecto
CMD ["mongod"]