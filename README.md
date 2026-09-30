# inf-sec
Лабораторная работа №1 по дисциплине "Информационная безопасность".\
API поддерживает регистрацию, вход и получение списка пользователей.
Доступ к списку пользователей разрешён только после аутентификации
по JWT.
### Стек
Java 17, Spring Boot, Spring Security, PostgreSQL, Maven

## Описание API
Регистрация и вход доступны без JWT. Для получения списка пользователей
необходимо передать в заголовок токен.


| Метод | Путь | Доступ | Назначение |
|---|---|---|---|
| `POST` | `/auth/register` | Открыт | Регистрация пользователя и выдача JWT |
| `POST` | `/auth/login` | Открыт | Проверка логина и пароля, выдача JWT |
| `GET` | `/api/data` | JWT | Получение списка пользователей: ID и имя |

Для запросов /auth/register и /auth/login. Тело запроса — JSON:
#### `POST /auth/register`
```json
{
  "username": "timur",
  "password": "12345678"
}
```
#### `POST /auth/login`
```json
{
"username": "timur",
"password": "12345678"
}
```
#### GET `/api/data` - в заголовке задается Authorization
```http
Authorization: Bearer <token>
```

## Описание реализованных мер защиты
- **Защита от SQL-инъекций.** Для защиты от SQLi используется Spring Data JPA и Hibernate. Значения передаются как параметры запросов, без конкатенации SQL. Реализация: [UserRepository](src/main/java/io/github/timur0o31/lab1_inf/repository/UserRepository.java).
- Для защиты от XSS используется экранирование данных через HtmlUtils.htmlEscape(). Реализация: [UserService](src/main/java/io/github/timur0o31/lab1_inf/service/UserService.java)
- При регистрации пароль хэшируется с помощью BCrypt, и в бд сохраняется его хэш. Настройка алгоритма: [SecurityConfig](src/main/java/io/github/timur0o31/lab1_inf/security/SecurityConfig.java).
  Хэширование перед сохранением: [AuthService](src/main/java/io/github/timur0o31/lab1_inf/service/AuthService.java).
- Аутентификация. При входе AuthenticationManager проверяет учётные данные, после чего сервер выдаёт JWT с именем пользователя, временем выдачи и сроком действия. Токен подписывается алгоритмом HS256 с секретным ключом сервера.
Реализация : [AuthService](src/main/java/io/github/timur0o31/lab1_inf/service/AuthService.java), [JwtUtils](src/main/java/io/github/timur0o31/lab1_inf/security/JwtUtils.java).
- Фильтр JwtFilter через JwtUtils проверяет подпись и срок действия токена. Реализация: [JwtFilter](src/main/java/io/github/timur0o31/lab1_inf/security/JwtFilter.java), [SecurityConfig](src/main/java/io/github/timur0o31/lab1_inf/security/SecurityConfig.java).
## Скриншоты отчетов SAST/SCA
![Результат SAST-проверки](docs/images/sast.png)
![Результат SCA-проверки](docs/images/sca.png)


### Статус CI
[![CI](https://github.com/timur0o31/inf-sec/actions/workflows/ci.yml/badge.svg?branch=main)](https://github.com/timur0o31/inf-sec/actions/workflows/ci.yml)