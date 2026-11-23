# ---------- Etapa 1: compilar con Maven ----------
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Primero solo el pom para aprovechar la caché de dependencias
COPY pom.xml .
RUN mvn -q dependency:go-offline

# Luego el código fuente
COPY src ./src
RUN mvn -q clean package -DskipTests

# ---------- Etapa 2: imagen liviana solo con el JRE ----------
FROM eclipse-temurin:21-jre
WORKDIR /app

# Usuario sin privilegios (buena práctica de seguridad)
RUN useradd -r -u 1001 spring
USER spring

COPY --from=build /app/target/libreria-api.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
