# Java CI/CD Project

A simple Java project demonstrating Maven build and Jenkins CI/CD pipeline integration.

## Project Overview

This project contains:
- Simple Calculator application
- JUnit 5 test suite
- Maven build configuration
- Jenkins pipeline with automated testing

## Repository

**GitHub**: https://github.com/pavan-growfin/cicd

**Status**: CI/CD pipeline configured with Jenkins and GitHub Actions

## Quick Start

### Build the Project
```bash
mvn clean install
```

### Run Tests
```bash
mvn test
```

### Run Application
```bash
mvn clean compile
java -cp target/classes com.example.Calculator
```

## Docker

Docker images are automatically built and pushed to GitHub Container Registry on every push to main.

### Pull and Run Docker Image

```bash
# Pull the latest image
docker pull ghcr.io/pavan-growfin/simple-java-test:latest

# Run the application
docker run ghcr.io/pavan-growfin/simple-java-test:latest
```

### Run Specific Version

```bash
# Pull a specific commit version
docker pull ghcr.io/pavan-growfin/simple-java-test:main-abc123

# Run it
docker run ghcr.io/pavan-growfin/simple-java-test:main-abc123
```

### Build Docker Image Locally

```bash
# Build the image
docker build -t simple-java-test:local .

# Run locally built image
docker run simple-java-test:local
```

## Jenkins CI/CD

The project includes automated Jenkins pipeline that:
- Builds on every commit (via SCM polling)
- Runs unit tests
- Packages JAR artifacts
- Publishes test results

See `LOCAL_JENKINS_SETUP.md` for detailed Jenkins configuration.

## Project Structure

```
.
├── src/
│   ├── main/java/com/example/
│   │   └── Calculator.java
│   └── test/java/com/example/
│       └── CalculatorTest.java
├── pom.xml
├── Jenkinsfile
└── README.md
```
