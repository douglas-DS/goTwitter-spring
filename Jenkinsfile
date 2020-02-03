node {
    checkout scm
    sh "git rev-parse --short HEAD > commit-id"
    tag = readFile('commit-id').replace("\n", "").replace("\r", "")
    companyName="douglasso"
    appName = "goTwitter-spring"
    imageName = "${companyName}/gotwitter-spring:${tag}"

    stage("Build")
        sh "./mvnw clean package"
        def customImage = docker.build("${imageName}")

    stage("Push")
        customImage.push()

    stage("Deploy PROD")
        customImage.push('latest')
        sh "kubectl apply -f k8s_${appName}.yaml"
        sh "kubectl set image deployments/${appName} ${appName}=${imageName}"
        sh "kubectl rollout status deployments/${appName}"
}