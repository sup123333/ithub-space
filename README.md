# IT Hub Space

> Единая информационная платформа колледжа IThub ДГТУ Ростов-на-Дону

[![Java](https://img.shields.io/badge/Java-21-orange)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3-green)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-14+-blue)](https://www.postgresql.org/)

---

## О проекте

До IT Hub Space информация об IThub ДГТУ была разбросана по четырём источникам: официальный сайт, ВКонтакте, Telegram и по запросу. Портфолио студентов и карта корпусов по России отсутствовали полностью.

**IT Hub Space** — веб-платформа, которая собирает всё в одном месте для трёх аудиторий:

- 🎓 **Абитуриенты** — направления, форма поступления, FAQ, адреса корпусов
- 📚 **Студенты** — мероприятия, медиа, портфолио, преподаватели
- 💼 **Работодатели** — портфолио выпускников, партнёрская программа

---

## Команда

| ФИО | Роль |
|-----|------|
| Арнаутов Владимир | Backend-разработчик |
| Джамбулатович Алим | Frontend-разработчик |
| Крупнов Георгий | Team Lead / PM |
| Шарафаненко Кристина | Аналитик |
| Канцян Артём | Тестировщик |
| Мусин Лев | Дизайнер |
| Аллазов Алихан | Дизайнер |

---

## Стек технологий

**Backend**
- Java 21 + Spring Boot 3
- Spring Security + JWT (HMAC-SHA256)
- Spring Data JPA + Hibernate + PostgreSQL
- HikariCP, BCrypt, Gradle

**Frontend**
- HTML5 + CSS3 + Vanilla JavaScript (без фреймворков)
- CSS Variables (тёмная/светлая тема)
- SPA — одностраничное приложение
- Адаптивная вёрстка: 1024px / 768px / 480px

**Инструменты**
- Git + GitHub (ветка `dev`)
- IntelliJ IDEA, pgAdmin, Postman

---

## Быстрый старт

### Требования

- Java 21+
- PostgreSQL 14+
- Gradle 8+

### Установка

```bash
# 1. Клонировать репозиторий
git clone https://github.com/sup123333/ithub-space.git
cd ithub-space
```

```sql
-- 2. Создать базу данных
CREATE DATABASE ithub_space;
```

```properties
# 3. Настроить src/main/resources/application.properties
spring.datasource.url=jdbc:postgresql://localhost:5432/ithub_space
spring.datasource.username=postgres
spring.datasource.password=ВАШ_ПАРОЛЬ
```

```bash
# 4. Запустить
./gradlew bootRun          # Linux / macOS
gradlew.bat bootRun        # Windows
```

```
# 5. Открыть в браузере
http://localhost:8080
```

> При первом запуске Spring Boot автоматически создаёт схему БД и заполняет таблицы из `data.sql`.

---

## Архитектура

```
[Браузер]
    │  Fetch API (JSON)
    ▼
[VIEW — index.html]
  SPA на Vanilla JS · CSS Variables · Адаптивность
    │
    ▼
[CONTROLLER — Spring Boot REST]
  AuthController · EventController · FacultyController
  BuildingController · MediaController · PortfolioController
  PartnerController · UserController
    │
    ▼
[MODEL — Spring Data JPA]
  User · Event · Faculty · Building · Partner · Portfolio · Media
    │
    ▼
[PostgreSQL]
  7 таблиц · data.sql
```

### Безопасность

Каждый запрос → `JwtFilter` → `SecurityContextHolder`

- Публичные эндпоинты: `/api/events`, `/api/buildings`, `/api/faculties` и др.
- Защищённые: `/api/users/me` — требуют `Authorization: Bearer <token>`
- Пароли: BCrypt

### Структура проекта

```
src/main/
├── java/ru/ithub/ithub_space/
│   ├── config/          # SecurityConfig, JwtFilter, JwtService, EncoderConfig
│   ├── controller/      # 8 REST-контроллеров
│   ├── model/           # JPA-сущности
│   ├── repository/      # Spring Data интерфейсы
│   └── service/         # UserService, EventService
└── resources/
    ├── application.properties   # Настройки БД, JWT, порт
    ├── data.sql                 # Начальные данные
    ├── logback-spring.xml       # Логирование
    └── static/
        └── index.html           # Весь фронтенд (SPA)
```

---

## REST API

| Метод | URL | Доступ | Описание |
|-------|-----|--------|----------|
| POST | `/api/auth/register` | Публичный | Регистрация |
| POST | `/api/auth/login` | Публичный | Вход, возврат JWT |
| GET | `/api/users/me` | 🔒 JWT | Профиль пользователя |
| GET | `/api/events` | Публичный | Мероприятия |
| GET | `/api/faculties` | Публичный | Направления |
| GET | `/api/media` | Публичный | Медиа |
| GET | `/api/partners` | Публичный | Партнёры |
| GET | `/api/portfolios` | Публичный | Портфолио |
| GET | `/api/buildings` | Публичный | Корпуса |
| GET | `/api/buildings/search?q=` | Публичный | Поиск корпусов |

### Пример запроса (авторизация)

```bash
# Получить токен
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"user@example.com","password":"password"}'

# Использовать токен
curl http://localhost:8080/api/users/me \
  -H "Authorization: Bearer <TOKEN>"
```

---

## Функциональность

| Раздел | Описание |
|--------|----------|
| Мероприятия | Список событий с датой, временем, локацией |
| Направления | 6 специальностей с описанием и куратором |
| Преподаватели | Скроллер, split-screen модальное окно |
| Корпуса | 16 объектов в 12 городах, поиск по городу/адресу |
| Поступить | Форма заявки + FAQ (10 вопросов) |
| Медиа | Фото и видео с фильтрацией |
| Портфолио | Студенческие проекты со ссылками |
| Партнёры | Компании-работодатели |
| Тема | 🌙 Тёмная / ☀️ Светлая, сохраняется в localStorage |
| Адаптивность | Полноценная мобильная версия с бургер-меню |

---

## Разработчику

### Git-workflow

```bash
# Получить актуальную ветку
git pull origin dev --rebase

# Сделать изменения и закоммитить
git add .
git commit -m "feat: описание изменений"
git push origin dev
```

Конвенция коммитов: `feat:` / `fix:` / `refactor:` / `docs:`

### Добавить новый эндпоинт

1. Создать `@Entity` класс в `model/`
2. Создать `JpaRepository<T, Long>` в `repository/`
3. Создать `@RestController` в `controller/`
4. Открыть в `SecurityConfig.java`: `.requestMatchers("/api/new/**").permitAll()`

### Добавить данные

Отредактируйте `src/main/resources/data.sql`, добавьте INSERT с `ON CONFLICT DO NOTHING`. Данные загрузятся при следующем перезапуске.

### Тестирование API в Postman

1. `POST /api/auth/login` → скопировать `token`
2. В заголовках добавить: `Authorization: Bearer <token>`
3. Выполнять защищённые запросы

### Логи

```
logs/ithub.log   — текущий день
logs/ithub.YYYY-MM-DD.log   — архив
```

---

## Тестирование

- **28 тест-кейсов** (Postman)
- Позитивные, негативные и граничные сценарии
- Найдено и исправлено **4 бага**

---

## Лицензия

Учебный проект. АТК ДГТУ / IThub Ростов-на-Дону, 2026.