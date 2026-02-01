# GitHub Actions CI/CD Setup Guide

## Overview

This project uses GitHub Actions for continuous integration and deployment, including:
- **SonarCloud** analysis for code quality
- **Unit tests** with JUnit
- **Code coverage** with JaCoCo
- **Build** and artifact packaging

## Workflow Trigger

The CI/CD pipeline runs automatically on:
- Every push to `main` branch
- Every pull request to `main` branch

## Setup Instructions

### 1. Configure SonarCloud

#### Option A: Using SonarCloud (Recommended for Public Repos)

1. **Sign up for SonarCloud**:
   - Go to https://sonarcloud.io
   - Click "Login" and authenticate with GitHub
   - Authorize SonarCloud to access your repositories

2. **Create Organization**:
   - Click "+" → "Analyze new project"
   - Import your GitHub organization
   - Organization key: `pavan-growfin` (or your organization)

3. **Set up Project**:
   - Select `cicd` repository
   - Click "Set Up"
   - Choose "With GitHub Actions"
   - Copy the SONAR_TOKEN

4. **Add Secret to GitHub**:
   - Go to your GitHub repo: Settings → Secrets and variables → Actions
   - Click "New repository secret"
   - Name: `SONAR_TOKEN`
   - Value: Paste the token from SonarCloud
   - Click "Add secret"

5. **Verify Configuration**:
   - Make a commit and push
   - Check the Actions tab to see the workflow run
   - View results in SonarCloud dashboard

#### Option B: Using Local SonarQube

If you prefer local SonarQube:

1. **Run SonarQube locally**:
   ```bash
   docker run -d --name sonarqube -p 9000:9000 sonarqube:latest
   ```

2. **Update workflow file** (`.github/workflows/ci.yml`):
   - Change `sonar.host.url` to `http://localhost:9000`
   - Update `sonar.projectKey` and `sonar.organization`

3. **Add SONAR_TOKEN**:
   - Generate token in SonarQube: My Account → Security → Generate Token
   - Add as GitHub secret (same as above)

### 2. Workflow Stages

The GitHub Actions workflow includes these stages:

1. **Checkout**: Clone the repository code
2. **Setup Java**: Install JDK 21
3. **Cache Dependencies**: Cache Maven and SonarCloud packages
4. **SonarCloud Analysis**: Run code quality checks (runs before tests)
5. **Unit Tests**: Execute JUnit tests
6. **Publish Test Results**: Display test results in GitHub Actions
7. **Package**: Build JAR artifact
8. **Upload Artifacts**: Store JAR for 30 days
9. **Code Coverage**: Generate JaCoCo coverage report

### 3. View Results

#### GitHub Actions
- Go to repository → **Actions** tab
- Click on the latest workflow run
- View logs, test results, and artifacts

#### SonarCloud Dashboard
- Visit https://sonarcloud.io
- Select your project
- View:
  - Code smells
  - Bugs
  - Security vulnerabilities
  - Code coverage
  - Duplications

#### Test Results
- Available in the Actions tab under "Maven Test Results"
- Shows passed/failed tests with details

#### Artifacts
- JAR files available in Actions → Workflow run → Artifacts
- Retained for 30 days

### 4. Code Coverage

The workflow includes JaCoCo for code coverage:
- Coverage report generated in `target/site/jacoco/`
- Integrated with SonarCloud
- Optional: Upload to Codecov (requires CODECOV_TOKEN)

To view locally:
```bash
mvn clean test jacoco:report
open target/site/jacoco/index.html
```

### 5. Badge Setup (Optional)

Add status badges to README:

```markdown
[![CI/CD Pipeline](https://github.com/pavan-growfin/cicd/actions/workflows/ci.yml/badge.svg)](https://github.com/pavan-growfin/cicd/actions/workflows/ci.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=pavan-growfin_cicd&metric=alert_status)](https://sonarcloud.io/dashboard?id=pavan-growfin_cicd)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=pavan-growfin_cicd&metric=coverage)](https://sonarcloud.io/dashboard?id=pavan-growfin_cicd)
```

## Comparison: GitHub Actions vs Jenkins

| Feature | GitHub Actions | Jenkins |
|---------|---------------|---------|
| **Trigger** | On push/PR (instant) | SCM polling (every 1 min) |
| **Environment** | Cloud runners | Local machine |
| **Setup** | Minimal (YAML file) | Requires configuration |
| **Cost** | Free for public repos | Free (self-hosted) |
| **SonarCloud** | ✅ Integrated | Manual setup |
| **Artifacts** | 30-day retention | Local storage |
| **Test Reports** | Built-in UI | Plugin required |

## Troubleshooting

### SonarCloud Analysis Fails
- Verify `SONAR_TOKEN` is added to GitHub secrets
- Check organization and project keys in `pom.xml`
- Ensure SonarCloud project is created

### Tests Not Running
- Check Java version in workflow matches `pom.xml`
- Verify Maven dependencies are correct
- Check workflow logs for detailed errors

### Coverage Not Showing
- Ensure JaCoCo plugin is in `pom.xml`
- Verify `jacoco.xml` path in SonarCloud properties
- Check if tests are actually running

### Workflow Not Triggering
- Verify workflow file is in `.github/workflows/`
- Check branch name matches trigger configuration
- Ensure workflow is enabled in Actions tab

## Local Testing

Test the workflow locally before pushing:

```bash
# Run all checks locally
mvn clean verify sonar:sonar \
  -Dsonar.projectKey=pavan-growfin_cicd \
  -Dsonar.organization=pavan-growfin \
  -Dsonar.host.url=https://sonarcloud.io \
  -Dsonar.token=YOUR_SONAR_TOKEN

# Run tests only
mvn clean test

# Generate coverage report
mvn jacoco:report
```

## Next Steps

1. Set up SonarCloud (follow steps above)
2. Add `SONAR_TOKEN` to GitHub secrets
3. Push a commit to trigger the workflow
4. View results in Actions tab and SonarCloud dashboard
5. (Optional) Add status badges to README
