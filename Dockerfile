# =========================================================
# ETAPA 1: Compilar el proyecto Java con Maven
# =========================================================
FROM maven:3.9-eclipse-temurin-17 AS builder

# Creamos el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiamos el archivo pom.xml y el código fuente
COPY pom.xml .
COPY src ./src

# Compilamos el proyecto para generar el archivo .war
RUN mvn clean package -DskipTests

# =========================================================
# ETAPA 2: Servidor Tomcat de producción
# =========================================================
FROM tomcat:9-jdk17-corretto

# Eliminamos la app por defecto de Tomcat
RUN rm -rf /usr/local/tomcat/webapps/ROOT

# Copiamos el .war recién generado en la ETAPA 1 dentro de Tomcat
COPY --from=builder /app/target/*.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080
CMD ["catalina.sh", "run"]