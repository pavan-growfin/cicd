# Local Jenkins Setup Guide

## Current Configuration

The Jenkinsfile is configured for **local Jenkins** using **SCM polling** instead of webhooks.

- **Polling Interval**: Every 2 minutes
- **No webhook required**: Works without exposing Jenkins to the internet

## Quick Setup Steps

### 1. Start Jenkins Locally

If not already running:
```bash
# If installed via Homebrew
brew services start jenkins-lts

# Or run manually
java -jar jenkins.war
```

Access Jenkins at: http://localhost:8080

### 2. Configure Tools in Jenkins

1. Go to `Manage Jenkins` → `Global Tool Configuration`

2. **Add Maven**:
   - Name: `Maven 3.9.12`
   - Install automatically OR point to: `/opt/homebrew/opt/maven`

3. **Add JDK**:
   - Name: `JDK-11`
   - Install automatically OR point to your Java home

### 3. Create Pipeline Job

1. Click `New Item`
2. Name: `cicd-pipeline`
3. Type: `Pipeline`
4. Click `OK`

5. Configure:
   - **Build Triggers**:
     - ☑ Poll SCM
     - Schedule: `H/2 * * * *` (every 2 minutes)

   - **Pipeline**:
     - Definition: `Pipeline script from SCM`
     - SCM: `Git`
     - Repository URL: `https://github.com/pavan-growfin/cicd.git`
     - Branch: `*/main`
     - Script Path: `Jenkinsfile`

6. Click `Save`

### 4. Test the Pipeline

**Option 1: Manual Trigger**
- Click `Build Now` to test immediately

**Option 2: Wait for Polling**
- Make a code change and commit
- Wait up to 2 minutes
- Jenkins will automatically detect and build

## How It Works

1. Jenkins polls GitHub every 2 minutes
2. If changes are detected, build triggers automatically
3. Pipeline runs:
   - Checkout code
   - Build with Maven
   - Run unit tests
   - Package JAR
   - Archive artifacts

## Optional: Use Webhooks with ngrok

If you want instant builds (webhook-based), you can expose local Jenkins:

### Install ngrok
```bash
brew install ngrok
```

### Expose Jenkins
```bash
ngrok http 8080
```

### Configure Webhook
1. Note the ngrok URL (e.g., `https://abc123.ngrok.io`)
2. In GitHub repo settings:
   - Add webhook: `https://abc123.ngrok.io/github-webhook/`
3. Update Jenkinsfile:
   - Uncomment: `githubPush()`
   - Comment out: `pollSCM('H/2 * * * *')`

## Troubleshooting

### Build Not Triggering
- Check Jenkins logs: `Manage Jenkins` → `System Log`
- Verify polling is enabled in job configuration
- Ensure repository URL is correct

### Maven Not Found
- Verify Maven tool name matches exactly: `Maven 3.9.12`
- Check Maven installation: `mvn -version` in terminal

### Tests Failing
- Check test results in build page
- Review console output for detailed errors

## Repository

- **GitHub**: https://github.com/pavan-growfin/cicd
- **Polling Frequency**: Every 2 minutes
- **Branch**: main
