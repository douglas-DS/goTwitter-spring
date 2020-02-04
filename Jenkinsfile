@Library('jenkins-shared-library')_
pipeline {
    agent any
    tools {
        maven 'maven-3'
    }
    environment {
        COMPANY_NAME = 'douglasso'
        APP_NAME = 'gotwitter-spring'
    }
    stages {
        stage('Checkout') {
            steps {
                checkout scm
                sh "git rev-parse --short HEAD > commit-id"
            }
        }
        stage('Build') {
            steps {
                sh "mvn -B -DskipTests clean package"
                script {
                    tag = readFile('commit-id').replace("\n", "").replace("\r", "")
                    imageName = '${COMPANY_NAME}/${APP_NAME}:${tag}'
                    echo '${imageName}'
                    customImage = docker.build("${imageName}")
                }
            }
        }
        stage('Push image') {
            steps {
                script {
                    docker.withRegistry('https://registry.hub.docker.com', 'dockerhub-ds') {
                        custom.push()
                        customImage.push('latest')
                    }
                }
            }
        }
        stage('Deploy to K8S') {
            steps {
                sh "kubectl apply -f k8s_${APP_NAME}.yaml"
                sh "kubectl set image deployments/${APP_NAME} ${APP_NAME}=${imageName}"
                sh "kubectl rollout status deployments/${APP_NAME}"
            }
        }
    }
    //post {
        //always {
            //slackNotifier(currentBuild.currentResult, 'Delivery')
        //}
    //}
}
