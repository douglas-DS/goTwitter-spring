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
                TAG = readFile('commit-id').trim()
                CUSTOM_IMAGE = null
            }
            steps {
                sh "mvn -B -DskipTests clean package"
                script {
                    //def tag = readFile('commit-id').replace("\n", "").replace("\r", "")
                    def imageName = '${COMPANY_NAME}/${APP_NAME}:${TAG}'
                    CUSTOM_IMAGE = docker.build("${imageName}")
                }
            }
        }
        stage('Push image') {
            steps {
                script {
                    withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'dockerhub-ds', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD']]) {
                        sh 'docker login -u "$USERNAME" -p "$PASSWORD"'
                        echo ${CUSTOM_IMAGE}
                        CUSTOM_IMAGE.push()
                        CUSTOM_IMAGE.push('latest')
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
