# Spring Boot Backend

Port: 3001

Environment
- POSTGRES_URL
- POSTGRES_USER
- POSTGRES_PASSWORD

Run
- ./gradlew bootRun

Docker
- Build (recommended): docker build -t calendar-backend -f backend/Dockerfile backend
- Alternative (from backend dir): docker build -t calendar-backend .
- Run: docker run --rm -p 3001:3001 \
    -e POSTGRES_URL="jdbc:postgresql://db:5432/calendar" \
    -e POSTGRES_USER="postgres" \
    -e POSTGRES_PASSWORD="postgres" \
    --name calendar-backend calendar-backend

Notes
- The backend container does not assume or reference any database/db_visualizer paths.
- Provide the database connection via environment variables at runtime.

API
- POST /api/events
- GET /api/events/{id}
- GET /api/events?start=ISO&end=ISO
- PUT /api/events/{id}
- DELETE /api/events/{id}
