# Docker Setup Guide

This project is configured to automatically build and publish Docker images to GitHub Container Registry (GHCR).

## 🚀 Quick Start

### Pull and Run the Latest Image

```bash
docker pull ghcr.io/pavan-growfin/simple-java-test:latest
docker run ghcr.io/pavan-growfin/simple-java-test:latest
```

That's it! The image contains everything needed to run the application.

---

## 📦 Available Image Tags

GitHub Actions automatically tags images in multiple ways:

| Tag | Description | Example |
|-----|-------------|---------|
| `latest` | Latest build from main branch | `ghcr.io/pavan-growfin/simple-java-test:latest` |
| `main` | Current main branch | `ghcr.io/pavan-growfin/simple-java-test:main` |
| `main-SHA` | Specific commit from main | `ghcr.io/pavan-growfin/simple-java-test:main-a1b2c3d` |
| `pr-123` | Pull request builds | `ghcr.io/pavan-growfin/simple-java-test:pr-123` |

---

## 🔧 How It Works

### GitHub Actions Workflow

On every push to `main`:
1. ✅ Builds the JAR with Maven
2. ✅ Runs all tests
3. 🐳 Builds Docker image
4. 📤 Pushes to GitHub Container Registry

On pull requests:
1. ✅ Builds and tests
2. 🐳 Builds Docker image (doesn't push)

### Dockerfile Architecture

The project uses a **multi-stage build**:

```dockerfile
# Stage 1: Build with Maven + JDK (discarded)
FROM maven:3.9-eclipse-temurin-21 AS build
# ... build JAR ...

# Stage 2: Runtime with JRE only (final image)
FROM eclipse-temurin:21-jre-alpine
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
```

**Result:** Small, secure, production-ready image (~200MB)

---

## 🔐 Making Images Public

By default, GHCR images are private. To make them public:

1. Go to: https://github.com/users/pavan-growfin/packages/container/simple-java-test
2. Click "Package settings" (bottom right)
3. Scroll to "Danger Zone"
4. Click "Change visibility" → "Public"

Now anyone can pull your image without authentication!

---

## 💻 Local Development

### Build Locally

```bash
# Build the image
docker build -t simple-java-test:dev .

# Run it
docker run simple-java-test:dev
```

### Build with Custom Tag

```bash
docker build -t simple-java-test:v1.0.0 .
```

---

## 🔍 Inspect the Image

```bash
# See image details
docker inspect ghcr.io/pavan-growfin/simple-java-test:latest

# See image layers
docker history ghcr.io/pavan-growfin/simple-java-test:latest

# Check image size
docker images ghcr.io/pavan-growfin/simple-java-test
```

---

## 🐛 Troubleshooting

### "Permission denied" when pulling

**Problem:** Image is private and you're not authenticated.

**Solution:**
```bash
# Login to GHCR
echo $GITHUB_TOKEN | docker login ghcr.io -u YOUR-USERNAME --password-stdin

# Or make the image public (see above)
```

### "Image not found"

**Problem:** Image hasn't been built yet or wrong name.

**Solution:**
1. Check GitHub Actions completed successfully
2. Verify image name at: https://github.com/pavan-growfin?tab=packages

### Build fails in GitHub Actions

**Problem:** Missing permissions.

**Solution:**
1. Go to: Settings → Actions → General
2. Workflow permissions → Enable "Read and write permissions"
3. Save and re-run the workflow

---

## 📊 What Gets Built

```
Push to main → GitHub Actions runs:
├─ Maven build
├─ Run tests
├─ Docker build
│  ├─ Stage 1: Compile JAR (Maven + JDK)
│  └─ Stage 2: Package with JRE
└─ Push to ghcr.io/pavan-growfin/simple-java-test
   ├─ :latest
   ├─ :main
   └─ :main-{commit-sha}
```

---

## 🎯 Next Steps

Want to extend this setup? Common additions:

- **Docker Compose:** Run with databases/dependencies
- **Environment Variables:** Configure app via ENV vars
- **Health Checks:** Add Docker health checks
- **Multi-platform:** Build for ARM64 (Apple Silicon, etc.)

See `docker-compose.yml` (if you want to create it) for examples.
