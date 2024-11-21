#!/usr/bin.env groovy

pipeline {
  agent any
  stages {
    stage("test") {
        steps {
            script {
                echo "Testing the application..."
            }
        }
    }
    stage("build") {      
      steps {
          script {
              echo "Building the application..."
          }
      }
    }

    stage("deploy") {
        steps {
            script {
                echo "Deploying the application..."
                def dockerCmd = 'docker run -p 3000:8080 -d vgevorgyan009/demo-app:1.1.2-8'
                sshagent(['ec2-server-key']) {
                    sh "ssh -o StrictHostKeyChecking=no ec2-user@13.50.16.143 ${dockerCmd}"
                }
            }
        } 
     } 
   }
}
