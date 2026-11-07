# Запуск из корня проекта:  powershell -ExecutionPolicy Bypass -File .\docker-check.ps1
# Вывод каждой команды сохраняется в docs/docker/*.txt (это и есть отчёт для сдачи).

$ErrorActionPreference = "Continue"
$out = "docs/docker"
New-Item -ItemType Directory -Force $out | Out-Null

function Step($file, $cmd) {
    Write-Host "`n>>> $cmd" -ForegroundColor Cyan
    $text = cmd /c "$cmd 2>&1"
    $text | Write-Host
    ("$ $cmd", $text) | Out-File -Encoding utf8 "$out/$file"
}

cmd /c "docker rm -f ithub-api ithub-db >nul 2>&1"
cmd /c "docker network create ithub-net >nul 2>&1"

# База данных для API (PostgreSQL в отдельном контейнере)
cmd /c "docker run -d --name ithub-db --network ithub-net -e POSTGRES_DB=ithub_space -e POSTGRES_PASSWORD=132455 postgres:16-alpine"
Write-Host "Ждём готовности PostgreSQL..."
do { Start-Sleep 2; cmd /c "docker exec ithub-db pg_isready -U postgres >nul 2>&1" } until ($LASTEXITCODE -eq 0)

# 1. docker build
Step "01-docker-build.txt" "docker build -t ithub-space-api ."

# 2. docker run -p
Step "02-docker-run.txt" "docker run -d --name ithub-api --network ithub-net -p 8080:8080 -e SPRING_DATASOURCE_URL=jdbc:postgresql://ithub-db:5432/ithub_space ithub-space-api"

Write-Host "Ждём запуска Spring Boot..."
do { Start-Sleep 2; cmd /c "curl -s -o nul http://localhost:8080/api/health" } until ($LASTEXITCODE -eq 0)

# 3. docker ps
Step "03-docker-ps.txt" "docker ps"

# 4. Запросы к API снаружи контейнера
Step "04-api-health.txt" "curl -s http://localhost:8080/api/health"
Step "05-api-faculties.txt" "curl -s http://localhost:8080/api/faculties"

# 5. docker logs
Step "06-docker-logs.txt" "docker logs ithub-api"

Write-Host "`nГотово. Результаты в $out" -ForegroundColor Green
