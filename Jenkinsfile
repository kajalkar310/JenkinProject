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

        //  REMOVE Checkout stage (already done automatically)

        stage('Build') {
            steps {
                bat 'mvn clean package'
            }
        }

         stage('SonarQube Analysis') {
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
                    steps {
                        timeout(time: 5, unit: 'MINUTES') {
                            waitForQualityGate abortPipeline: true
                        }
                    }
                }

        stage('Stop Old Application') {
            steps {
                bat '''
                for /f "tokens=5" %%a in ('netstat -aon ^| findstr :%APP_PORT%') do taskkill /PID %%a /F
                '''
            }
        }

        stage('Deploy Application') {
            steps {
                bat '''
                start "" java -jar target\\%JAR_NAME%
                '''
            }
        }
    }

    post {
        success {
            echo 'Application deployed successfully'
        }
        failure {
            echo 'Deployment failed'
        }
    }
}
