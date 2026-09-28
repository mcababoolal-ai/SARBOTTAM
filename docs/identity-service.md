# Identity Service

Identity Service owns the school-level user profile, role assignment, permissions catalogue, and account status. Keycloak owns passwords, login pages, multi-factor authentication, token issuance, logout, and refresh tokens.

## Run locally

```powershell
docker compose up -d
$env:APP_SECURITY_ENABLED='false'
mvn -pl identity-service spring-boot:run
```

Identity Service runs on port `8084`; through the gateway use `http://localhost:8080/api/v1/identity`.

## APIs

Every tenant-specific request needs `X-School-Id`.

- `GET /api/v1/identity/roles`
- `GET /api/v1/identity/permissions`
- `GET /api/v1/identity/users`
- `POST /api/v1/identity/users`
- `PUT /api/v1/identity/users/{id}/roles`
- `PUT /api/v1/identity/users/{id}/status`

Example create request:

```json
{
  "username": "principal",
  "email": "principal@sarbottampragati.edu.in",
  "displayName": "School Principal",
  "keycloakSubject": "<Keycloak-user-id>",
  "roles": ["PRINCIPAL"]
}
```

## Required Keycloak configuration before real login

1. Create matching realm roles: `SCHOOL_ADMIN`, `PRINCIPAL`, `TEACHER`, `ACCOUNTANT`, `PARENT`, and `STUDENT`.
2. Create a Keycloak user, set its password, and grant its matching realm role.
3. Copy Keycloak's user ID into `keycloakSubject` when creating the school user profile.
4. Add a token mapper that emits `school_id` for the user. Services use this to isolate one school from another.
5. Set `APP_SECURITY_ENABLED=true` when Keycloak is configured. The React app should then use Keycloak's Authorization Code with PKCE flow rather than sending passwords to the application.

## React login setup

1. In Keycloak, create client `school-web` as a **public** client.
2. Enable **Standard Flow**, keep the client secret empty, and add valid redirect URI `http://localhost:5173/*`.
3. Set Web Origins to `http://localhost:5173`.
4. In `frontend`, copy `.env.example` to `.env`; set `VITE_AUTH_ENABLED=true`.
5. Run `npm install`, then `npm run dev`. The portal shows a secure **Log in** page and redirects to Keycloak. It sends the obtained access token to the gateway and services automatically.

The User Management screen creates the matching school profile and role assignment. Create the Keycloak account/password first, copy the Keycloak user ID, and enter it as `keycloakSubject` in that screen.

Do not store school user passwords in Identity Service or PostgreSQL.
