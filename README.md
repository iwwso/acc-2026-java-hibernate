# Practical Task: Library Book Tracker API

Starter project for Data Architecture practical task: Build a REST API for managing books in a library using Spring Boot, Spring Data JPA and an H2 database.

## Task
Complete all of the 'TODO' portions of the code. The finished application should compile and all of the controller endpoints should be functional.

## Behavioural Requirements
- Books can be created
- All books can be retrieved
- A single book can be retrieved by ID
- Books can be deleted
- Books can be filtered and searched by their fields.
- Controller delegates to Service, which delegates to Repository
- DTOs are used for requests and responses

## Stretch goals
- Request validation
- Custom exception handling
- Searching by partial title
- Add 'borrow' and 'return' endpoints along with the new 'borrowedStatus' field to the Book entity


## Security (branch `security`)

All endpoints require HTTP Basic authentication.

| User | Password | Role | Can do |
| --- | --- | --- | --- |
| `user` | `user` | USER | GET only |
| `admin` | `admin` | ADMIN | everything |


Passwords are stored BCrypt-encoded in an in-memory user store
(`config/SecurityConfig`), so they are never kept as plain text.

Rules:
- `GET /**` — any authenticated user
- everything else (POST, PUT, DELETE) — ADMIN only

No credentials: 401. Authenticated but not ADMIN: 403.

CSRF is disabled because this is a stateless REST API used with Basic Auth,
not a browser form. The H2 console needs `admin`, since it works over POST.

Run the security tests: `mvn test -Dtest=SecurityConfigTest`