# Jenkins Pipeline Setup Guide

## Prerequisites

1. Jenkins server installed and running
2. Required Jenkins plugins:
   - Pipeline Plugin
   - Git Plugin
   - JUnit Plugin
   - Maven Integration Plugin
   - GitHub Plugin (for webhook support)

## Jenkins Configuration

### 1. Configure Maven Tool

1. Navigate to: `Manage Jenkins` → `Global Tool Configuration`
2. Scroll to `Maven` section
3. Click `Add Maven`
   - Name: `Maven 3.9.12`
   - Install automatically (or specify Maven home path)

### 2. Configure JDK

1. In the same `Global Tool Configuration` page
2. Scroll to `JDK` section
3. Click `Add JDK`
   - Name: `JDK-11`
   - Install automatically (or specify JDK home path)

## Creating the Pipeline Job

### Step 1: Configure GitHub Webhook

1. In your GitHub repository:
   - Go to `Settings` → `Webhooks` → `Add webhook`
   - Payload URL: `http://your-jenkins-url/github-webhook/`
   - Content type: `application/json`
   - Events: Select `Just the push event`
   - Click `Add webhook`

### Step 2: Create Jenkins Pipeline Job

1. Click `New Item` in Jenkins
2. Enter job name: `simple-java-test-pipeline`
3. Select `Pipeline` and click `OK`
4. In the configuration page:
   - Under `Build Triggers`:
     - ☑ GitHub hook trigger for GITScm polling
   - Under `Pipeline`:
     - Definition: `Pipeline script from SCM`
     - SCM: `Git`
     - Repository URL: Enter your Git repository URL
     - Branch: `*/main` (or your branch name)
     - Script Path: `Jenkinsfile`
5. Click `Save`

### Alternative: Using SCM Polling

If webhooks are not available, you can use SCM polling instead:
1. In Jenkinsfile, change triggers section:
   - Comment out: `githubPush()`
   - Uncomment: `pollSCM('H/5 * * * *')`
2. In Jenkins job configuration:
   - Under `Build Triggers`:
     - ☑ Poll SCM
     - Schedule: `H/5 * * * *` (every 5 minutes)

## Pipeline Stages

The pipeline includes the following stages:

1. **Checkout**: Checks out source code from SCM
2. **Build**: Compiles the Java code using Maven
3. **Unit Tests**: Runs JUnit tests and publishes results
4. **Package**: Creates JAR file
5. **Archive Artifacts**: Stores build artifacts

## Triggering Options

### GitHub Webhook (Current Configuration)
- Triggers build immediately when code is pushed to GitHub
- Better performance than polling
- Requires GitHub webhook setup (see Step 1 above)
- Current trigger: `githubPush()`

### SCM Polling (Alternative)
- Polls repository every 5 minutes for changes
- Use when webhook is not available
- To enable: Comment out `githubPush()` and uncomment `pollSCM('H/5 * * * *')` in Jenkinsfile

### Manual Trigger
- Click `Build Now` in Jenkins job page anytime

## Test Reports

After each build:
- View test results in the job's `Test Result` page
- Failed tests are highlighted
- Test trends are displayed over time

## Notifications (Optional)

To enable email notifications:
1. Configure email settings in `Manage Jenkins` → `Configure System`
2. Uncomment the `emailext` blocks in the `post` section
3. Replace `your-email@example.com` with actual email addresses

## Troubleshooting

### Build Fails with "Maven not found"
- Verify Maven tool name matches in Jenkinsfile and Global Tool Configuration
- Check Maven installation path

### Tests Not Publishing
- Ensure JUnit plugin is installed
- Verify test reports are generated in `target/surefire-reports/`

### Webhook Not Triggering Builds
- Verify webhook is configured correctly in GitHub
- Check webhook delivery status in GitHub Settings → Webhooks
- Ensure Jenkins URL is accessible from GitHub (not localhost)
- Verify GitHub Plugin is installed in Jenkins
- Check Jenkins system log for webhook-related errors

### SCM Polling Not Working (if using polling)
- Check Jenkins has access to the Git repository
- Verify credentials are configured correctly
- Check Jenkins logs for polling errors
