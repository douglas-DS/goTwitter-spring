//@Library('jenkins-shared-library') _
pipeline {
    agent any
    tools { maven 'maven-3' }
    environment {
        COMPANY_NAME = 'douglasso'
        APP_NAME = 'gotwitter-spring'
    }
    stages {
        stage('Checkout') {
            steps {
                script { notifyBuild('STARTED') }
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
    post {
        failure {
            script {
                currentBuild.result = 'FAILURE'
                notifyBuild(currentBuild.result)
            }
        }
        always {
            script {
                notifyBuild(currentBuild.result)
            }
        }
    }
}

def notifyBuild(String buildStatus = 'STARTED') {
    // build status of null means successful
    buildStatus =  buildStatus ?: 'SUCCESS'

    // Default values
//     def colorName = 'RED'
//     def colorCode = '#FF0000'
    def subject = "${buildStatus}: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'"
    def summary = "${subject} (${env.BUILD_URL})"
    def details = """<p>STARTED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]':</p>
    <p>Check console output at &QUOT;<a href='${env.BUILD_URL}'>${env.JOB_NAME} [${env.BUILD_NUMBER}]</a>&QUOT;</p>"""
    
    switch(buildStatus) {
        case 'STARTED':
            color = 'YELLOW';
            colorCode = '#FFFF00';
            break;
        case 'SUCCESS':
            color = 'GREEN'
            colorCode = '#00FF00'
            break;
        case 'FAILURE':
            color = 'RED'
            colorCode = '#FF0000'
            break;
//         default:
//             color = 'RED'
//             colorCode = '#FF0000'
//             break;
    }
    // Send notifications
    slackSend (color: colorCode, message: summary)
}
