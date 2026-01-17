pipeline {
    agent any

    tools {
        jdk 'JDK17'
        maven 'Maven3'
    }

    environment {
        JAR_NAME = "user-service-0.0.1-SNAPSHOT.jar"
        APP_PORT = "8080"
    }

    stages {

        stage('Build') {
            steps {
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('SonarQube Analysis') {
            when {
                anyOf {
                    branch 'develop'
                    branch 'main'
                    branch 'master'
                }
            }
            steps {
                withSonarQubeEnv('sonar-9001') {
                    bat '''
                    mvn sonar:sonar ^
                      -Dsonar.projectKey=user-service ^
                      -Dsonar.projectName=user-service
                    '''
                }
            }
        }

        stage('Quality Gate') {
            when {
                anyOf {
                    branch 'develop'
                    branch 'main'
                    branch 'master'
                }
            }
            steps {
                timeout(time: 5, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        stage('Stop Old Application') {
            when {
                anyOf {
                    branch 'main'
                    branch 'master'
                }
            }
            steps {
                bat '''
                for /f "tokens=5" %%a in ('netstat -aon ^| findstr :%APP_PORT%') do taskkill /PID %%a /F
                '''
            }
        }

        stage('Deploy Application') {
            when {
                anyOf {
                    branch 'main'
                    branch 'master'
                }
            }
            steps {
                bat '''
                start "" java -jar target\\%JAR_NAME%
                '''
            }
        }
    }

    post {
        success {
            echo "Pipeline succeeded for branch: ${env.BRANCH_NAME}"
        }
        failure {
            echo "Pipeline failed for branch: ${env.BRANCH_NAME}"
        }
    }
}
