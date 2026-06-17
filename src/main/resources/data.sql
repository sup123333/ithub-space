-- ============================================================
--  IT Hub Space — демо-данные (реальные данные колледжа)
-- ============================================================

-- ── USERS ──────────────────────────────────────────────────
INSERT INTO users (first_name, last_name, middle_name, email, password, city, direction, group_name, subject, role)
VALUES
  ('Мария',    'Кузнецова', 'Петровна', 'kuznetsova@ithub.ru', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Ростов-на-Дону', null,              null,           null,                           'ADMIN'),
  ('Иван',     'Сериков',   null,       'serikov@ithub.ru',    '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Ростов-на-Дону', 'Разработка',      null,           'HTML/CSS',                     'TEACHER'),
  ('Айшат',   'Юсупова',   null,       'yusupova@ithub.ru',   '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Ростов-на-Дону', 'Программирование', null,           'СУБД (PostgreSQL, MySQL)',      'TEACHER'),
  ('Вероника', 'Инаркаева', null,       'inarkayeva@ithub.ru', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Ростов-на-Дону', 'Дизайн',          null,           'Основные принципы UX/UI',      'TEACHER'),
  ('Владимир', 'Арнаутов',  'Тестович', 'test@ithub.ru',       '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Ростов-на-Дону', 'Backend',         'Разработка-24', null,                          'STUDENT'),
  ('Мария',    'Фудулей',   null,       'fudulei@ithub.ru',    '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Ростов-на-Дону', 'Программирование', null,           'Введение в программирование',  'TEACHER')
ON CONFLICT (email) DO NOTHING;

-- ── FACULTIES ──────────────────────────────────────────────
INSERT INTO faculties (name, description, head_name, student_count, image_url)
SELECT * FROM (VALUES
  ('Разработка'::varchar,       'Специальность 09.02.11 — Разработка и управление программным обеспечением. Бизнес-роль: Веб-разработчик. Студенты осваивают HTML, CSS, JavaScript, работу с API и основы бэкенд-разработки.'::text,                                                                                                                      'Иван Сериков'::varchar,       85::integer, null::varchar),
  ('Программирование'::varchar, 'Специальность 09.02.11 — Разработка и управление программным обеспечением. Бизнес-роли: Java-разработчик, .NET-разработчик. Углублённое изучение объектно-ориентированного программирования, баз данных и архитектуры ПО.'::text,                                                                                      'Айшат Юсупова'::varchar,      72::integer, null::varchar),
  ('Кибербезопасность'::varchar,'Специальность 10.02.05 — Информационная безопасность и системное администрирование. Бизнес-роль: Специалист по кибербезопасности. Защита информации, сетевая безопасность, администрирование систем.'::text,                                                                                                           'Елена Дозорова'::varchar,     48::integer, null::varchar),
  ('Цифровой маркетинг'::varchar,'Специальность 42.02.01 — Реклама. Бизнес-роли: Специалист по продвижению в социальных сетях, Специалист по интернет-маркетингу. SMM, таргетированная реклама, аналитика.'::text,                                                                                                                                     'Виктория Колесникова'::varchar,65::integer, null::varchar),
  ('Контент-маркетинг'::varchar, 'Специальность 42.02.01 — Реклама. Бизнес-роли: Специалист по созданию контента, Дизайнер маркетинговых коммуникаций. Создание текстов, визуала и медиаконтента для брендов.'::text,                                                                                                                                  'Виктория Колесникова'::varchar,58::integer, null::varchar),
  ('Бренд-маркетинг'::varchar,  'Специальность 42.02.01 — Реклама. Бизнес-роли: Бренд-менеджер, Менеджер проектов. Стратегия бренда, управление коммуникациями и проектами.'::text,                                                                                                                                                                     'Виктория Колесникова'::varchar,42::integer, null::varchar)
) AS v(name, description, head_name, student_count, image_url)
WHERE NOT EXISTS (SELECT 1 FROM faculties LIMIT 1);

-- ── EVENTS ─────────────────────────────────────────────────
INSERT INTO events (title, description, event_date, location, image_url, program, author_id)
SELECT * FROM (VALUES
  ('Хакатон IThub 2026'::varchar,
   'Главное соревнование колледжа для студентов направлений Разработки и Программирования. 24 часа непрерывной работы в командах, менторы-преподаватели колледжа, призы лучшим командам.'::text,
   '2026-07-15 10:00:00'::timestamp, 'IThub Ростов-на-Дону, пл. Гагарина, д. 1'::varchar, null::varchar,
   '10:00 — Открытие, формирование команд
11:00 — Старт разработки
День 2, 09:00 — Финальная защита проектов
11:00 — Награждение победителей'::text,
   1::bigint),

  ('День открытых дверей IThub'::varchar,
   'Познакомься с колледжем изнутри: экскурсия по пространству, встреча со студентами и преподавателями, презентации всех специальностей. Узнай всё о поступлении на 2026–2027 учебный год.'::text,
   '2026-07-01 11:00:00'::timestamp, 'IThub Ростов-на-Дону, пл. Гагарина, д. 1'::varchar, null::varchar,
   '11:00 — Приветствие от директора Марии Кузнецовой
11:30 — Презентации специальностей
13:00 — Экскурсия по колледжу
14:00 — Q&A с преподавателями и студентами'::text,
   1::bigint),

  ('Воркшоп: HTML/CSS с нуля до первого сайта'::varchar,
   'Открытый практический воркшоп от преподавателя Ивана Серикова. За 3 часа создашь свою первую веб-страницу с нуля. Подходит для всех — опыт не нужен.'::text,
   '2026-06-28 14:00:00'::timestamp, 'IThub Ростов-на-Дону, Компьютерный класс'::varchar, null::varchar,
   '14:00 — Введение: как работает браузер
14:30 — HTML: структура страницы
15:30 — CSS: стили и верстка
16:30 — Практика: собираем страницу
17:00 — Готовый результат и обратная связь'::text,
   2::bigint),

  ('Митап: Карьера после IThub — куда идут выпускники'::varchar,
   'Встреча с выпускниками колледжа, которые уже работают в IT. Расскажут о первом опыте, стажировках, портфолио и о том, как попасть в хорошую компанию сразу после колледжа.'::text,
   '2026-08-15 18:00:00'::timestamp, 'IThub Ростов-на-Дону, пл. Гагарина, д. 1'::varchar, null::varchar,
   '18:00 — Истории выпускников (3 спикера)
19:00 — Разбор портфолио
19:30 — Нетворкинг'::text,
   1::bigint),

  ('Открытый урок: основы кибербезопасности'::varchar,
   'Преподаватель направления Кибербезопасность Елена Дозорова проведёт открытый урок по управлению веб-серверами и базовой защите приложений.'::text,
   '2026-07-10 16:00:00'::timestamp, 'IThub Ростов-на-Дону, Аудитория №3'::varchar, null::varchar,
   '16:00 — Что такое кибербезопасность в 2026 году
16:45 — Разбор реальных уязвимостей
17:30 — Практика: настройка защиты сервера'::text,
   1::bigint)
) AS v(title, description, event_date, location, image_url, program, author_id)
WHERE NOT EXISTS (SELECT 1 FROM events LIMIT 1);

-- ── PARTNERS ───────────────────────────────────────────────
INSERT INTO partners (company_name, contact_person, email, phone, description, logo_url)
SELECT * FROM (VALUES
  ('ДГТУ — Донской государственный технический университет'::varchar, 'Приёмная комиссия'::varchar, 'priem.rostov@ithub.ru'::varchar, '+7 (863) 2-738-667'::varchar, 'Базовый партнёр колледжа IThub. Колледж работает на базе ДГТУ, студенты получают диплом государственного образца. Адрес: Ростов-на-Дону, пл. Гагарина, д. 1.'::text, null::varchar),
  ('IThub — головная сеть колледжей'::varchar,                        'Михаил Сумбатян'::varchar,   'info@ithub.ru'::varchar,          null::varchar,               'Основатель и генеральный директор ГК IThub. Сеть колледжей информационных и креативных технологий международного уровня. Бизнес-ориентированное образование нового поколения.'::text, null::varchar),
  ('Яндекс'::varchar,                                                  null::varchar,                 null::varchar,                     null::varchar,               'Технологический партнёр. Студенты направления Разработка проходят практику и используют инструменты Яндекса. Лучшие выпускники получают предложения о стажировке.'::text, null::varchar),
  ('СберТех'::varchar,                                                 null::varchar,                 null::varchar,                     null::varchar,               'Партнёр по трудоустройству. Предоставляет стажировки студентам направлений Программирование и Кибербезопасность.'::text, null::varchar),
  ('Ростелеком'::varchar,                                              null::varchar,                 null::varchar,                     '+7 (863) 210-00-10'::varchar,'Региональный партнёр в сфере телекоммуникаций и кибербезопасности. Совместные проекты по защите информационных систем.'::text, null::varchar)
) AS v(company_name, contact_person, email, phone, description, logo_url)
WHERE NOT EXISTS (SELECT 1 FROM partners LIMIT 1);

-- ── MEDIA ──────────────────────────────────────────────────
INSERT INTO media (url, title, type, created_at, author_id)
SELECT * FROM (VALUES
  ('https://rostov.ithub.ru/media/campus-1.jpg'::varchar,          'Пространство для учёбы и креатива — зал 1'::varchar,     'PHOTO'::varchar, '2025-09-01 10:00:00'::timestamp, 1::bigint),
  ('https://rostov.ithub.ru/media/campus-2.jpg'::varchar,          'Рабочее пространство студентов IThub'::varchar,          'PHOTO'::varchar, '2025-09-01 10:15:00'::timestamp, 1::bigint),
  ('https://rostov.ithub.ru/media/campus-3.jpg'::varchar,          'Компьютерный класс — направление Разработка'::varchar,   'PHOTO'::varchar, '2025-09-01 10:30:00'::timestamp, 1::bigint),
  ('https://rostov.ithub.ru/media/hackathon-2025.jpg'::varchar,    'Хакатон IThub 2025 — финальная защита'::varchar,         'PHOTO'::varchar, '2025-11-20 15:00:00'::timestamp, 1::bigint),
  ('https://rostov.ithub.ru/media/open-day-2025.jpg'::varchar,     'День открытых дверей — презентация специальностей'::varchar,'PHOTO'::varchar,'2025-10-10 13:00:00'::timestamp,1::bigint),
  ('https://rostov.ithub.ru/media/workshop-html.jpg'::varchar,     'Воркшоп HTML/CSS — студенты за работой'::varchar,        'PHOTO'::varchar, '2025-12-05 15:00:00'::timestamp, 2::bigint),
  ('https://rostov.ithub.ru/media/promo-2026.mp4'::varchar,        'IThub Ростов — промо-ролик 2026'::varchar,               'VIDEO'::varchar, '2026-01-15 09:00:00'::timestamp, 1::bigint),
  ('https://rostov.ithub.ru/media/director-welcome.mp4'::varchar,  'Приветствие директора Марии Кузнецовой'::varchar,        'VIDEO'::varchar, '2026-02-01 10:00:00'::timestamp, 1::bigint),
  ('https://rostov.ithub.ru/media/hackathon-recap-2025.mp4'::varchar,'Итоги хакатона IThub 2025 — видеоотчёт'::varchar,     'VIDEO'::varchar, '2025-11-25 12:00:00'::timestamp, 1::bigint)
) AS v(url, title, type, created_at, author_id)
WHERE NOT EXISTS (SELECT 1 FROM media LIMIT 1);

-- ── PORTFOLIOS ─────────────────────────────────────────────
INSERT INTO portfolios (title, description, project_url, image_url, student_id)
SELECT * FROM (VALUES
  ('IT Hub Space — сайт колледжа'::varchar,               'Интерактивный сайт-хаб IThub ДГТУ для студентов, преподавателей и работодателей. Backend на Spring Boot + JWT, фронтенд на ванильном JS. Разработан командой из 5 студентов за 5 дней в рамках учебного хакатона.'::text,                  'https://github.com/ithub-space/ithub-backend'::varchar,          null::varchar, 5::bigint),
  ('Телеграм-бот приёмной комиссии IThub'::varchar,       'Бот на Python (aiogram) для автоматических ответов на вопросы абитуриентов: специальности, условия поступления, расписание дней открытых дверей. Снизил нагрузку на менеджеров на 40%.'::text,                                             'https://github.com/ithub-rostov/admission-bot'::varchar,         null::varchar, 5::bigint),
  ('Лендинг для студенческого проекта SmartCity'::varchar,'Одностраничный сайт для презентации студенческого проекта умного города. Адаптивная вёрстка на HTML/CSS, анимации на JS, форма обратной связи.'::text,                                                                                        'https://github.com/ithub-rostov/smartcity-landing'::varchar,     null::varchar, 5::bigint),
  ('База данных учебного расписания IThub'::varchar,      'Проектирование и реализация базы данных расписания занятий на PostgreSQL. Включает схему, хранимые процедуры и представления для формирования отчётов.'::text,                                                                                 'https://github.com/ithub-rostov/schedule-db'::varchar,           null::varchar, 5::bigint),
  ('UI Kit в стиле IThub'::varchar,                       'Библиотека компонентов на CSS-переменных в фирменном тёмном стиле колледжа: кнопки, формы, карточки, навигация, модальные окна. Используется в учебных проектах студентов.'::text,                                                           'https://github.com/ithub-rostov/ithub-ui-kit'::varchar,          null::varchar, 5::bigint),
  ('Анализ вакансий IT-рынка Ростова-на-Дону'::varchar,  'Исследовательский проект: парсинг hh.ru на Python, анализ 3000+ вакансий за 2025 год, визуализация трендов на Matplotlib. Вывод: самые востребованные навыки — Python, Java, SQL.'::text,                                                    'https://github.com/ithub-rostov/hh-research'::varchar,           null::varchar, 5::bigint)
) AS v(title, description, project_url, image_url, student_id)
WHERE NOT EXISTS (SELECT 1 FROM portfolios LIMIT 1);
