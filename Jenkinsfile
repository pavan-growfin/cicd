pipeline {
    agent any

    tools {
        maven 'Maven 3.9.12'
        jdk 'JDK-11'
    }

    triggers {
        // Use SCM webhook trigger (recommended for better performance)
        githubPush()

        // Alternative: Poll SCM every 5 minutes for changes
        // pollSCM('H/5 * * * *')
    }

    options {
        // Keep only last 10 builds
        buildDiscarder(logRotator(numToKeepStr: '10'))

        // Timeout after 30 minutes
        timeout(time: 30, unit: 'MINUTES')

        // Disable concurrent builds
        disableConcurrentBuilds()
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Checking out source code...'
                checkout scm
            }
        }

        stage('Build') {
            steps {
                echo 'Building the project...'
                sh 'mvn clean compile'
            }
        }

        stage('Unit Tests') {
            steps {
                echo 'Running unit tests...'
                sh 'mvn test'
            }
            post {
                always {
                    // Publish JUnit test results
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }

        stage('Package') {
            steps {
                echo 'Packaging the application...'
                sh 'mvn package -DskipTests'
            }
        }

        stage('Archive Artifacts') {
            steps {
                echo 'Archiving artifacts...'
                archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
            }
        }
    }

    post {
        success {
            echo 'Pipeline completed successfully!'
            // Send notification on success (optional)
            // emailext (
            //     subject: "SUCCESS: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
            //     body: "Build succeeded: ${env.BUILD_URL}",
            //     to: "your-email@example.com"
            // )
        }

        failure {
            echo 'Pipeline failed!'
            // Send notification on failure (optional)
            // emailext (
            //     subject: "FAILURE: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
            //     body: "Build failed: ${env.BUILD_URL}",
            //     to: "your-email@example.com"
            // )
        }

        always {
            echo 'Cleaning up workspace...'
            cleanWs()
        }
    }
}
