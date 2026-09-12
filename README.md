# IT Hub Space

> Единая информационная платформа колледжа IThub ДГТУ Ростов-на-Дону

[![Java](https://img.shields.io/badge/Java-21-orange)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3-green)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-14+-blue)](https://www.postgresql.org/)

---

## О проекте

До IT Hub Space информация об IThub ДГТУ была разбросана по нескольким источникам:
официальный сайт, ВКонтакте, Telegram и по личному запросу.
Портфолио студентов и карта корпусов по России отсутствовали полностью.

**IT Hub Space** — веб-платформа, которая собирает всё в одном месте для трёх аудиторий:

- **Абитуриенты** — направления, форма поступления, FAQ, адреса корпусов
- **Студенты** — мероприятия, медиа, портфолио, преподаватели
- **Работодатели** — портфолио выпускников, партнёрская программа

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
- Git + GitHub
- IntelliJ IDEA, pgAdmin, Postman

---

## Быстрый старт

### Требования

- Java 21+
- PostgreSQL 14+
- Gradle 8+

### Установка

```bash
git clone https://github.com/sup123333/ithub-space.git
cd ithub-space
```

```sql
CREATE DATABASE ithub_space;
```

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/ithub_space
spring.datasource.username=postgres
spring.datasource.password=ВАШ_ПАРОЛЬ
```

```bash
./gradlew bootRun   # Linux / macOS
gradlew.bat bootRun # Windows
```

Открыть в браузере: `http://localhost:8080`

> При первом запуске Spring Boot автоматически создаёт схему БД и заполняет таблицы из `data.sql`.

---

## Архитектура

```
[Браузер]  →  Fetch API (JSON)
    ↓
[VIEW — index.html]   SPA · CSS Variables · Адаптивность
    ↓
[CONTROLLER — Spring Boot REST]
  AuthController · EventController · FacultyController
  BuildingController · MediaController · PortfolioController
  PartnerController · UserController
    ↓
[MODEL — Spring Data JPA]
  User · Event · Faculty · Building · Partner · Portfolio · Media
    ↓
[PostgreSQL — 7 таблиц · data.sql]
```

---

## Функциональность

| Раздел | Описание |
|--------|----------|
| Мероприятия | Список событий с датой, временем, локацией |
| Направления | 6 специальностей с описанием и куратором |
| Преподаватели | Скроллер, split-screen модальное окно |
| Корпуса | 16 объектов в 12 городах, поиск по городу/адресу |
| Поступить | Форма заявки + FAQ |
| Медиа | Фото и видео с фильтрацией |
| Портфолио | Студенческие проекты со ссылками |
| Партнёры | Компании-работодатели |
| Тема | Тёмная / Светлая, сохраняется в localStorage |

---

## Безопасность

Каждый запрос → `JwtFilter` → `SecurityContextHolder`

- Публичные эндпоинты: `/api/events`, `/api/buildings`, `/api/faculties` и др.
- Защищённые: `/api/users/me` — требуют `Authorization: Bearer <token>`
- Пароли хешируются через BCrypt

---

## Лицензия

Учебный проект. АТК ДГТУ / IThub Ростов-на-Дону, 2026.
