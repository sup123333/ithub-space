# ---------- Этап 1: сборка (build) ----------
# FROM - базовый образ с JDK 21 и Alpine Linux, нужен чтобы скомпилировать проект
FROM eclipse-temurin:21-jdk-alpine AS build

# WORKDIR - рабочая папка внутри контейнера, все следующие команды выполняются в ней
WORKDIR /app

# COPY - копируем файлы проекта внутрь образа
# сначала Gradle-обёртку и настройки сборки, затем исходники
COPY gradlew gradlew.bat ./
COPY gradle ./gradle
COPY build.gradle settings.gradle ./
COPY src ./src

# RUN - выполняется при сборке образа: собираем исполняемый jar Spring Boot
RUN chmod +x gradlew && ./gradlew bootJar --no-daemon

# ---------- Этап 2: запуск (runtime) ----------
# FROM - лёгкий образ только с JRE: в итоговом образе нет JDK и исходников
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# COPY --from=build - берём из первого этапа только готовый jar
COPY --from=build /app/build/libs/*.jar app.jar

# ENV - порт, на котором Spring Boot слушает внутри контейнера (server.port=${PORT})
ENV PORT=8080

# EXPOSE - документирует порт приложения; наружу его открывает docker run -p 8080:8080
EXPOSE 8080

# ENTRYPOINT - основная команда: контейнер = приложение, она выполняется всегда
ENTRYPOINT ["java", "-jar", "app.jar"]
