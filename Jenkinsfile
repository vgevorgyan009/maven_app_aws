// jenkinsfile to build dynamicly incremented application .jar file versions and applications docker image versions, and avoid infinate loop of builds
// this jenkinsfile also deploys app to aws ec2
pipeline {
  agent any
  tools {
      maven 'maven-3.9'
  }
  stages {
    stage('increment version') {
        steps {
            script {
                echo 'incrementing app version...'
                sh 'mvn build-helper:parse-version versions:set \
                -DnewVersion=\\\${parsedVersion.majorVersion}.\\\${parsedVersion.minorVersion}.\\\${parsedVersion.nextIncrementalVersion} \
                versions:commit'
                def matcher = readFile('pom.xml') =~ '<version>(.+)</version>'
                def version = matcher[0][1]
                env.IMAGE_NAME = "$version-$BUILD_NUMBER-tarber"
            }
        }
    }
    stage("build app") {      
        steps {
            script {
              echo "building the application..."
              sh 'mvn clean package'
          }
      }
    }
    stage('build image') {
        steps {
            script {
                echo 'building the docker image...'
                withCredentials([usernamePassword(credentialsId: 'docker-hub-repo', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
                sh "docker build -t vgevorgyan009/demo-app:${IMAGE_NAME} ."
                sh 'echo $PASS | docker login -u $USER --password-stdin'
                sh "docker push vgevorgyan009/demo-app:${IMAGE_NAME}"
    }
            }
        }
    }
    stage("deploy") {
        steps {
            script {
                echo "Deploying docker image to EC2..."
                def shellCmd = "bash ./server-cmds.sh ${IMAGE_NAME}"
                def ec2Instance = "ec2-user@13.50.16.143"
                sshagent(['ec2-server-key']) {
                sh "scp server-cmds.sh ${ec2Instance}:/home/ec2-user"   
                sh "scp docker-compose.yaml ${ec2Instance}:/home/ec2-user" 
                sh "ssh -o StrictHostKeyChecking=no ${ec2Instance} ${shellCmd}"
                }
            }
        } 
     } 
     stage('commit version update') {
        steps {
            script {
                withCredentials([usernamePassword(credentialsId: 'github-credentials-token', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
                    sh 'git config --global user.email "jenkins@hotmail.com"'
                    sh 'git config --global user.name "jenkins"'

                    sh 'git status'
                    sh 'git branch'
                    sh 'git config --list'

                    sh "git remote set-url origin https://${USER}:${PASS}@github.com/vgevorgyan009/maven_app_aws.git"
                    sh 'git add .'
                    sh 'git commit -m "ci: version bump"'
                    sh 'git push origin HEAD:docker-compose'
                }
            }
        }
     }
   }
}

