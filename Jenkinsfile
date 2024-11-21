// nuyn jenkinsfile4-y uxxaki docker compose ov, eli script1.groovy i heta
def gv

pipeline {
  agent any
  tools {
       maven 'maven-3.9'
  }
  environment {
        IMAGE_NAME = 'vgevorgyan009/demo-app:java-maven-6.0'
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
              gv.buildImage()
          }
      }
    }
    stage("deploy") {
        steps {
            script {
                echo "Deploying docker image to EC2..."
                def dockerComposeCmd = "docker-compose -f docker-compose.yaml up --detach"
                sshagent(['ec2-server-key']) {
                sh "scp docker-compose.yaml ec2-user@13.50.16.143:/home/ec2-user" 
                sh "ssh -o StrictHostKeyChecking=no ec2-user@13.50.16.143 ${dockerComposeCmd}"
                }
            }
        } 
     } 
   }
}
