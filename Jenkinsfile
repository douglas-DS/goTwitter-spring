//@Library('jenkins-shared-library') _
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
        stage('Build package and image') {
            environment {
                tag = readFile('commit-id').trim()
                imageName = ""
                customImage = null
            }
            steps {
                sh "mvn -B -DskipTests clean package"
                script {
                    imageName = "${COMPANY_NAME}/${APP_NAME}:${tag}"
                    customImage = docker.build("${imageName}")
                }
            }
        }
        stage('Push image') {
            steps {
                script {
                    withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'dockerhub-ds', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD']]) {
                        sh 'docker login -u "$USERNAME" -p "$PASSWORD"'
                        customImage.push()
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
}
