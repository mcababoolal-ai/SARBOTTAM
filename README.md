# School Management Platform

Runnable first vertical slice for the school-management microservice platform.

Included: Docker development dependencies, API Gateway, Identity Service, Student Service, PostgreSQL schema migrations, JWT validation through Keycloak, Kafka event publishing, and a React shell.

## Run locally

1. Install Java 21, Maven 3.9+, Docker Desktop, and Node.js 20+.
2. From this directory run `docker compose up -d`.
3. Start the backend in two terminals:
   - `mvn -pl identity-service spring-boot:run`
   - `mvn -pl student-service spring-boot:run`
   - `mvn -pl api-gateway spring-boot:run`
4. Start the UI:
   - `cd frontend`
   - `npm install`
   - `npm run dev`

To see the UI before setting up Keycloak, start student service with PowerShell: `$env:APP_SECURITY_ENABLED='false'; mvn -pl student-service spring-boot:run`. Then create students with the `X-School-Id` request header (the UI uses the example ID by default).

The services accept a JWT issued by `http://localhost:8081/realms/school`. Create the `school` realm and a test user in Keycloak, then add the user claims `school_id` and `roles` as described in `docs/manual-setup.md`.

For development-only API experiments, set `APP_SECURITY_ENABLED=false` before starting student-service. Do not use that setting outside local development.

## Test

`mvn -pl student-service test`

## Main routes

- Gateway: `http://localhost:8080`
- Student API: `http://localhost:8080/api/v1/students`
- Student API docs: `http://localhost:8083/swagger-ui/index.html`
- Identity API docs: `http://localhost:8084/swagger-ui/index.html`
- Keycloak: `http://localhost:8081`
- Kafka UI: `http://localhost:8082`

## Admission numbers

Student Service generates admission numbers when an admission is created. The format is `YYYYMMDDNNN`, where `NNN` starts at `001` for each school on each date. For example, the first two admissions on 10 September 2026 are `20260910001` and `20260910002`.

See `docs/manual-setup.md` for tasks that require your accounts or infrastructure access.

# How to run in short
1.   docker compose up -d 
2.   docker compose ps 
3.   $env:APP_SECURITY_ENABLED='false'
     mvn -pl identity-service clean spring-boot:run
4.   $env:APP_SECURITY_ENABLED='false'
     mvn -pl student-service clean spring-boot:run
5.   mvn -pl api-gateway clean spring-boot:run
6.   cd frontend
     npm run dev

