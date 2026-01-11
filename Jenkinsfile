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
                git branch: 'main', url: 'https://github.com/kajalkar310/JenkinProject.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }

    
        stage('Deploy Application') {
            steps {
                echo 'Build successful'
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
