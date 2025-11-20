# Dockerfile para la aplicación backend de vehículos
FROM eclipse-temurin:17-jdk

# Configurar variables de entorno
ENV JAVA_OPTS="-Xms512m -Xmx1024m"

# Crear directorio de trabajo
WORKDIR /app

# Copiar el archivo JAR generado por Maven
COPY target/vehiculos-api-1.0.0.jar app.jar

# Exponer puerto de la aplicación
EXPOSE 8080

# Ejecutar la aplicación
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]