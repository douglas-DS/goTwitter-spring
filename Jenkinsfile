node {
    checkout scm
    sh "git rev-parse --short HEAD > commit-id"
    tag = readFile('commit-id').replace("\n", "").replace("\r", "")
    companyName="douglasso"
    appName = "gotwitter-spring"
    imageName = "${companyName}/${appName}:${tag}"

    //Phase when jar and project image is build
    stage('Build')
        sh "./mvnw clean package"
        def customImage = docker.build("${imageName}")

    //The image is pushed to docker repo
    stage('Push')
        customImage.push()

    //Deploy to k8s cluster and old images are removed of deployments
    stage('Deploy PROD')
        customImage.push('latest')
        sh "kubectl apply -f k8s_${appName}.yaml"
        sh "kubectl set image deployments/${appName} ${appName}=${imageName}"
        sh "kubectl rollout status deployments/${appName}"
}