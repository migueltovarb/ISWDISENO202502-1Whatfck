@echo off
echo ====================================
echo    SISTEMA DE RECLAMOS - INICIO
echo ====================================
echo.

REM Verificar si Docker está ejecutándose
docker info >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ ERROR: Docker no está ejecutándose
    echo 💡 Por favor, inicia Docker Desktop y vuelve a intentar
    echo.
    pause
    exit /b 1
)

echo ✅ Docker está ejecutándose
echo.

REM Iniciar base de datos MongoDB
echo 🚀 Iniciando base de datos MongoDB...
docker-compose up -d

if %errorlevel% neq 0 (
    echo ❌ ERROR: No se pudo iniciar la base de datos
    echo.
    pause
    exit /b 1
)

echo ✅ Base de datos iniciada correctamente
echo.

REM Esperar a que MongoDB esté listo
echo ⏳ Esperando a que MongoDB esté listo...
timeout /t 5 /nobreak >nul

REM Verificar conexión a MongoDB
echo 🔍 Verificando conexión a MongoDB...
docker exec reclamos-mongodb mongosh --eval "db.adminCommand('ping')" >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ ERROR: No se puede conectar a MongoDB
    echo 💡 Revisa que el puerto 27018 no esté ocupado
    echo.
    docker-compose logs mongodb-reclamos
    echo.
    pause
    exit /b 1
)

echo ✅ MongoDB está listo y funcionando
echo.

REM Cambiar al directorio del proyecto
cd SistemaReclamos

REM Crear el JAR con dependencias si no existe
if not exist target\sistema-reclamos-1.0.0.jar (
    echo 🔨 Creando JAR con dependencias...
    mvn package -q
    if %errorlevel% neq 0 (
        echo ❌ ERROR: Falló la creación del JAR
        echo.
        pause
        exit /b 1
    )
    echo ✅ JAR creado correctamente
    echo.
)

REM Ejecutar la aplicación
echo 🎮 Iniciando Sistema de Reclamos...
echo.
echo ====================================
echo    ¡BIENVENIDO AL SISTEMA!
echo ====================================
echo.
echo 💡 Usuarios de prueba disponibles:
echo    Cliente: juan@email.com / maria@email.com
echo    Soporte: carlos@email.com / ana@email.com
echo    (Cualquier contraseña)
echo.
echo ====================================
echo.

java -jar target\sistema-reclamos-1.0.0.jar

echo.
echo ====================================
echo    ¡Gracias por usar el sistema!
echo ====================================
echo.
pause