pipeline {
  agent any
  stages {
    stage("test") {
        steps {
            script {
                echo "testing the application..."
                echo "executing pipeline for branch $BRANCH_NAME"
            }
        }
    }
    stage("build") {      
      when {
        expression {
            BRANCH_NAME == "master"
        }
      }
      steps {
          script {
              echo "building the application..."
          }
      }
    }
    stage("deploy") {
      when {
        expression {
            BRANCH_NAME == "master"
        }
      }
        steps {
            script {
                echo "building the application..."
            }
        } 
     } 
   }
}
