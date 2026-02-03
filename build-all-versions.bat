@echo off
echo Building Oracle JDBC Tester with multiple driver versions...
echo.

echo [1/10] Building with Oracle JDBC 23.26.0.0.0 (23ai - Latest)...
call mvn clean package -Pojdbc-23.26.0.0.0
if %errorlevel% neq 0 exit /b %errorlevel%

echo.
echo [2/10] Building with Oracle JDBC 23.9.0.25.07...
call mvn clean package -Pojdbc-23.9.0.25.07
if %errorlevel% neq 0 exit /b %errorlevel%

echo.
echo [3/10] Building with Oracle JDBC 23.3.0.23.09...
call mvn clean package -Pojdbc-23.3.0.23.09
if %errorlevel% neq 0 exit /b %errorlevel%

echo.
echo [4/10] Building with Oracle JDBC 21.20.0.0 (21c - Latest)...
call mvn clean package -Pojdbc-21.20.0.0
if %errorlevel% neq 0 exit /b %errorlevel%

echo.
echo [5/10] Building with Oracle JDBC 21.13.0.0...
call mvn clean package -Pojdbc-21.13.0.0
if %errorlevel% neq 0 exit /b %errorlevel%

echo.
echo [6/10] Building with Oracle JDBC 21.1.0.0...
call mvn clean package -Pojdbc-21.1.0.0
if %errorlevel% neq 0 exit /b %errorlevel%

echo.
echo [7/10] Building with Oracle JDBC 19.29.0.0 (19c - Latest)...
call mvn clean package -Pojdbc-19.29.0.0
if %errorlevel% neq 0 exit /b %errorlevel%

echo.
echo [8/10] Building with Oracle JDBC 19.21.0.0...
call mvn clean package -Pojdbc-19.21.0.0
if %errorlevel% neq 0 exit /b %errorlevel%

echo.
echo [9/10] Building with Oracle JDBC 18.15.0.0 (18c - Latest)...
call mvn clean package -Pojdbc-18.15.0.0
if %errorlevel% neq 0 exit /b %errorlevel%

echo.
echo [10/10] Building with Oracle JDBC 12.2.0.1 (12c)...
call mvn clean package -Pojdbc-12.2.0.1
if %errorlevel% neq 0 exit /b %errorlevel%

echo.
echo ================================
echo All versions built successfully!
echo ================================
echo.
echo JARs created:
dir /b target\*.jar
