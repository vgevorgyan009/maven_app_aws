// this jenkinsfile is in paire with script1.groovy, it builds java-maven app then builds docker image from apps artefact then pushes it to dockerhub private repo, and then deploys app as dockercontainer to AWS EC2
def gv

pipeline {
  agent any
  tools {
       maven 'maven-3.9'
  }
  environment {
        IMAGE_NAME = 'vgevorgyan009/demo-app:java-maven-5.0'
  }
  stages {
    stage("init") {
        steps {
            script {
                gv = load "script1.groovy"
            }
        }
    }
    stage("build app jar") {        
      steps {
          script {
              echo 'building application jar...'
              gv.buildJar()
          }
      }
    }
    stage("build image") {        
      steps {
          script {
              gv.buildImage(env.IMAGE_NAME)
          }
      }
    }
    stage("deploy") {
        steps {
            script {
                echo "Deploying docker image to EC2..."
                def dockerCmd = "docker run -p 4000:8080 -d ${IMAGE_NAME}"
                sshagent(['ec2-server-key']) {
                sh "ssh -o StrictHostKeyChecking=no ec2-user@13.50.16.143 ${dockerCmd}"
                }
            }
        } 
     } 
   }
}

