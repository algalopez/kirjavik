
# Clean and build
.PHONY: clean-build
clean-build:
	./gradlew clean build

.PHONY: sonar
sonar:
	./gradlew sonarqube
