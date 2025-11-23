@echo off
echo ====================================
echo    SISTEMA DE RECLAMOS
echo    Compilacion y Ejecucion
echo ====================================
echo.

REM Crear directorio bin si no existe
if not exist bin mkdir bin

REM Limpiar bin anterior para evitar conflictos
if exist bin\com rmdir /s /q bin\com

echo Compilando enums...
javac -d bin src\com\sistema\enums\*.java 2>nul
if %errorlevel% neq 0 (
    echo ❌ Error compilando enums
    goto :error
)

echo Compilando modelos...
javac -d bin -cp bin src\com\sistema\model\*.java 2>nul
if %errorlevel% neq 0 (
    echo ❌ Error compilando modelos
    goto :error
)

echo Compilando servicios...
javac -d bin -cp bin src\com\sistema\services\*.java 2>nul
if %errorlevel% neq 0 (
    echo ❌ Error compilando servicios
    goto :error
)

echo Compilando controladores...
javac -d bin -cp bin src\com\sistema\controllers\*.java 2>nul
if %errorlevel% neq 0 (
    echo ❌ Error compilando controladores
    goto :error
)

echo Compilando clase principal...
javac -d bin -cp bin src\com\sistema\SistemaReclamos.java 2>nul
if %errorlevel% neq 0 (
    echo ❌ Error compilando clase principal
    goto :error
)

echo.
echo ====================================
echo ✅ COMPILACION EXITOSA!
echo ====================================
echo.
echo 🚀 Ejecutando Sistema de Reclamos...
echo.

java -cp bin com.sistema.SistemaReclamos

goto :end

:error
echo.
echo ====================================
echo ❌ ERROR EN COMPILACION
echo ====================================
echo.
echo Verifica que todos los archivos Java esten en sus directorios correctos.
echo.
pause
exit /b 1

:end
echo.
echo ====================================
echo 👋 Sesion finalizada
echo ====================================
echo.
pause