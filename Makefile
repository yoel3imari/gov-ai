# Development commands
dev-up:
	docker compose -f docker-compose.dev.yml up -d --build

dev-down:
	docker compose -f docker-compose.dev.yml down

dev-logs:
	docker compose -f docker-compose.dev.yml logs -f

# Production commands
prod-up:
	docker compose up --build -d

prod-down:
	docker compose down

prod-logs:
	docker compose logs -f

# Build commands
build-backend:
	cd backend && ./gradlew build -x test

build-frontend:
	cd frontend && npm run build

# Clean commands
hard-clean:
	docker compose -f docker-compose.dev.yml down -v
	docker compose down -v
	docker system prune -f

# Database commands
db-migrate:
	docker compose -f docker-compose.dev.yml exec postgres psql -U $(DB_USERNAME) -d $(DB_NAME) -c "SELECT version();"

redis-cli:
	docker compose -f docker-compose.dev.yml exec redis redis-cli

# Test commands
test-backend:
	cd backend && ./gradlew test

test-frontend:
	cd frontend && npm test

# Full setup
setup: clean dev-up