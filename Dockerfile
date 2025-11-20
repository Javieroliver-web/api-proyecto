# Etapa 1: Construcción
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
# Descarga dependencias
RUN mvn dependency:go-offline
COPY src ./src
# Compila el proyecto
RUN mvn clean package -DskipTests

# Etapa 2: Ejecución
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# --- CORRECCIÓN AQUÍ ---
# Usamos *.jar para que copie el archivo que haya generado Maven, 
# sin importar si se llama 'sprintix-api' o 'api-gateway'.
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]