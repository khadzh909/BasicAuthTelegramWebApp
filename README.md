# 🚀 Telegram Auth Service

Сервис авторизации через Telegram, реализованный с использованием Spring Boot. Пользователи проходят аутентификацию через Telegram Web Apps, после чего их данные сохраняются в базу данных PostgreSQL.

---

## 📦 Стек технологий

- Java 17
- Spring Boot
- Spring Data JPA (Hibernate)
- PostgreSQL
- Docker / Docker Compose
- Telegram Bot API

---



## ⚙️ Настройка окружения

### 1. Запуск PostgreSQL через Docker

Убедись, что у тебя установлен [Docker](https://www.docker.com/).

Запусти контейнер с базой данных:

```bash
docker-compose up -d

