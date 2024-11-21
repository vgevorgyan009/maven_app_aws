def buildJar() {
    echo 'building the application...'
    sh 'mvn package'
} 

def buildImage() {
    echo 'building the docker image...'
    withCredentials([usernamePassword(credentialsId: 'docker-hub-repo', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        sh "docker build -t ${IMAGE_NAME} ."
        sh 'echo $PASS | docker login -u $USER --password-stdin'
        sh "docker push ${IMAGE_NAME}"
    }
} 

return this
