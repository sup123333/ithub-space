# IT Hub Space — backend

> REST API единой информационной платформы колледжа IThub ДГТУ (Ростов-на-Дону).
> Командный проект учебной практики, 2026. Я — **backend-разработчик** (Java / Spring Boot).

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5-6DB33F)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6-6DB33F)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-14+-336791)
![Gradle](https://img.shields.io/badge/Gradle-8-02303A)

---

## Зачем это нужно

Информация о колледже была разбросана по сайту, ВКонтакте и Telegram, а портфолио студентов
и списка корпусов не было вовсе. IT Hub Space собирает всё в одном месте для трёх аудиторий:

- **абитуриенты** — направления, мероприятия, заявка на поступление;
- **студенты** — мероприятия, медиа, портфолио;
- **работодатели** — портфолио выпускников, партнёрская программа.

## Моя роль

В команде из 7 человек (team lead, аналитик, frontend, тестировщик, 2 дизайнера) я отвечал за серверную часть:

- спроектировал доменную модель и схему БД (JPA-сущности `User`, `Event`, `Faculty`, `Media`);
- реализовал слои `controller → service → repository` на Spring Boot;
- настроил Spring Security: хеширование паролей BCrypt, `UserDetailsService` на базе БД;
- вёл репозиторий: ветки `feature/*`, Conventional Commits, разрешение merge-конфликтов.

## Стек

| Слой | Технологии |
|------|-----------|
| Язык | Java 21 |
| Фреймворк | Spring Boot 3.5 (Web, Data JPA, Security, Validation) |
| БД | PostgreSQL, Hibernate |
| Сборка | Gradle |
| Прочее | Lombok, JUnit 5 |

## Архитектура

```
HTTP (JSON)
   │
Controller   — AuthController, EventController, FacultyController, MediaController
   │
Service      — UserService, EventService
   │
Repository   — Spring Data JPA
   │
PostgreSQL
```

```
src/main/java/ru/ithub/ithub_space/
├── config/        # SecurityConfig
├── controller/    # REST-контроллеры
├── service/       # бизнес-логика
├── repository/    # Spring Data JPA
└── model/         # JPA-сущности и enum Role
```

## API (реализовано)

| Метод | URL | Описание |
|-------|-----|----------|
| POST | `/api/auth/register` | Регистрация пользователя (пароль хешируется BCrypt) |
| GET | `/api/events` | Список мероприятий, отсортирован по дате |
| GET | `/api/events/{id}` | Мероприятие по id |
| POST / PUT / DELETE | `/api/events[/{id}]` | Создание, изменение, удаление мероприятия |
| GET | `/api/faculties[/{id}]` | Направления обучения |
| POST | `/api/faculties` | Добавить направление |
| GET | `/api/media?type=PHOTO\|VIDEO` | Медиа с фильтром по типу |
| POST | `/api/media` | Добавить медиафайл |

Полный план эндпоинтов, включая будущие, — в [api-plan.md](api-plan.md).
Архитектурные решения и компромиссы — в [project-notes.md](project-notes.md).

## Запуск

Нужны Java 21+ и PostgreSQL 14+.

```bash
git clone https://github.com/sup123333/ithub-space.git
cd ithub-space
```

```sql
CREATE DATABASE ithub_space;
```

Параметры подключения задаются переменными окружения (есть значения по умолчанию):

| Переменная | По умолчанию |
|------------|--------------|
| `DB_URL` | `jdbc:postgresql://localhost:5432/ithub_space` |
| `DB_USERNAME` | `postgres` |
| `DB_PASSWORD` | `postgres` |
| `SERVER_PORT` | `8080` |

```bash
./gradlew bootRun      # Linux / macOS
gradlew.bat bootRun    # Windows
```

Схема БД создаётся автоматически (`spring.jpa.hibernate.ddl-auto=update`).

## Roadmap

- [x] Доменная модель и CRUD для мероприятий, направлений, медиа
- [x] Регистрация с хешированием паролей
- [ ] Вход и выдача JWT, stateless-аутентификация
- [ ] Роль `ADMIN` для изменения контента, публичный доступ на чтение
- [ ] DTO и валидация входных данных вместо сущностей в контроллерах
- [ ] Глобальная обработка ошибок (`@RestControllerAdvice`), корректные 400/404
- [ ] Пагинация для `/api/events`
- [ ] Unit- и интеграционные тесты (Testcontainers)
- [ ] Swagger / OpenAPI, Docker Compose

## Команда

| Участник | Роль |
|----------|------|
| **Арнаутов Владимир** | **Backend** |
| Джамбулатович Алим | Frontend |
| Крупнов Георгий | Team Lead / PM |
| Шарафаненко Кристина | Аналитик |
| Канцян Артём | Тестировщик |
| Мусин Лев, Аллазов Алихан | Дизайн |

---

Учебный проект · АТК ДГТУ / IThub Ростов-на-Дону · 2026
