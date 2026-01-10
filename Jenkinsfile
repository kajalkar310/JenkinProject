pipeline {
    agent any

    tools {
        jdk 'JDK17'
        maven 'Maven3'
    }

    environment {
        APP_NAME = "user-service"
        JAR_NAME = "user-service-0.0.1-SNAPSHOT.jar"
        APP_PORT = "8080"
    }

    stages {

        stage('Checkout Code') {
            steps {
                git branch: 'main', url: 'https://github.com/your-repo/user-service.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Stop Old Application') {
            steps {
                script {
                    sh '''
                    PID=$(lsof -t -i:$APP_PORT || true)
                    if [ ! -z "$PID" ]; then
                      kill -9 $PID
                    fi
                    '''
                }
            }
        }

        stage('Deploy Application') {
            steps {
                sh '''
                nohup java -jar target/$JAR_NAME > app.log 2>&1 &
                '''
            }
        }
    }

    post {
        success {
            echo ' Application deployed successfully without Docker'
        }
        failure {
            echo ' Deployment failed'
        }
    }
}
