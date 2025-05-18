# Etapa 1: build usando imagem do Maven
FROM maven:3.9.6-eclipse-temurin-17-alpine AS builder

# Cria diretório para a aplicação
WORKDIR /app

# Copia os arquivos do projeto
COPY . .

# Compila o projeto e gera um JAR
RUN mvn clean package -DskipTests

# Etapa 2: runtime usando imagem leve do JDK
FROM eclipse-temurin:17-jre-alpine

# Instala bash (necessário para -s /bin/bash funcionar)
RUN apk add --no-cache bash

# Cria usuário com diretório home e shell bash
RUN adduser -h /home/challenge -s /bin/bash -D challenge

# Diretório do app
WORKDIR /home/challenge

# Copia o JAR gerado da etapa de build
COPY --from=builder /app/target/*.jar app.jar

# Altera a permissão do JAR para o usuário
RUN chown challenge:challenge app.jar

# Alterna para o usuário não root
USER challenge

# Exposição da porta padrão do Spring Boot
EXPOSE 8080

# Comando para rodar o JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
