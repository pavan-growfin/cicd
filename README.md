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

**Status**: CI/CD pipeline configured with Jenkins

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
