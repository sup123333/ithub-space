# CRUD /api/events — примеры запросов

Команды для PowerShell (Windows). Используется `curl.exe`, а не `curl`: в Windows PowerShell 5.1 `curl` — это алиас `Invoke-WebRequest`.
Тела запросов лежат в [`requests/`](requests/) и передаются через `-d "@файл"`, поэтому JSON не нужно экранировать.

Все команды выполняются из корня проекта, стек поднят через `docker compose up -d --build`.

| Метод | URL | Доступ | Успех | Ошибки |
|-------|-----|--------|-------|--------|
| GET | `/api/events` | публично | 200 | — |
| GET | `/api/events/{id}` | публично | 200 | 404 |
| POST | `/api/events` | JWT, роль ADMIN | 201 | 403 без токена / не ADMIN |
| PUT | `/api/events/{id}` | JWT, роль ADMIN | 200 | 403, 404 |
| DELETE | `/api/events/{id}` | JWT, роль ADMIN | 204 | 403, 404 |

## 1. Регистрация и вход

```powershell
curl.exe -s -X POST http://localhost:8080/api/auth/register -H "Content-Type: application/json" -d "@docs/compose/requests/register.json"

$TOKEN = curl.exe -s -X POST http://localhost:8080/api/auth/login -H "Content-Type: application/json" -d "@docs/compose/requests/login.json"
```

`/api/auth/login` возвращает JWT строкой, она сохраняется в `$TOKEN`. Повторная регистрация того же email вернёт `409` — это нормально, просто выполните login.

## 2. Создание (POST → 201)

```powershell
curl.exe -s -i -X POST http://localhost:8080/api/events -H "Authorization: Bearer $TOKEN" -H "Content-Type: application/json" -d "@docs/compose/requests/event-create.json"
```

Из ответа запомните `id`, например `$ID = 6`.

## 3. Чтение (GET → 200)

```powershell
curl.exe -s http://localhost:8080/api/events
curl.exe -s -i http://localhost:8080/api/events/$ID
```

## 4. Обновление (PUT → 200)

```powershell
curl.exe -s -i -X PUT http://localhost:8080/api/events/$ID -H "Authorization: Bearer $TOKEN" -H "Content-Type: application/json" -d "@docs/compose/requests/event-update.json"
```

## 5. Удаление (DELETE → 204) и проверка (GET → 404)

```powershell
curl.exe -s -i -X DELETE http://localhost:8080/api/events/$ID -H "Authorization: Bearer $TOKEN"
curl.exe -s -i http://localhost:8080/api/events/$ID
```

## 6. Запись без токена (→ 403)

```powershell
curl.exe -s -i -X POST http://localhost:8080/api/events -H "Content-Type: application/json" -d "@docs/compose/requests/event-create.json"
```

## 7. Проверка сохранения данных

```powershell
curl.exe -s -X POST http://localhost:8080/api/events -H "Authorization: Bearer $TOKEN" -H "Content-Type: application/json" -d "@docs/compose/requests/event-persist.json"
docker compose down        # без -v: том db_data остаётся
docker compose up -d
curl.exe -s http://localhost:8080/api/events/$ID   # запись на месте
docker volume ls
```
