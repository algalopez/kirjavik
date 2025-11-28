# kirjavik

Book lending application

## Description

## How to run

Start the environment

`docker compose -f environment/docker-compose.yml -p environmentup -d`

Run db migrations

`./gradlew migrateBackoffice`  
`./gradlew migrateHavn`  
`./gradlew migrateStats`

Build the app, run tests and execute sonar

`./gradlew clean build`

Run sonar

`Enter to localhost:34601, create a token and copy it to the build.gradle (or an env var)`

`./gradlew sonar`

Run app

`./gradlew quarkusDev -Dquarkus.profile=prod`

Links

|             |                                     |
|-------------|-------------------------------------|
| Quarkus dev | http://localhost:8080/q/dev/        |
| Swagger     | http://localhost:8080/q/swagger-ui/ |
| Sonarqube   | http://localhost:34601/             |
| RabbitMQ    | http://localhost:34607              |
| KurrentDB   | http://localhost:34605/ui/cluster   |
| Prometheus  | http://localhost:34603              |
| Grafana     | http://localhost:34604              |

