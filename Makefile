.PHONY: run-dev infra-up infra-down infra-stop

infra-up:
	docker-compose up -d postgres redis

infra-down:
	docker-compose down

infra-stop:
	docker-compose stop postgres redis

run-dev: infra-up
	export $$(grep -v '^#' .env | xargs) && ./mvnw spring-boot:run